package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.component.BackArrowTopAppBar
import land.sungbin.androidprojecttemplate.ui.main.setting.component.BaseAppSettingLayout
import team.duckie.quackquack.ui.component.QuackBody1

@Composable
internal fun SettingNotificationScreen(
    onClickBack: () -> Unit,
) {
    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.notification,
                )
            ) {
                onClickBack()
            }
        },
    ) {
        SettingNotificationLayout(
            text = stringResource(
                id = R.string.activity_notifications,
            ),
            checked = true,
            onCheckedChanged = {}
        )

        SettingNotificationLayout(
            text = stringResource(
                id = R.string.message_notifications,
            ),
            checked = true,
            onCheckedChanged = {}
        )
    }
}

@Composable
private fun SettingNotificationLayout(
    text: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(42.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackBody1(text = text)

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
        )
    }
}

@Preview
@Composable
fun PreviewSettingNotificationScreen() {
    SettingNotificationScreen {

    }
}