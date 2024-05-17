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
import com.example.moneytracker.feature_transaction.domain.util.Constants
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.InputFieldState
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
    private val _name = mutableStateOf(
        InputFieldState(
            hint = Constants.TEXT_INPUT_PLACEHOLDER
        )
    )
    val name: State<InputFieldState> = _name

//    private val _isExpense = mutableStateOf<CategoryViewModel?>(null)
//    val isExpense: State<CategoryViewModel?> = _isExpense

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _incomeCategories = mutableStateOf<List<CategoryViewModel>>(emptyList())
    val incomeCategories: State<List<CategoryViewModel>> = _incomeCategories

    private val _expenseCategories = mutableStateOf<List<CategoryViewModel>>(emptyList())
    val expenseCategories: State<List<CategoryViewModel>> = _expenseCategories

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
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
        viewModelScope.launch {
            _expenseCategories.value = categoryService.getCategories(isExpenseFilter = true)
        }
        viewModelScope.launch {
            _incomeCategories.value = categoryService.getCategories(isExpenseFilter = false)
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
                            name = event.name,
                            color = Color.Gray,
                            isExpense = event.isExpense,
                            iconResourceId = R.drawable.blur_radial,
                            // id generated by db
                            id = -1
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