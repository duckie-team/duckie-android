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
import androidx.compose.runtime.LaunchedEffect
import team.duckie.app.android.common.compose.ui.content.UserIgnoreLayout
import team.duckie.app.android.feature.setting.viewmodel.SettingViewModel
import team.duckie.app.android.feature.setting.viewmodel.state.SettingState

@Composable
internal fun SettingIgnoreUserScreen(
    vm: SettingViewModel,
    state: SettingState,
) {
    LaunchedEffect(key1 = Unit) {
        vm.getIgnoreUsers()
    }

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
