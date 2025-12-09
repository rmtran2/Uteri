package com.cs407.uteri.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun MenstrualRing(
    periodDay: Int?,
    daysUntilNext: Int?
) {
    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            val radius = size.minDimension / 2 - 16.dp.toPx()
            val stroke = 14.dp.toPx()
            val center = Offset(size.width / 2, size.height / 2)
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2, radius * 2)

            // Base cycle ring (full)
            drawArc(
                color = Color(0xFFFFB7CE),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(stroke, cap = StrokeCap.Round)
            )

            // Period arc (first 7 days or as long as flow)
            if (periodDay != null && periodDay in 1..7) {
                val sweep = (periodDay / 7f) * 360f
                drawArc(
                    color = Color(0xFFFF4F6E),
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(stroke, cap = StrokeCap.Round)
                )
            }
        }

        // Text inside ring
        when {
            periodDay != null && periodDay > 0 && periodDay <= 7 ->
                Text("Period Day $periodDay")

            daysUntilNext != null && daysUntilNext >= 0 ->
                Text("Next in\n$daysUntilNext days")

            else ->
                Text("No data\nyet")
        }
    }
}
