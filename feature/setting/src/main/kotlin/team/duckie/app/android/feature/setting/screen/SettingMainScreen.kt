/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.component.SettingContentLayout
import team.duckie.app.android.feature.setting.constans.SettingType
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.quackquack.material.QuackTypography

@Composable
internal fun SettingMainScreen(
    vm: SettingViewModel,
    version: String,
) {
    val userSettings = SettingType.userSettings
    val otherSettings = SettingType.otherSettings

    LazyColumn {
        titleSection(title = R.string.user_settings)
        items(userSettings) { item ->
            SettingContentLayout(
                title = stringResource(id = item.titleRes),
                onClick = { vm.navigateStep(item) },
                typography = QuackTypography.Body1,
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            QuackMaxWidthDivider()
            SettingContentLayout(
                modifier = Modifier.padding(vertical = 12.dp),
                title = stringResource(id = R.string.notification),
                typography = QuackTypography.Body1,
            )
            QuackMaxWidthDivider()
        }
        titleSection(title = R.string.others)
        items(otherSettings) { item ->
            SettingContentLayout(
                title = stringResource(id = item.titleRes),
                typography = QuackTypography.Body1,
                trailingText = if (item == SettingType.Version) version else null,
                onTrailingTextClick = {
                    if (item == SettingType.Version) {
                        vm.changeDevModeDialogVisible(true)
                    }
                },
                onClick = {
                    when (item) {
                        SettingType.Version -> {
                            vm.goToMarket()
                        }

                        else -> {
                            vm.navigateStep(item)
                        }
                    }
                },
            )
        }
    }
}

internal fun LazyListScope.titleSection(
    @StringRes title: Int
) {
    item {
        SettingContentLayout(
            title = LocalContext.current.getString(title),
            typography = QuackTypography.Title2,
        )
    }
}
