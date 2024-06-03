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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneytracker.R
import com.example.moneytracker.feature_transaction.presentation.bottom_bar.BottomBar
import com.example.moneytracker.feature_transaction.presentation.statistics.BalanceInfo
import com.example.moneytracker.feature_transaction.presentation.statistics.StatisticsViewModel
import com.example.moneytracker.feature_transaction.presentation.statistics.TotalForCategoryForMonth
import com.example.moneytracker.feature_transaction.presentation.util.getNominativeMonth
import com.example.moneytracker.ui.theme.Purple40
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartStyle
import com.jaikeerthick.composable_graphs.composables.pie.style.PieChartVisibility
import java.time.format.DateTimeFormatter


// TODO add date picker
// TODO split to multiple files so each has its own component

@Composable
fun Statistics(
    navController: NavController,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val locale = LocalContext.current.resources.configuration.locales[0]
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM", locale)

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
                MonthHeadline(
                    // TODO this will probably change, when date picker is implemented
                    // i would display the selected date range (e.g. 12.02.2007 - 30.8.2007)
                    monthName = getNominativeMonth(
                        viewModel.state.currentDateTime.format(
                            monthFormatter
                        ), locale
                    )
                            + " "
                            + viewModel.state.currentDateTime.year.toString()
                )
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .border(3.dp, Color.Black)
                .padding(20.dp)
        ) {
            Row(Modifier.padding(20.dp)) {
                Column(Modifier.padding(20.dp)) {
                    // TODO use some text composable from shared folder to have unified style
                    // or create your own text composable if current ones are not sufficient (use MaterialTheme.typography for style)
                    Text(text = stringResource(R.string.expense), fontSize = 15.sp)
                    Text(text = balanceInfo.expense.toString(), fontSize = 20.sp)
                }
                Column(Modifier.padding(20.dp)) {
                    Text(text = stringResource(R.string.income), fontSize = 15.sp)
                    Text(text = balanceInfo.income.toString(), fontSize = 20.sp)
                }
                Column(Modifier.padding(20.dp)) {
                    Text(text = stringResource(R.string.balance), fontSize = 15.sp)
                    Text(text = balanceInfo.getBalance().toString(), fontSize = 20.sp)
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
            style = PieChartStyle(
                visibility = PieChartVisibility(
                    isLabelVisible = true,
                    isPercentageVisible = true
                )
            ),
            onSliceClick = { pieData ->
                Toast.makeText(aContext, "${pieData.label}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}