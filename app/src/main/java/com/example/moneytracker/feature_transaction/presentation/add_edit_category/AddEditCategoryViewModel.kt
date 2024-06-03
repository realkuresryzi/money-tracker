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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private val categoryService: ICategoryService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _isIncome = mutableStateOf<CategoryViewModel?>(null)
    val isIncome: State<CategoryViewModel?> = _isIncome

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentId: Int = 0

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != 0) {
                viewModelScope.launch {
                    categoryService.getCategory(id).also { category ->
                        currentId = category.id
                        _name.value = category.name
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditCategoryEvent) {
        when (event) {
            is AddEditCategoryEvent.EnteredName -> {
                _name.value = event.value
            }

            is AddEditCategoryEvent.EnteredIsExpense -> {
                //_isIncome.value = isIncome.value
            }

            is AddEditCategoryEvent.Save -> {
                viewModelScope.launch {
                    categoryService.insertCategory(
                        CategoryViewModel(
                            name = name.value,
                            color = Color.Gray,
                            isExpense = isIncome.value?.isExpense == true,
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