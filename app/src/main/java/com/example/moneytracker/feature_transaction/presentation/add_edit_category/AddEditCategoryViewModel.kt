package com.example.moneytracker.feature_transaction.presentation.add_edit_category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.InputFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private val categoryService: ICategoryService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _name = mutableStateOf(InputFieldState())
    val name: State<InputFieldState> = _name

//    private val _isIncome = mutableStateOf<CategoryViewModel?>(null)
//    val isIncome: State<CategoryViewModel?> = _isIncome

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentId: Int = 0

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != 0) {
                viewModelScope.launch {
                    categoryService.getCategory(id).also { category ->
                        currentId = category.id
                        _name.value = name.value.copy(
                            text = category.name,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditCategoryEvent) {
        when (event) {
            is AddEditCategoryEvent.EnteredName -> {
                _name.value = name.value.copy(
                    text = event.value
                )
            }

            is AddEditCategoryEvent.Save -> {
                viewModelScope.launch {
                    categoryService.insertCategory(
                        CategoryViewModel(
                            name = name.value.text,
                            color = Color.Gray,
                            isExpense = false,
                            iconResourceId = R.drawable.blur_radial,
                            id = currentId
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveCategory)
                }
            }
        }
    }

    sealed class UiEvent {
        data object SaveCategory : UiEvent()
    }
}