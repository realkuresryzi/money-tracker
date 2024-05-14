package com.example.moneytracker.feature_transaction.data.storage

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create the new table with the updated schema
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Transaction_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `title` TEXT NOT NULL,
                `amount` REAL NOT NULL,
                `categoryId` INTEGER NOT NULL,
                `createdAt` TEXT NOT NULL,
                `imageUri` TEXT,
                FOREIGN KEY(`categoryId`) REFERENCES `Category`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
            )
        """.trimIndent()
        )

        // Copy data from the old table to the new table
        db.execSQL(
            """
            INSERT INTO `Transaction_new` (`id`, `title`, `amount`, `categoryId`, `createdAt`, `imageUri`)
            SELECT `id`, `title`, `amount`, `categoryId`, `createdAt`, `imageUri`
            FROM `Transaction`
        """.trimIndent()
        )

        // Remove the old table
        db.execSQL("DROP TABLE `Transaction`")

        // Rename the new table to the old table name
        db.execSQL("ALTER TABLE `Transaction_new` RENAME TO `Transaction`")

        // Recreate the indices
        db.execSQL("CREATE INDEX `index_Transaction_categoryId` ON `Transaction` (`categoryId`)")
    }
}