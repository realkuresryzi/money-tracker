package com.example.moneytracker.feature_transaction.presentation.statistics.components

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.statistics.TotalForCategoryForMonth
import com.example.moneytracker.feature_transaction.presentation.statistics.BalanceInfo
import com.example.moneytracker.feature_transaction.presentation.statistics.StatisticsViewModel
import com.example.moneytracker.ui.theme.Purple40
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility


@Composable
fun Statistics(
    navController: NavController,
    viewModel: StatisticsViewModel = hiltViewModel()
){

    ModalNavigationDrawer(drawerContent = { }) {
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
                MonthHeadline(monthName = viewModel.currentMonth + " " + viewModel.currentYear)
                StatisticsBarGraph(viewModel.balanceInfo)
                StatisticsPieChart(viewModel.totalForCategoriesForMonth)
            }
        }

    }


}

// wrapper to view preview, only for debug purposes
@Preview
@Composable
fun StatisticsToShowPreview() {
    Statistics(navController = rememberNavController())
}

@Composable
fun MonthHeadline(monthName: String) {
    Text(
        text = monthName,
        color = Purple40,
        fontSize = 50.sp,
        modifier = Modifier
            .padding(25.dp),
    )
}


@Composable
fun StatisticsBarGraph(balanceInfo: BalanceInfo) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Card(
            modifier = Modifier.border(3.dp, Color.Black).padding(20.dp),

        )
        {
            Row(Modifier.padding(20.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Expenses", fontSize = 15.sp)
                    Text(text = balanceInfo.expense.toString(), fontSize = 25.sp)
                }
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Income", fontSize = 15.sp)
                    Text(text = balanceInfo.income.toString(), fontSize = 25.sp)
                }
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Balance", fontSize = 15.sp)
                    Text(text = balanceInfo.getBalance().toString(), fontSize = 25.sp)
                }

            }
        }

    }

}

@Composable
fun StatisticsPieChart(expensesByCategoryData: Collection<TotalForCategoryForMonth>) {
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
        val aContext = LocalContext.current
        PieChart(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(300.dp),
            data = pieChartData,
            style = PieChartStyle(visibility = PieChartVisibility(true, true, )),
            onSliceClick = { pieData ->
            Toast.makeText(aContext, "${pieData.label}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}