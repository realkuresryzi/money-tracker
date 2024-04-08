package com.example.moneytracker.ui.Statistics

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.moneytracker.Greeting
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.ui.theme.Purple40
import com.jaikeerthick.composable_graphs.composables.bar.BarGraph
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData



class StatisticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.Default)
            setContent {
                MoneyTrackerTheme {
                    StatisticsPreview()
                }

            }
        }
    }

}
@Preview
@Composable
fun StatisticsPreview() {
    Column {
        MonthHeadline(monthName = "August")
        StatisticsBarGraph()
        StatisticsPieChart()

    }
}

@Composable
fun MonthHeadline(monthName: String){
    Text(text = monthName,
        color = Purple40,
        fontSize = 50.sp,
        modifier = Modifier
            .padding(25.dp))
}

@Composable
fun StatisticsBarGraph(){
    Box {
        Card (
            modifier = Modifier.border(3.dp, Color.Black))
            {
                Row (Modifier.padding(20.dp)){
                    Column (Modifier.padding(20.dp)) {
                        Text(text = "Expenses", fontSize = 15.sp)
                        Text(text = "25000", fontSize = 25.sp)
                    }
                    Column (Modifier.padding(20.dp)) {
                        Text(text = "Income", fontSize = 15.sp)
                        Text(text = "35000", fontSize = 25.sp)
                    }
                    Column (Modifier.padding(20.dp)){
                        Text(text = "Balance", fontSize = 15.sp)
                        Text(text = "10000", fontSize = 25.sp)
                    }

                }
                BarGraph(
                    data = listOf(BarData(x = "22", y = 20), BarData(x = "23", y = 30)),
                )
            }

    }

}

@Composable
fun StatisticsPieChart(){
    val pieChartData = listOf(
        PieData(value = 130F, label = "HTC", color = Color.Green),
        PieData(value = 260F, label = "Apple", labelColor = Color.Blue),
        PieData(value = 500F, label = "Google"),
    )

    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ){
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