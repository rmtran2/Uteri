package com.cs407.uteri.ui.utils

import com.cs407.uteri.data.LogEntry
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Find the most recent day with flow=true
 */
fun getLastPeriodStart(entries: List<LogEntry>): LocalDate? {
    val sorted = entries.sortedByDescending { it.date }
    for (i in sorted.indices) {
        if (sorted[i].flow) {
            // ensure previous day was NOT a flow day → start of period
            if (i == sorted.size - 1 || !sorted[i + 1].flow) {
                return sorted[i].date
            }
        }
    }
    return null
}

fun getCyclePrediction(lastStart: LocalDate?): LocalDate? {
    if (lastStart == null) return null
    return lastStart.plusDays(28)
}

fun getPeriodDay(lastStart: LocalDate?): Int {
    if (lastStart == null) return -1
    val today = LocalDate.now()
    val diff = ChronoUnit.DAYS.between(lastStart, today).toInt()
    return if (diff >= 0) diff + 1 else -1
}

fun daysUntil(date: LocalDate?): Int? {
    if (date == null) return null
    val today = LocalDate.now()
    return ChronoUnit.DAYS.between(today, date).toInt()
}

enum class CyclePhase {
    MENSTRUAL, FOLLICULAR, OVULATION, LUTEAL, UNKNOWN
}

fun getCyclePhase(periodDay: Int?): CyclePhase {
    if (periodDay == null) return CyclePhase.UNKNOWN

    return when (periodDay) {
        in 1..5 -> CyclePhase.MENSTRUAL
        in 6..13 -> CyclePhase.FOLLICULAR
        in 14..16 -> CyclePhase.OVULATION
        in 17..28 -> CyclePhase.LUTEAL
        else -> CyclePhase.UNKNOWN
    }
}

fun phaseToText(phase: CyclePhase): String {
    return when (phase) {
        CyclePhase.MENSTRUAL -> "Menstrual"
        CyclePhase.FOLLICULAR -> "Follicular"
        CyclePhase.OVULATION -> "Ovulation"
        CyclePhase.LUTEAL -> "Luteal"
        CyclePhase.UNKNOWN -> "Unknown"
    }
}

