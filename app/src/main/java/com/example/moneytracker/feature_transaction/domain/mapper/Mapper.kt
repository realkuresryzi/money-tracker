package com.example.moneytracker.feature_transaction.domain.mapper

interface Mapper<T1, T2> {
    fun map(source: T1): T2
}