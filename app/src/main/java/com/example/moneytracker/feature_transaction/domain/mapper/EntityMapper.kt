package com.example.moneytracker.feature_transaction.domain.mapper

interface EntityMapper<Entity, Model> {
    suspend fun entityToModel(entity: Entity): Model
    fun modelToEntity(model: Model): Entity
}