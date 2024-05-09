package com.example.moneytracker.di

import android.app.Application
import androidx.room.Room
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.repository.implementation.CategoryRepository
import com.example.moneytracker.feature_transaction.data.repository.implementation.TransactionRepository
import com.example.moneytracker.feature_transaction.data.storage.RoomDb
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository
import com.example.moneytracker.feature_transaction.data.repository.ITransactionRepository
import com.example.moneytracker.feature_transaction.domain.mapper.CategoryMapper
import com.example.moneytracker.feature_transaction.domain.mapper.EntityMapper
import com.example.moneytracker.feature_transaction.domain.mapper.TransactionMapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionModel
import com.example.moneytracker.feature_transaction.domain.service.ICategoryService
import com.example.moneytracker.feature_transaction.domain.service.ITransactionService
import com.example.moneytracker.feature_transaction.domain.service.implementation.CategoryService
import com.example.moneytracker.feature_transaction.domain.service.implementation.TransactionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoomDb(app: Application): RoomDb {
        return Room.databaseBuilder(
            app,
            RoomDb::class.java,
            RoomDb.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: RoomDb): ICategoryRepository {
        return CategoryRepository(db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(db: RoomDb): ITransactionRepository {
        return TransactionRepository(db.transactionDao)
    }

    @Provides
    @Singleton
    fun provideCategoryMapper(): EntityMapper<Category, CategoryModel> {
        return CategoryMapper()
    }

    @Provides
    @Singleton
    fun provideTransactionMapper(
        repository: ICategoryRepository,
        mapper: EntityMapper<Category, CategoryModel>
    ): EntityMapper<Transaction, TransactionModel> {
        return TransactionMapper(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideCategoryService(
        repository: ICategoryRepository,
        mapper: EntityMapper<Category, CategoryModel>
    ): ICategoryService {
        return CategoryService(repository, mapper)
    }

    @Provides
    @Singleton
    fun provideTransactionService(
        repository: ITransactionRepository,
        mapper: EntityMapper<Transaction, TransactionModel>
    ): ITransactionService {
        return TransactionService(repository, mapper)
    }
}