package com.cs407.uteri.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.cs407.uteri.ui.theme.UteriTheme
import com.cs407.uteri.ui.utils.Navbar
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarScreen(
    onNavigateBack: () -> Unit,
    navController: NavController
) {
    Scaffold (
        bottomBar = {
            Navbar(navController)
        }
    ) { padding ->
        Calendar(
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun Calendar(
    modifier: Modifier = Modifier
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val today = LocalDate.now()

    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val cells = buildList {
        addAll(daysOfWeek)
        repeat(currentMonth.atDay(1).dayOfWeek.value % 7) {
            add(null)
        }
        for (day in 1..currentMonth.lengthOfMonth()) {
            add(currentMonth.atDay(day))
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Month
        Row {
            Text(currentMonth.month.toString().lowercase().replaceFirstChar{it.uppercase()})
        }

        // Calendar Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            items(cells) { cell ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    when (cell) {
                        is LocalDate -> CalendarCell(cell)
                        is String -> Text(cell)
                        null -> Spacer(modifier = Modifier.aspectRatio(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarCell(
    date: LocalDate,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

