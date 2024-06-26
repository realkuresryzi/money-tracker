package com.example.moneytracker.feature_transaction.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(
        """select * from `category`
            where (:isExpenseFilter is null or isExpense = :isExpenseFilter)
            order by name asc
        """
    )
    fun getCategories(isExpenseFilter: Boolean? = null): Flow<List<Category>>

    @Query(
        """
            select * from `transaction`
        """
    )
    fun getTransactions(): Flow<List<Transaction>>

    @Query("select * from `category` where id = :id")
    suspend fun getCategoryById(id: Int): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM Category")
    suspend fun deleteAllCategories()
}