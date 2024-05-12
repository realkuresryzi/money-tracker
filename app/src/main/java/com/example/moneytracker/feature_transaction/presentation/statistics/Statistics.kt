package com.example.moneytracker.feature_transaction.presentation.statistics

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.ui.theme.Purple40
import com.jaikeerthick.composable_graphs.composables.bar.BarGraph
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData

@Composable
fun Statistics(
    navController: NavController,
    viewModel: StatisticsViewModel = viewModel()
){

    ModalNavigationDrawer(drawerContent = { /*TODO*/ }) {
        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController,
                    modifier = Modifier
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                MonthHeadline(monthName = viewModel.getCurrentMonth() + " " + viewModel.getCurrentYear())
                StatisticsBarGraph(viewModel.getExpenseInfo())
                StatisticsPieChart(viewModel.getCategoryMonthTotalList())
            }
        }

    }


}

// wrapper to view preview, only for debug purposes
@Preview
@Composable
fun StatisticsToShowPreview() {
    Statistics(navController = rememberNavController(), viewModel = viewModel())
}

@Composable
fun MonthHeadline(monthName: String) {
    Text(
        text = monthName,
        color = Purple40,
        fontSize = 50.sp,
        modifier = Modifier
            .padding(25.dp)
    )
}


@Composable
fun StatisticsBarGraph(expenseInfo: ExpenseInfo) {
    Box {
        Card(
            modifier = Modifier.border(3.dp, Color.Black)
        )
        {
            Row(Modifier.padding(20.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Expenses", fontSize = 15.sp)
                    Text(text = expenseInfo.getExpense().toString(), fontSize = 25.sp)
                }
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Income", fontSize = 15.sp)
                    Text(text = expenseInfo.getIncome().toString(), fontSize = 25.sp)
                }
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Balance", fontSize = 15.sp)
                    Text(text = expenseInfo.getBalance().toString(), fontSize = 25.sp)
                }

            }
            BarGraph(
                data = listOf(BarData(x = "22", y = 20), BarData(x = "23", y = 30)),
            )
        }

    }

}

@Composable
fun StatisticsPieChart(expensesByCategoryData: Collection<CategoryMonthTotal>) {
    val pieChartData = mutableListOf<PieData>()

    for (category in expensesByCategoryData) {
        //todo could add color mapped by category
        pieChartData.add(PieData(value = category.total.toFloat(), label = category.category.name))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(220.dp),
            data = pieChartData,
            /**onSliceClick = { pieData ->
            Toast.makeText(context, "${pieData.label}", Toast.LENGTH_SHORT).show()
            }**/
        )
    }
}