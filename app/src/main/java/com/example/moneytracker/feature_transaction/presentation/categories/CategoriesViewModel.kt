package com.example.moneytracker.feature_transaction.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryService: ICategoryService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _iconId = mutableStateOf("")
    val iconId: State<String> = _iconId

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
    private var getIncomeCategoriesJob: Job? = null
    private var getExpenseCategoriesJob: Job? = null
    private var recentlyDeleted: CategoryViewModel? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    categoryService.getCategory(id).also { category ->
                        currentId = category.id
                        _name.value = category.name
                        _iconId.value = category.iconResourceId.toString()
                        _color.value = color.value
                        _isExpense.value = isExpense.value
                    }
                }
            }
        }
        getIncomeCategories()
        getExpenseCategories()
    }

    fun onEvent(event: CategoriesEvent, callback: (String) -> Unit) {
        when (event) {
            is CategoriesEvent.Delete -> {
                viewModelScope.launch {
                    val result = categoryService.deleteCategory(event.category)
                    recentlyDeleted = event.category
                    callback(result)
                }
            }

            is CategoriesEvent.Restore -> {
                viewModelScope.launch {
                    categoryService.insertCategory(recentlyDeleted ?: return@launch)
                    recentlyDeleted = null
                }
            }
        }
    }

    private fun getIncomeCategories() {
        getIncomeCategoriesJob?.cancel()
        getIncomeCategoriesJob = categoryService.getCategories(
            isExpenseFilter = false,
        )
            .onEach { categories ->
                _incomeCategories.value = categories
            }
            .launchIn(viewModelScope)
    }

    private fun getExpenseCategories() {
        getExpenseCategoriesJob?.cancel()
        getExpenseCategoriesJob = categoryService.getCategories(
            isExpenseFilter = true,
        )
            .onEach { categories ->
                _expenseCategories.value = categories
            }
            .launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data object SaveCategory : UiEvent()
    }
}