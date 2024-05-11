package com.example.moneytracker.feature_transaction.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.domain.util.Constants

@Dao
interface TransactionDao {
    @Query(
        """select * from `transaction`
            inner join category on `transaction`.categoryId = category.id
            where (:isExpenseFilter is null or category.isExpense = :isExpenseFilter)
            and (:categoryFilter is null or category.id = :categoryFilter)
            order by
                case when :orderAsc = 1 then
                    case :order
                        when '${Constants.TITLE}' then `transaction`.title
                        when '${Constants.AMOUNT}' then `transaction`.amount
                        else `transaction`.createdAt 
                    end 
                end asc,
                case when :orderAsc = 0 then
                    case :order
                        when '${Constants.TITLE}' then `transaction`.title
                        when '${Constants.AMOUNT}' then `transaction`.amount
                        else `transaction`.createdAt 
                    end
                end desc
        """
    )
    suspend fun getTransactions(
        isExpenseFilter: Boolean? = null,
        categoryFilter: Int? = null,
        order: String = Constants.DATE,
        orderAsc: Boolean = true
    ): List<Transaction>

    @Query("select * from `transaction` where id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}