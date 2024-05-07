package com.example.compose_speakingtalk

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.md_theme_light_background
import com.example.compose.md_theme_light_bottomSheet
import com.example.compose.md_theme_light_bottomSheetSetting
import com.example.compose.md_theme_light_bottomSheetSettingUnder
import com.example.compose.md_theme_light_switch_thumb
import com.example.compose.md_theme_light_switch_uncheck_track
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetValue
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState

@Composable
fun BottomSheetContainer(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    var currentSheetTarget by remember {
        mutableStateOf(FlexibleSheetValue.FullyExpanded)
    }
    val state = rememberScrollState()


    val sheetState = rememberFlexibleBottomSheetState(
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.88f,
        ),
        isModal = false,
        skipIntermediatelyExpanded = true,
        skipSlightlyExpanded = true,
    )

    if (!isChecked) {
        FlexibleBottomSheet(
            sheetState = sheetState,
            containerColor = md_theme_light_bottomSheet,
            onDismissRequest = {
                onCheckedChange(isChecked)
            },
            onTargetChanges = { sheetValue ->
                currentSheetTarget = sheetValue
            },
        ) {
            Column(modifier = Modifier.verticalScroll(state)) {
                SettingBluetoothItem(title = "블루투스 연결")
                SettingSound(title = "사운드 설정")
                SettingTextContent(title = "텍스트 내용 설정")
            }
        }
    }
}

@Composable
fun SettingBluetoothItem(title: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }

    Column {
        Card(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(3.dp, color = md_theme_light_background),
            colors = CardDefaults.cardColors(
                containerColor = md_theme_light_bottomSheetSetting
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp,
                    end = 10.dp
                )
            ) {
                Title(title, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.weight(1f))
                ExpandButton(expanded = expanded, onClick = { expanded = !expanded })
            }
        }
        if (expanded) {
            Column {
                SettingToggleItem(
                    title = "현재 상태",
                    checked = checked,
                    onCheckedChange = { checked = !checked })
                SettingText(
                    title = "연결된 기기",
                    value = "",
                )
                SettingIcon(
                    title = "블루투스 설정"
                )
            }
        }
    }
}

@Composable
fun SettingSound(title: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var spinnerExpended by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0.5f) }
    var checkedNotification by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("0.5") }

    Column {
        Card(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(3.dp, color = md_theme_light_background),
            colors = CardDefaults.cardColors(
                containerColor = md_theme_light_bottomSheetSetting
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp,
                    end = 10.dp
                )
            ) {
                Title(title, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.weight(1f))
                ExpandButton(expanded = expanded, onClick = { expanded = !expanded })
            }
        }
        if (expanded) {
            Column {
                SettingSlider(
                    title = "볼륨",
                    sliderPosition = sliderValue,
                    onValueChange = { sliderValue = it })

                SettingSpinner(
                    title = "읽기 속도",
                    expanded = spinnerExpended,
                    selectedText = selectedText,
                    onValueChange = { selectedText = it },
                    onExpandedChange = { spinnerExpended = !spinnerExpended },
                    onDismissRequest = { spinnerExpended = false }
                )

                SettingToggleItem(
                    title = "진동 알림",
                    checked = checkedNotification,
                    onCheckedChange = { checkedNotification = !checkedNotification })
            }
        }
    }
}

@Composable
fun SettingTextContent(title: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var checkedSender by remember { mutableStateOf(false) }
    var checkedTime by remember { mutableStateOf(false) }

    Column {
        Card(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(3.dp, color = md_theme_light_background),
            colors = CardDefaults.cardColors(
                containerColor = md_theme_light_bottomSheetSetting
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp,
                    end = 10.dp
                )
            ) {
                Title(title, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.weight(1f))
                ExpandButton(expanded = expanded, onClick = { expanded = !expanded })
            }
        }
        if (expanded) {
            Column(modifier = Modifier.padding(bottom = 20.dp)) {
                SettingToggleItem(
                    title = "발신자",
                    checked = checkedSender,
                    onCheckedChange = { checkedSender = !checkedSender })
                SettingToggleItem(
                    title = "발신 시간",
                    checked = checkedTime,
                    onCheckedChange = { checkedTime = !checkedTime })
            }
        }
    }
}

