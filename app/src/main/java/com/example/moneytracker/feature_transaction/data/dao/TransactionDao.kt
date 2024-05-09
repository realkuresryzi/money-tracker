package com.example.moneytracker.feature_transaction.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query(
        """select * from `transaction`
            inner join category on `transaction`.categoryId = category.id
            where (:isExpense is null or category.isExpense = :isExpense)
            and (:categoryId is null or category.id = :categoryId)
            order by createdAt desc
        """
    )
    suspend fun getTransactions(
        isExpense: Boolean? = null,
        categoryId: Int? = null
    ): List<Transaction>

    @Query("select * from `transaction` where id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}