package com.example.compose_speakingtalk

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.ComposespeakingtalkTheme
import com.example.compose.md_theme_light_bottomSheet

@Composable
fun OnOffScreen(
    viewModel: OnOffScreenViewModel,
    onBluetoothSettingClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var onStateValue by rememberSaveable { mutableStateOf(true) }
    val uiState by viewModel.onOffScreenUIState.collectAsStateWithLifecycle()
    Column {
        IconToggleButton(
            checked = onStateValue,
            onCheckedChange = { onStateValue = !onStateValue },
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                tint = md_theme_light_bottomSheet,
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            OnOffSpeakingTalkImage(imageResourceId = if (uiState.isOn) R.drawable.speaking_on else R.drawable.speaking_off)
            OnOffToggleButton(
                isOn = uiState.isOn,
                onCheckedChange = { viewModel.updateOnOff() })
            Spacer(modifier = Modifier.height(20.dp))
            BottomSheetContainer(
                uiState = uiState,
                isChecked = onStateValue,
                onCheckedChange = { onStateValue = !onStateValue },
                onBluetoothSettingClick = onBluetoothSettingClick,
                onSliderValueChanged = viewModel::updateSliderValue,
                onSelectedItemChanged = viewModel::updateSelectedSpeed,
                onNotificationChanged = viewModel::updateNotificationState,
                onSenderCheckChanged = viewModel::updateSenderState,
                onTimeCheckChanged = viewModel::updateSendTimeState,
            )
        }
    }

}

@Composable
fun OnOffSpeakingTalkImage(
    @DrawableRes imageResourceId: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = imageResourceId),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun OnOffToggleButton(
    isOn: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        IconToggleButton(
            checked = isOn,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .size(200.dp)
                .weight(1f)

        ) {
            Image(
                painter = painterResource(id = if (isOn) R.drawable.on else R.drawable.on_trans),
                contentDescription = null,
            )
        }
        IconToggleButton(
            checked = isOn,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .size(200.dp)
                .weight(1.2f)
                .padding(horizontal = 10.dp)
        ) {
            Image(
                painter = painterResource(id = if (!isOn) R.drawable.off else R.drawable.off_trans),
                contentDescription = null,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnOffScreenPreview() {
    ComposespeakingtalkTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}