@Composable
fun Title(title: String, modifier: Modifier) {
    Text(
        text = title,
        fontSize = 24.sp,
        color = md_theme_light_background,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
}

@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "",
            tint = md_theme_light_background,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun SettingToggleItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 20.dp, top = 5.dp, end = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_bottomSheetSettingUnder
        ),
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = md_theme_light_bottomSheetSetting,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = md_theme_light_switch_thumb,
                    uncheckedThumbColor = md_theme_light_switch_thumb,
                    uncheckedBorderColor = md_theme_light_switch_uncheck_track,
                    checkedTrackColor = md_theme_light_background,
                    uncheckedTrackColor = md_theme_light_switch_uncheck_track,
                )
            )
        }
    }
}

@Composable
fun SettingText(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 20.dp, top = 5.dp, end = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_bottomSheetSettingUnder
        ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = md_theme_light_bottomSheetSetting,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = value,
                fontSize = 20.sp,
                color = md_theme_light_bottomSheetSetting,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun SettingIcon(
    title: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 20.dp, top = 5.dp, end = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_bottomSheetSettingUnder
        ),
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = md_theme_light_bottomSheetSetting,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = null,
                    tint = md_theme_light_bottomSheetSetting
                )
            }
        }
    }
}

@Composable
fun SettingSlider(
    title: String,
    sliderPosition: Float,
    onValueChange: (Float) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 20.dp, top = 5.dp, end = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_bottomSheetSettingUnder
        ),
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = md_theme_light_bottomSheetSetting,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Spacer(Modifier.weight(1f))
            Slider(
                value = sliderPosition,
                onValueChange = onValueChange,
                colors = SliderDefaults.colors(
                    thumbColor = md_theme_light_background,
                    activeTrackColor = md_theme_light_background,
                ),
                modifier = Modifier.padding(start = 100.dp)
            )
        }
    }
}

@Composable
fun SettingSpinner(
    title: String,
    expanded: Boolean,
    selectedText: String,
    onValueChange: (String) -> Unit,
    onExpandedChange: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    val speeds = listOf(0.5, 1.0, 1.5, 2.0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 20.dp, top = 5.dp, end = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_bottomSheetSettingUnder
        ),
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = md_theme_light_bottomSheetSetting,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Spacer(Modifier.weight(1f))
            BasicTextField(
                value = selectedText,
                onValueChange = onValueChange,
                readOnly = true,
                textStyle = TextStyle(color = Color.White, fontSize = 15.sp),
                modifier = Modifier
                    .height(50.dp)
                    .width(80.dp),
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(modifier = Modifier.weight(1f)){
                            innerTextField()
                        }
                        IconButton(onClick = onExpandedChange, modifier = Modifier.size(24.dp).weight(1f)) {
                            Icon(
                                imageVector = icon,
                                tint = Color.White,
                                contentDescription = null,
                            )

                        }
                        DropdownMenu(
                            expanded = expanded, onDismissRequest = onDismissRequest,
                            modifier = Modifier.background(color = md_theme_light_bottomSheetSetting)
                        ) {
                            speeds.forEachIndexed { _, d ->
                                DropdownMenuItem(
                                    text = { Text(text = "$d", color = md_theme_light_background) },
                                    onClick = { onValueChange("$d")
                                    onExpandedChange()})
                            }
                        }
                    }
                })
        }
    }
}

@Composable
fun SettingDropdown() {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemPreview() {
    SettingSound(title = "사운드 설정")
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomSheetPreview() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    BottomSheetContainer(isChecked, { isChecked != isChecked })
}
