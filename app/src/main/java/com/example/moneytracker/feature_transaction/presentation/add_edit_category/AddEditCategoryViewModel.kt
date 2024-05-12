package com.example.moneytracker.feature_transaction.presentation.add_edit_category

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private val categoryService: ICategoryService
) : ViewModel() {
    private val _color = mutableIntStateOf(CategoryModel.colors.random().toArgb())
    val color: State<Int> = _color
}