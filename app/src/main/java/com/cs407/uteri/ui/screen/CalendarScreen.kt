package com.cs407.uteri.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BasalBodyTemperatureRecord
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cs407.uteri.R
import com.cs407.uteri.data.DatabaseProvider
import com.cs407.uteri.data.Mood
import com.cs407.uteri.ui.utils.Navbar
import com.cs407.uteri.ui.utils.daysUntil
import com.cs407.uteri.ui.utils.getCyclePhase
import com.cs407.uteri.ui.utils.getCyclePrediction
import com.cs407.uteri.ui.utils.getLastPeriodStart
import com.cs407.uteri.ui.utils.getPeriodDay
import com.cs407.uteri.ui.utils.phaseToText
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val database = remember { DatabaseProvider.getDatabase(context) }
    val viewModel: CalendarViewModel = viewModel(
        factory = CalendarViewModelFactory(database)
    )

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var showModal by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val entries = viewModel.allEntries.collectAsState().value

    val lastStart = getLastPeriodStart(entries)
    val periodDay = getPeriodDay(lastStart)

    // Request for Health Connect Permissions from the user
    val permissions = setOf(
        HealthPermission.getReadPermission(BasalBodyTemperatureRecord::class),
    )
    val healthConnectClient = HealthConnectClient.getOrCreate(context)
    val launcher = rememberLauncherForActivityResult(
        PermissionController.createRequestPermissionResultContract()
    ) { granted -> // TODO: handle denied permissions?
      }

    LaunchedEffect(Unit) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (!granted.containsAll(permissions)) {
            launcher.launch(permissions)
        }
    }

    LaunchedEffect(showModal) {
        if (showModal) {
            sheetState.expand()
        }
    }

    Scaffold(
        bottomBar = { Navbar(navController) },
        containerColor = Color(0xFFFFFae8),
        floatingActionButton = {
            FloatingActionButton(onClick = { showModal = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
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
                        Text(
                            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
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
                GradientCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    startColor = Color(0xFFFF93BA),
                    endColor = Color(0xFFFF6489)
                ) {
                    Calendar(
                        modifier = Modifier.padding(16.dp),
                        currentMonth = currentMonth,
                        entries = viewModel.allEntries.collectAsState().value,
                        onDateClick = { date ->
                            selectedDate = date
                            showModal = true
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                GradientCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(120.dp),
                    startColor = Color(0xFFFF6489),
                    endColor = Color(0xFFFFE1EB)
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
                            val phase = getCyclePhase(periodDay)
                            val phaseText = phaseToText(phase)
                            Text(
                                text = "Current Phase: $phaseText",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier.size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CycleProgressView(periodDay = periodDay)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                GradientCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(120.dp),
                    startColor = Color(0xFFFFe598),
                    endColor = Color(0xFFFFD75F)
                ) {
                }
            }
        }
        if (showModal) {
            ModalBottomSheet(
                onDismissRequest = { showModal = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                LogEntryForm(
                    date = selectedDate,
                    viewModel = viewModel,
                    onDismiss = { showModal = false }
                )
            }
        }
    }
}

@Composable
private fun GradientCard(
    modifier: Modifier = Modifier,
    startColor: Color,
    endColor: Color,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(startColor, endColor)
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        content()
    }
}

@Composable
private fun CycleProgressView(
    periodDay: Int,
    cycleLength: Int = 28
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val progress = (periodDay.toFloat() / cycleLength.toFloat()).coerceIn(0f, 1f)
        val sweepAngle = (360f * progress).coerceIn(0f, 360f)

        Canvas(modifier = Modifier
            .size(80.dp)
            .align(Alignment.Center)) {
            val radius = size.minDimension / 2 - 6.dp.toPx()
            val center = Offset(size.width / 2, size.height / 2)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2, radius * 2)
            val strokeWidth = 6.dp.toPx()
            val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)

            drawArc(
                color = Color.White.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )
        }
        Box(modifier = Modifier
            .size(60.dp)
            .align(Alignment.Center)) {
            Image(
                painter = painterResource(id = R.drawable.asterisk),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.2f)
            )
            Text(
                text = periodDay.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-12).dp, y = (-8).dp),
                color = Color.White
            )
            Text(
                text = "/",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
            Text(
                text = cycleLength.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 12.dp, y = 8.dp),
                color = Color.White
            )
        }
    }
}

