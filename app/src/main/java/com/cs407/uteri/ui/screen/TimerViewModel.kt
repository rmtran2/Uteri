package com.cs407.uteri.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel : ViewModel() {

    private val _hoursInput = MutableStateFlow("0")
    val hoursInput: StateFlow<String> = _hoursInput.asStateFlow()

    private val _minutesInput = MutableStateFlow("0")
    val minutesInput: StateFlow<String> = _minutesInput.asStateFlow()

    private val _remainingTime = MutableStateFlow(0L)
    val remainingTime: StateFlow<Long> = _remainingTime.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _showTimerEndDialog = MutableStateFlow(false)
    val showTimerEndDialog: StateFlow<Boolean> = _showTimerEndDialog.asStateFlow()

    private var timerActive = false

    // input updates
    fun updateHours(value: String) {
        if (value.all { it.isDigit() }) _hoursInput.value = value
    }

    fun updateMinutes(value: String) {
        if (value.all { it.isDigit() }) _minutesInput.value = value
    }

    // timer logic
    fun startTimerFromInputs() {
        val hrs = hoursInput.value.toLongOrNull() ?: 0
        val mins = minutesInput.value.toLongOrNull() ?: 0
        val totalSeconds = hrs * 3600 + mins * 60

        if (totalSeconds > 0) {
            startTimer(totalSeconds)
        }
    }
    fun startTimerWithHours(hours: Long) {
        val totalSeconds = hours * 3600
        if (totalSeconds > 0) startTimer(totalSeconds)
    }

    private fun startTimer(totalSeconds: Long) {
        _remainingTime.value = totalSeconds
        _isRunning.value = true
        _showTimerEndDialog.value = false
        timerActive = true

        viewModelScope.launch {
            while (_remainingTime.value > 0 && timerActive) {
                delay(1000)
                _remainingTime.value -= 1
            }

            if (_remainingTime.value == 0L && timerActive) {
                _isRunning.value = false
                _showTimerEndDialog.value = true
            }
        }
    }

    fun stopTimer() {
        timerActive = false
        _isRunning.value = false
    }

    fun dismissDialog() {
        _showTimerEndDialog.value = false
    }
}
