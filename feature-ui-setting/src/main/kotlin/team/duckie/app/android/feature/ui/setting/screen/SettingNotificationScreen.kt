/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.screen

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
import team.duckie.app.android.feature.ui.setting.constans.SettingNotificationType
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackSwitch

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

@Composable
private fun SettingNotificationLayout(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            QuackBody1(text = title)
            QuackBody2(
                modifier = Modifier.padding(top = 4.dp),
                text = description,
                color = QuackColor.Gray1,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        QuackSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