@Composable
private fun Calendar(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    entries: List<com.cs407.uteri.data.LogEntry>,
    onDateClick: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val entriesByDate = entries.associateBy { it.date }

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
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
        ) {
            items(cells) { cell ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    when (cell) {
                        is LocalDate -> {
                            val entry = entriesByDate[cell]
                            CalendarCell(
                                date = cell,
                                isToday = cell == today,
                                hasFlow = entry?.flow == true,
                                onClick = { onDateClick(cell) }
                            )
                        }
                        is String -> Text(
                            cell,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = Color.White
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
    isToday: Boolean = false,
    hasFlow: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = when {
                        isPressed -> if (isToday) Color(0xFF90CAF9) else Color(0xFFF5F5F5)
                        isToday -> Color(0xFFFFe598)
                        else -> Color.White
                    },
                    shape = CircleShape
                )
                .border(
                    width = if (isToday || hasFlow) 4.dp else 1.dp,
                    color = when {
                        hasFlow -> Color(0xFF700000)
                        isToday -> Color(0xFFFF6489)
                        else -> Color(0xFFE0E0E0)
                    },
                    shape = CircleShape
                )
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isToday) FontWeight.SemiBold else FontWeight.Normal
            ),
                color = if (isToday) Color(0xFF590056) else Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
        }
    }
}

@Composable
private fun LogEntryForm(
    date: LocalDate,
    viewModel: CalendarViewModel,
    onDismiss: () -> Unit
) {
    val medications by viewModel.medications.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var flow by remember { mutableStateOf(false) }
    var birthControl by remember { mutableStateOf(false) }
    var selectedMoods by remember { mutableStateOf(setOf<Mood>()) }
    var selectedMedications by remember { mutableStateOf(setOf<Int>()) }

    var showAddMedicationDialog by remember { mutableStateOf(false) }
    var newMedicationName by remember { mutableStateOf("") }
    var newMedicationType by remember { mutableStateOf("") }


    LaunchedEffect(date) {
        val existingEntry = viewModel.getEntryForDate(date)
        existingEntry?.let {
            flow = it.flow
            birthControl = it.birthControl
            selectedMoods = it.moods?.toSet() ?: emptySet()
            selectedMedications = it.medications?.toSet() ?: emptySet()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .padding(bottom = 32.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Log Entry for ${date.format(java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy"))}",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Flow",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = flow,
                onCheckedChange = { flow = it }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Birth Control",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = birthControl,
                onCheckedChange = { birthControl = it }
            )
        }

        Text(
            text = "Moods",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Mood.values().toList()) { mood ->
                FilterChip(
                    selected = selectedMoods.contains(mood),
                    onClick = {
                        selectedMoods = if (selectedMoods.contains(mood)) {
                            selectedMoods - mood
                        } else {
                            selectedMoods + mood
                        }
                    },
                    label = {
                        Text(
                            text = mood.name.lowercase().replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        Text(
            text = "Medications",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )

        medications.forEach { medication ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${medication.name} (${medication.type})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Checkbox(
                    checked = selectedMedications.contains(medication.id),
                    onCheckedChange = { checked ->
                        selectedMedications = if (checked) {
                            selectedMedications + medication.id
                        } else {
                            selectedMedications - medication.id
                        }
                    }
                )
            }
        }

        TextButton(
            onClick = { showAddMedicationDialog = true }
        ) {
            Text("+ Add New Medication")
        }

        if (showAddMedicationDialog) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Add New Medication",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    value = newMedicationName,
                    onValueChange = { newMedicationName = it },
                    label = { Text("Medication Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = newMedicationType,
                    onValueChange = { newMedicationType = it },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            showAddMedicationDialog = false
                            newMedicationName = ""
                            newMedicationType = ""
                        }
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val newId = viewModel.addMedication(newMedicationName, newMedicationType)
                                selectedMedications = selectedMedications + newId.toInt()
                                showAddMedicationDialog = false
                                newMedicationName = ""
                                newMedicationType = ""
                            }
                        },
                        enabled = newMedicationName.isNotBlank() && newMedicationType.isNotBlank()
                    ) {
                        Text("Add")
                    }
                }
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.saveLogEntry(
                        date = date,
                        flow = flow,
                        moods = selectedMoods.toList(),
                        birthControl = birthControl,
                        medicationIds = selectedMedications.toList()
                    )
                    onDismiss()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Entry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    val navController = rememberNavController()
    CalendarScreen(
        navController = navController
    )
}
