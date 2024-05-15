package com.example.moneytracker.feature_transaction.data.storage

import android.graphics.Color
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.data.entity.Category
import com.example.moneytracker.feature_transaction.data.entity.Transaction
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

class DatabaseSeeder @Inject constructor(
    private val roomDb: RoomDb
) {
    suspend fun seedDatabase() {

        roomDb.transactionDao.deleteAllTransactions()
        roomDb.categoryDao.deleteAllCategories()

        val categories = listOf(
            // Expense Categories
            Category(
                id = 0,
                name = "Food",
                color = Color.RED,
                isExpense = true,
                iconResourceId = R.drawable.food_fork_drink
            ),
            Category(
                id = 0,
                name = "Transport",
                color = Color.BLUE,
                isExpense = true,
                iconResourceId = R.drawable.train_car
            ),
            Category(
                id = 0,
                name = "Utilities",
                color = Color.RED,
                isExpense = true,
                iconResourceId = R.drawable.invoice_list
            ),
            Category(
                id = 0,
                name = "Entertainment",
                color = Color.YELLOW,
                isExpense = true,
                iconResourceId = R.drawable.basketball
            ),
            Category(
                id = 0,
                name = "Healthcare",
                color = Color.MAGENTA,
                isExpense = true,
                iconResourceId = R.drawable.home
            ),
            Category(
                id = 0,
                name = "Education",
                color = Color.CYAN,
                isExpense = true,
                iconResourceId = R.drawable.shopping
            ),
            Category(
                id = 0,
                name = "Miscellaneous",
                color = Color.GRAY,
                isExpense = true,
                iconResourceId = R.drawable.cart
            ),
            // Income Categories
            Category(
                id = 0,
                name = "Salary",
                color = Color.GREEN,
                isExpense = false,
                iconResourceId = R.drawable.chart_box
            ),
            Category(
                id = 0,
                name = "Freelance",
                color = Color.LTGRAY,
                isExpense = false,
                iconResourceId = R.drawable.dots_horizontal_circle
            ),
            Category(
                id = 0,
                name = "Investments",
                color = Color.DKGRAY,
                isExpense = false,
                iconResourceId = R.drawable.plus
            )
        )
        roomDb.categoryDao.insertAll(categories)

        val incomeCategories = roomDb.categoryDao.getCategories(isExpenseFilter = false)
        val expenseCategories = roomDb.categoryDao.getCategories(isExpenseFilter = true)
        val transactions = listOf(
            Transaction(
                id = 0,
                title = "Grocery Shopping",
                amount = -50.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusDays(Random.nextLong(1, 30))
            ),
            Transaction(
                id = 0,
                title = "Bus Ticket",
                amount = -2.5,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusMonths(Random.nextLong(1, 12))
            ),
            Transaction(
                id = 0,
                title = "Electric Bill",
                amount = -30.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusYears(Random.nextLong(1, 5))
            ),
            Transaction(
                id = 0,
                title = "Movie Night",
                amount = -12.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusDays(Random.nextLong(1, 30))
            ),
            Transaction(
                id = 0,
                title = "Doctor's Visit",
                amount = -100.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusMonths(Random.nextLong(1, 12))
            ),
            Transaction(
                id = 0,
                title = "Textbooks",
                amount = -60.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusYears(Random.nextLong(1, 5))
            ),
            Transaction(
                id = 0,
                title = "Birthday Gift",
                amount = -25.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusDays(Random.nextLong(1, 30))
            ),
            Transaction(
                id = 0,
                title = "Monthly Salary",
                amount = 3000.0,
                categoryId = incomeCategories[Random.nextInt(0, incomeCategories.size)].id,
                createdAt = LocalDateTime.now().minusMonths(Random.nextLong(1, 12))
            ),
            Transaction(
                id = 0,
                title = "Freelance Project",
                amount = 800.0,
                categoryId = incomeCategories[Random.nextInt(0, incomeCategories.size)].id,
                createdAt = LocalDateTime.now().minusYears(Random.nextLong(1, 5))
            ),
            Transaction(
                id = 0,
                title = "Stock Dividend",
                amount = 150.0,
                categoryId = incomeCategories[Random.nextInt(0, incomeCategories.size)].id,
                createdAt = LocalDateTime.now().minusDays(Random.nextLong(1, 30))
            ),
            Transaction(
                id = 0,
                title = "Dinner",
                amount = -40.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusMonths(Random.nextLong(1, 12))
            ),
            Transaction(
                id = 0,
                title = "Taxi Fare",
                amount = -20.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusYears(Random.nextLong(1, 5))
            ),
            Transaction(
                id = 0,
                title = "Internet Bill",
                amount = -45.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusDays(Random.nextLong(1, 30))
            ),
            Transaction(
                id = 0,
                title = "Concert Ticket",
                amount = -80.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusMonths(Random.nextLong(1, 12))
            ),
            Transaction(
                id = 0,
                title = "Medicine",
                amount = -15.0,
                categoryId = expenseCategories[Random.nextInt(0, expenseCategories.size)].id,
                createdAt = LocalDateTime.now().minusYears(Random.nextLong(1, 5))
            )
        )
        roomDb.transactionDao.insertAll(transactions)
    }
}
