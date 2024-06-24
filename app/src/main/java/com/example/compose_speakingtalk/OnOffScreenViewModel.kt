package com.example.compose_speakingtalk

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnOffScreenViewModel: ViewModel() {

    private val _onOffScreeUIState = MutableStateFlow(OnOffScreenUIState())
    val onOffScreenUIState = _onOffScreeUIState.asStateFlow()

    fun updateOnOff(){
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            isOn = !_onOffScreeUIState.value.isOn
        )
    }

    fun updateBluetoothState() {
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            isBluetoothChecked = !_onOffScreeUIState.value.isBluetoothChecked
        )
    }

    fun updateSliderValue(value: Float) {
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            sliderValue = value
        )
    }

    fun updateSelectedSpeed(speed: String) {
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            selectedSpeed = speed
        )
    }

    fun updateNotificationState(isChecked: Boolean) {
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            isNotificationChecked = isChecked
        )
    }

    fun updateSenderState() {
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            isSenderChecked = !_onOffScreeUIState.value.isSenderChecked
        )
    }

    fun updateSendTimeState() {
        _onOffScreeUIState.value = _onOffScreeUIState.value.copy(
            isSendTimeChecked = !_onOffScreeUIState.value.isSendTimeChecked
        )
    }

}
