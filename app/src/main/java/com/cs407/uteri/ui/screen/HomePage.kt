package com.cs407.uteri.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs407.uteri.R
import com.cs407.uteri.data.DatabaseProvider
import com.cs407.uteri.ui.utils.daysUntil
import com.cs407.uteri.ui.utils.getCyclePrediction
import com.cs407.uteri.ui.utils.getLastPeriodStart
import com.cs407.uteri.ui.utils.getPeriodDay


@Composable
fun HomePage(
    onNavigateToCalendar: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToTimer: () -> Unit,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val db = remember { DatabaseProvider.getDatabase(context) }
    val viewModel: CalendarViewModel = viewModel(
        factory = CalendarViewModelFactory(db)
    )

    val entries = viewModel.allEntries.collectAsState().value

    val lastStart = getLastPeriodStart(entries)
    val periodDay = getPeriodDay(lastStart)
    val nextPeriod = getCyclePrediction(lastStart)
    val daysUntilNext = daysUntil(nextPeriod)

    Row(horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(24.dp)){
        ProfileButton(onNavigateToProfile = onNavigateToProfile)
    }
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.uteruslogo),
                contentDescription = "Logo",
                modifier = Modifier.size(250.dp)
            )
            Button(
                onClick = {
                    onNavigateToCalendar()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.pink)
                ),
                modifier = Modifier.fillMaxWidth().padding(start=32.dp, end=32.dp).height(94.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar_icon),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column()
                    {
                        Text(text = stringResource(R.string.Calendar), fontSize = 18.sp)
                        Text(text = "Log your period")
                    }
                }
            }
            Spacer(modifier.height(24.dp))
            Button(
                onClick = {
                    onNavigateToMap()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.pink)
                ),
                modifier = Modifier.fillMaxWidth().padding(start=32.dp, end=32.dp).height(94.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.map_icon),
                        contentDescription = "Map",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column()
                    {
                        Text(text = stringResource(R.string.Resource_Map), fontSize = 18.sp)
                        Text(text = "Find Resources")
                    }
                }
            }
            Spacer(modifier.height(24.dp))
            Button(
                onClick = {
                    onNavigateToTimer()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.pink)
                ),
                modifier = Modifier.fillMaxWidth().padding(start=32.dp, end=32.dp).height(94.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.timer_icon),
                        contentDescription = "Timer",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column()
                    {
                        Text(text = stringResource(R.string.Timer), fontSize = 18.sp)
                        Text(text = "Time your period schedule")
                    }
                }
            }
            Spacer(modifier.height(24.dp))
            Text(text = stringResource(R.string.Menstrual_Phase),
                color = colorResource(id = R.color.pink), fontSize = 25.sp)
            Spacer(modifier.height(24.dp))
            MenstrualRing(
                periodDay = periodDay,
                daysUntilNext = daysUntilNext
            )
        }
    }
}