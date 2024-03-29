/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
@file:Suppress("UnusedPrivateMember")

package team.duckie.app.android.feature.setting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.setting.constans.SettingDesignToken
import team.duckie.app.android.feature.setting.constans.SettingNotificationType
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody1

/**
 * 알림 설정 화면
 *
 * TODO(limsaehyun): 현재는 UI만 구성된 상태
 */
@Composable
fun SettingNotificationScreen() {
    val settingNotifications = SettingNotificationType.values()

    Spacer(modifier = Modifier.height(12.dp))
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(settingNotifications) { item ->
            SettingNotificationLayout(
                title = stringResource(id = item.title),
                description = stringResource(id = item.description),
                checked = false,
                onCheckedChange = { },
            )
        }
    }
}

@Suppress("unused")
@Composable
private fun SettingNotificationLayout(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) = with(SettingDesignToken) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            QuackBody1(
                text = title,
            )
            QuackText(
                modifier = Modifier.padding(top = 4.dp),
                text = description,
                typography = SettingVerticalResultTypography,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
//        QuackSwitch(
//            checked = checked,
//            onCheckedChange = onCheckedChange,
//        )
// TODO(limsaehyun) [QuackQuack] Switch 작업 필요!
    }
}
