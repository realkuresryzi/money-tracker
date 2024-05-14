package com.example.moneytracker.di

import android.app.Application
import androidx.room.Room
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import com.example.moneytracker.feature_transaction.data.entity.TransactionWithCategory
import com.example.moneytracker.feature_transaction.data.repository.ICategoryRepository
import com.example.moneytracker.feature_transaction.data.repository.ITransactionRepository
import com.example.moneytracker.feature_transaction.data.repository.implementation.CategoryRepository
import com.example.moneytracker.feature_transaction.data.repository.implementation.TransactionRepository
import com.example.moneytracker.feature_transaction.data.storage.MIGRATION_1_2
import com.example.moneytracker.feature_transaction.data.storage.RoomDb
import com.example.moneytracker.feature_transaction.domain.mapper.CategoryEntityToModelMapper
import com.example.moneytracker.feature_transaction.domain.mapper.CategoryModelToEntityMapper
import com.example.moneytracker.feature_transaction.domain.mapper.Mapper
import com.example.moneytracker.feature_transaction.domain.mapper.TransactionEntityToModelMapper
import com.example.moneytracker.feature_transaction.domain.mapper.TransactionModelToEntityMapper
import com.example.moneytracker.feature_transaction.domain.model.CategoryViewModel
import com.example.moneytracker.feature_transaction.domain.model.TransactionViewModel
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
        )
            .addMigrations(MIGRATION_1_2)
            .build()
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
    fun provideCategoryEntityToModelMapper(): Mapper<Category, CategoryViewModel> {
        return CategoryEntityToModelMapper()
    }

    @Provides
    @Singleton
    fun provideCategoryModelToEntityMapper(): Mapper<CategoryViewModel, Category> {
        return CategoryModelToEntityMapper()
    }

    @Provides
    @Singleton
    fun provideTransactionModelToEntityMapper(): Mapper<TransactionViewModel, Transaction> {
        return TransactionModelToEntityMapper()
    }

    @Provides
    @Singleton
    fun provideTransactionEntityToModelMapper(
        mapper: Mapper<Category, CategoryViewModel>
    ): Mapper<TransactionWithCategory, TransactionViewModel> {
        return TransactionEntityToModelMapper(mapper)
    }

    @Provides
    @Singleton
    fun provideCategoryService(
        repository: ICategoryRepository,
        entityToModelMapper: Mapper<Category, CategoryViewModel>,
        modelToEntityMapper: Mapper<CategoryViewModel, Category>
    ): ICategoryService {
        return CategoryService(repository, entityToModelMapper, modelToEntityMapper)
    }

    @Provides
    @Singleton
    fun provideTransactionService(
        repository: ITransactionRepository,
        transactionWithCategoryMapper: Mapper<TransactionWithCategory, TransactionViewModel>,
        transactionMapper: Mapper<TransactionViewModel, Transaction>
    ): ITransactionService {
        return TransactionService(repository, transactionWithCategoryMapper, transactionMapper)
    }
}