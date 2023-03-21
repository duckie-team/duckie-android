/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.setting.constans.SettingType
import team.duckie.app.android.feature.ui.setting.viewmodel.SettingViewModel
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
fun SettingMainScreen(
    vm: SettingViewModel,
    version: String,
) {
    val settingItems = SettingType.values()

    LazyColumn {
        items(settingItems) { item ->
            SettingContentLayout(
                title = stringResource(id = item.titleRes),
                trailingText = if (item == SettingType.Version) version else null,
                onClick = {
                    vm.navigateStep(item)
                },
            )
        }
    }
}

@Composable
private fun SettingContentLayout(
    title: String,
    trailingText: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .quackClickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackTitle2(
            text = title,
            color = QuackColor.Black,
        )
        if (trailingText != null) {
            QuackBody1(
                text = trailingText,
                color = QuackColor.Gray1,
            )
        }
    }
}
