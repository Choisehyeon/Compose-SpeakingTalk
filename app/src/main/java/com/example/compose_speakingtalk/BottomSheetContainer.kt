package com.example.compose_speakingtalk

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.md_theme_light_bottomSheet
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

            },
            onTargetChanges = { sheetValue ->
                currentSheetTarget = sheetValue
            }
        ) {

        }
    }
}


@Preview
@Composable
fun BottomSheetPreview() {
    var isChecked by remember {
        mutableStateOf(true)
    }
    BottomSheetContainer(isChecked, { isChecked != isChecked })
}
