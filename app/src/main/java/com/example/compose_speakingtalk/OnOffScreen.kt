package com.example.compose_speakingtalk

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ComposespeakingtalkTheme

@Composable
fun OnOffScreen(modifier: Modifier = Modifier) {
    var onStateValue by rememberSaveable { mutableStateOf(true) }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        OnOffSpeakingTalkImage(imageResourceId = if (onStateValue) R.drawable.speaking_on else R.drawable.speaking_off)
        OnOffToggleButton(isOn = onStateValue, onCheckedChange = { onStateValue = !onStateValue })
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
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OnOffScreen()
        }
    }
}
