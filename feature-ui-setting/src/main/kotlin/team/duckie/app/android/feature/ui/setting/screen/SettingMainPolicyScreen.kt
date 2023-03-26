/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.ui.setting.component.SettingContentLayout
import team.duckie.app.android.feature.ui.setting.constans.SettingType
import team.duckie.app.android.shared.ui.compose.Spacer

@Composable
fun SettingMainPolicyScreen(
    navigatePage: (SettingType) -> Unit,
    navigateOssLicense: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(space = 12.dp)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(SettingType.policyPages) { page ->
                SettingContentLayout(
                    title = stringResource(id = page.titleRes),
                    isBold = false,
                ) {
                    navigatePage(page)
                }
            }
            item {
                SettingContentLayout(
                    title = stringResource(
                        id = R.string.open_source_license,
                    ),
                    isBold = false,
                ) {
                    navigateOssLicense()
                }
            }
        }
    }
}
