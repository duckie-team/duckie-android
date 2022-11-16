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
import land.sungbin.androidprojecttemplate.ui.main.setting.component.PaddingQuackBody1
import land.sungbin.androidprojecttemplate.ui.main.setting.component.PaddingTitleAndContentText
import team.duckie.quackquack.ui.component.QuackDivider

@Composable
fun SettingDuckieInformationScreen(
    onClickBack: () -> Unit,
    onClickTerms: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickOpenSourceLicense: () -> Unit,
) {

    BackHandler {
        onClickBack()
    }

    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.duckie_information,
                )
            ) {
                onClickBack()
            }
        },
    ) {
        PaddingTitleAndContentText(
            text = stringResource(
                id = R.string.version_information,
            ),
            content = "1.0.0",
        )

        Spacer(modifier = Modifier.height(16.dp))

        QuackDivider()

        Spacer(modifier = Modifier.height(16.dp))

        PaddingQuackBody1(
            text = stringResource(
                id = R.string.terms,
            ),
        )

        PaddingQuackBody1(
            text = stringResource(
                id = R.string.privacy_policy,
            ),
        )

        PaddingQuackBody1(
            text = stringResource(
                id = R.string.open_source_license,
            ),
        )
    }
}