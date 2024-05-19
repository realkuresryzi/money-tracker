package com.example.moneytracker.feature_transaction.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.entity.TransactionWithCategory
import com.example.moneytracker.feature_transaction.data.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(
        """select 
                `transaction`.*,
                category.id as category_id,
                category.name as category_name,
                category.color as category_color,
                category.isExpense as category_isExpense,
                category.iconResourceId as category_iconResourceId
            from `transaction`
            left join category on `transaction`.categoryId = category.id
            where (:isExpenseFilter is null or  category.isExpense = :isExpenseFilter)
            and (:categoryFilter is null or `transaction`.categoryId = :categoryFilter)
            order by
                case when :orderAsc = 1 then
                    case :order
                        when '${Constants.TITLE}' then `transaction`.title
                        when '${Constants.AMOUNT}' then `transaction`.amount
                        else `transaction`.createdAt 
                    end 
                end collate nocase asc,
                case when :orderAsc = 0 then
                    case :order
                        when '${Constants.TITLE}' then `transaction`.title
                        when '${Constants.AMOUNT}' then `transaction`.amount
                        else `transaction`.createdAt 
                    end
                end collate nocase desc
        """
    )
    fun getTransactions(
        isExpenseFilter: Boolean? = null,
        categoryFilter: Int? = null,
        order: String = Constants.DATE,
        orderAsc: Boolean = true
    ): Flow<List<TransactionWithCategory>>

    @Query(
        """select
                `transaction`.*,
                category.id as category_id,
                category.name as category_name,
                category.color as category_color,
                category.isExpense as category_isExpense,
                category.iconResourceId as category_iconResourceId
            from `transaction` 
            inner join category on `transaction`.categoryId = category.id 
            where `transaction`.id = :id
        """
    )
    suspend fun getTransactionById(id: Int): TransactionWithCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<Transaction>)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("DELETE FROM `Transaction`")
    suspend fun deleteAllTransactions()

    @Query(
        """select * from `transaction`
            inner join category on `transaction`.categoryId = category.id
            where (:isExpenseFilter is null or category.isExpense = :isExpenseFilter)
            and (:categoryFilter is null or category.id = :categoryFilter)
            and strftime('%m', `transaction`.createdAt) = :month
            and strftime('%Y', `transaction`.createdAt) = :year
        """
    )
    suspend fun getTransactionsByMonth(
        isExpenseFilter: Boolean? = null,
        categoryFilter: Int? = null,
        month: Int,
        year: Int
    ): List<Transaction>

    @Query(
        """select sum(amount) from `transaction`
            inner join category on `transaction`.categoryId = category.id
            where category.isExpense = 1
            and strftime('%m', `transaction`.createdAt) = :month
            and strftime('%Y', `transaction`.createdAt) = :year
        """
    )
    suspend fun getTotalExpenseByMonth(
        month: Int,
        year: Int
    ): Double

    @Query(
        """select sum(amount) from `transaction`
            inner join category on `transaction`.categoryId = category.id
            where category.isExpense = 0
            and strftime('%m', `transaction`.createdAt) = :month
            and strftime('%Y', `transaction`.createdAt) = :year
        """
    )
    suspend fun getTotalIncomeByMonth(
        month: Int,
        year: Int
    ): Double
}