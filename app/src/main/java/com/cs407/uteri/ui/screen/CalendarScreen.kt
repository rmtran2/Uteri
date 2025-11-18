package com.cs407.uteri.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs407.uteri.R
import com.cs407.uteri.ui.utils.Navbar
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onNavigateBack: () -> Unit,
    navController: NavController
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var showModal by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        bottomBar = { Navbar(navController) },
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(onClick = { showModal = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { 
                        currentMonth = currentMonth.minusMonths(1)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios_24px),
                            contentDescription = "Previous Month"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { 
                        currentMonth = currentMonth.plusMonths(1)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward_ios_24px),
                            contentDescription = "Next Month"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                // Calendar view at the top
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Calendar(
                        modifier = Modifier.padding(16.dp),
                        currentMonth = currentMonth
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                // Cycle phase card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFB6C1)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Current Phase: Ovulation",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier.size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CycleProgressView()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Blank card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5E6F0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    // Blank for now
                }
            }
        }
        if (showModal) {
            ModalBottomSheet(
                onDismissRequest = { showModal = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp)
                ) {
                    // Blank modal content
                }
            }
        }
    }
}

@Composable
private fun CycleProgressView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(80.dp).align(Alignment.Center)) {
            val radius = size.minDimension / 2 - 6.dp.toPx()
            val center = Offset(size.width / 2, size.height / 2)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2, radius * 2)
            val strokeWidth = 6.dp.toPx()
            val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

            // Background arc
            drawArc(
                color = Color.White.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
            // Progress arc
            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = 360f * (28f / 31f),
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
        }
        Box(modifier = Modifier.size(60.dp).align(Alignment.Center)) {
            Image(
                painter = painterResource(id = R.drawable.asterisk),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().alpha(0.2f)
            )
            Text(
                text = "28",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.align(Alignment.Center).offset(x = (-12).dp, y = (-8).dp),
                color = Color.White
            )
            Text(
                text = "/",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
            Text(
                text = "31",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.align(Alignment.Center).offset(x = 12.dp, y = 8.dp),
                color = Color.White
            )
        }
    }
}

@Composable
private fun Calendar(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth
) {
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
        // Calendar Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            items(cells) { cell ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    when (cell) {
                        is LocalDate -> CalendarCell(cell, cell == today)
                        is String -> Text(
                            cell,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        null -> Box(
                            modifier = Modifier.aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.asterisk),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarCell(
    date: LocalDate,
    isToday: Boolean = false
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = Color.White,
                shape = CircleShape
            )
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isToday) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFC7DEFFFF)
@Composable
fun CalendarScreenPreview() {
    val navController = rememberNavController()
    CalendarScreen(
        onNavigateBack = {},
        navController = navController
    )
}



