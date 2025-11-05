package com.cs407.uteri

import android.R.attr.text
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs407.uteri.ui.theme.UteriTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UteriTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePage(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomePage(name: String, modifier: Modifier = Modifier) {
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
                onClick = {},
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
                    Column(
                    ) {
                       Text(text = stringResource(R.string.Calendar), fontSize = 18.sp)
                       Text(text = "Log your period")
                    }
                }
            }
            Spacer(modifier.height(2.dp))
            Button(
                onClick = {},
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.pink)
                ),
                modifier = Modifier.fillMaxWidth().padding(start=32.dp, end=32.dp).height(94.dp)
            ) {
            }
            Spacer(modifier.height(2.dp))
            Button(
                onClick = {},
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.pink)
                ),
                modifier = Modifier.fillMaxWidth().padding(start=32.dp, end=32.dp).height(94.dp)
            ) {
            }



        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UteriTheme {
        HomePage("Android")
    }
}