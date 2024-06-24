package com.example.compose_speakingtalk

data class OnOffScreenUIState(
    val isOn: Boolean = false,
    val isBluetoothChecked: Boolean = false,
    val isNotificationChecked: Boolean = false,
    val isSenderChecked: Boolean = false,
    val isSendTimeChecked: Boolean = false,
    val sliderValue: Float = 0f,
    val selectedSpeed: String  = "0.5"
)

