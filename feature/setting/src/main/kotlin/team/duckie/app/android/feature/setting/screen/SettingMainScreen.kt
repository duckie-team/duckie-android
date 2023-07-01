/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import team.duckie.app.android.feature.setting.component.SettingContentLayout
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel

@Composable
internal fun SettingMainScreen(
    vm: SettingViewModel,
    version: String,
) {
    val settingItems = SettingType.settingPages

    LazyColumn {
        items(settingItems) { item ->
            SettingContentLayout(
                title = stringResource(id = item.titleRes),
                trailingText = if (item == SettingType.Version) version else null,
                onTrailingTextClick = if (item == SettingType.Version) {
                    {
                        vm.changeDevModeDialogVisible(true)
                    }
                } else {
                    null
                },
                onClick = {
                    if (item !in SettingType.nonClickablePages) {
                        vm.navigateStep(item)
                    }
                },
                isBold = true,
            )
        }
    }
}
