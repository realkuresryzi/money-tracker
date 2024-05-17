package com.example.moneytracker.feature_transaction.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.util.Constants
import com.example.moneytracker.feature_transaction.presentation.add_edit_category.AddEditCategoryEvent
import com.example.moneytracker.feature_transaction.presentation.add_edit_transaction.InputFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryService: ICategoryService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _name = mutableStateOf(
        InputFieldState(
            hint = Constants.TEXT_INPUT_PLACEHOLDER
        )
    )
    val name: State<InputFieldState> = _name

    private val _iconId = mutableStateOf(
        InputFieldState(
            hint = Constants.NUMERIC_INPUT_PLACEHOLDER
        )
    )
    val iconId: State<InputFieldState> = _iconId

    private val _category = mutableStateOf<CategoryViewModel?>(null)
    val category: State<CategoryViewModel?> = _category

    private val _color = mutableStateOf<CategoryViewModel?>(null)
    val color: State<CategoryViewModel?> = _color

    private val _isExpense = mutableStateOf<CategoryViewModel?>(null)
    val isExpense: State<CategoryViewModel?> = _isExpense

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
                        _iconId.value = iconId.value.copy(
                            text = category.iconResourceId.toString(),
                            isHintVisible = false
                        )
                        _color.value = color.value
                        _isExpense.value = isExpense.value
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

    sealed class UiEvent {
        data object SaveCategory : UiEvent()
    }
}