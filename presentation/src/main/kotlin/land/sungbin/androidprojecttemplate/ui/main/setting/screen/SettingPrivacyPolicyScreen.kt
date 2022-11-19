package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.component.BackArrowTopAppBar
import land.sungbin.androidprojecttemplate.ui.main.setting.component.BaseAppSettingLayout
import team.duckie.quackquack.ui.component.QuackBody1

private const val dummyPrivacyPolicy = "이용약관입니다"

@Composable
fun SettingPrivacyPolicy(
    onClickBack: () -> Unit,
) {

    BackHandler {
        onClickBack()
    }

    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.privacy_policy,
                )
            ) {
                onClickBack()
            }
        },
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        QuackBody1(text = dummyPrivacyPolicy)
    }
}