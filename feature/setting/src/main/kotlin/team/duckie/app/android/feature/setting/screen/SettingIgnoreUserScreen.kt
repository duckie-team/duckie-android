/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import team.duckie.app.android.common.compose.ui.content.UserIgnoreLayout
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

@Composable
internal fun SettingIgnoreUserScreen(
    vm: SettingViewModel,
    state: SettingState,
) {
    LaunchedEffect(key1 = Unit) {
        vm.getIgnoreUsers()
    }

    if (state.ignoreUsers.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            QuackText(
                text = stringResource(id = R.string.setting_ignore_user_not_found),
                typography = QuackTypography.HeadLine1.change(
                    color = QuackColor.DuckieOrange
                ),
            )
        }
    } else {
        LazyColumn {
            items(state.ignoreUsers) { item ->
                UserIgnoreLayout(
                    userId = item.id,
                    profileImageIrl = item.profileImageUrl,
                    nickname = item.nickName,
                    favoriteTag = item.duckPower?.tag?.name ?: "",
                    tier = item.duckPower?.tier ?: "",
                    onClickTrailingButton = { userId ->
                        vm.cancelIgnoreUser(userId)
                    },
                    rippleEnabled = false,
                )
            }
        }
    }
}
