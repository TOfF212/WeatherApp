package com.hfad.weather.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hfad.weather.domain.Weather
import com.hfad.weather.ui.theme.Pink40
import com.hfad.weather.ui.theme.Pink80
import kotlinx.coroutines.launch




@Composable
fun ForecastList(
    daysList: List<Weather>,
    currentDay: Weather,
    onDayClick: (Weather) -> Unit) {
    val tablist = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState(pageCount = { tablist.size })
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .padding(5.dp)
            .clip(RoundedCornerShape(50.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex, indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex])
                )
            }, contentColor = Pink40, containerColor = Pink80
        ) {
            tablist.forEachIndexed { index, string ->
                Tab(
                    selected = false,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(text = string)
                    })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            when (index){
                0 -> HoursList(currentDay.hours)
                1 -> DaysList(daysList, onDayClick)
            }
        }
    }
}

@Composable
fun DaysList(daysList: List<Weather>, onDayClick: (Weather) -> Unit){
    LazyColumn (Modifier.fillMaxSize()){
        itemsIndexed(
            daysList
        ){
                _, item -> ListItem(item, onDayClick)
        }
    }
}
@Composable
fun HoursList(hoursList: List<Weather>){
    LazyColumn (Modifier.fillMaxSize()){
        itemsIndexed(
            hoursList
        ){
                _, item ->
            ListItem(item, {})
        }
    }
}
@Composable
fun ListItem(item: Weather, onClick: (Weather) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(1f)
            .padding(top = 5.dp)
            .clickable { onClick(item) },
        colors = CardDefaults.cardColors(containerColor = Pink80, contentColor = Pink40),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(50.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    text = item.time, style = TextStyle(fontSize = 20.sp),
                )
                Text(
                    text = item.condition, style = TextStyle(fontSize = 20.sp),
                )
            }
            Text(
                text = item.currentTemp.ifEmpty { item.maxTemp + "/" + item.minTemp },
                style = TextStyle(fontSize = 30.sp),
            )

            AsyncImage(
                model = "https:${item.conditionIcon}",
                contentDescription = "im2",
                modifier = Modifier
                    .size(35.dp)
                    .padding(top = 8.dp, end = 8.dp)
            )
        }
    }
}

