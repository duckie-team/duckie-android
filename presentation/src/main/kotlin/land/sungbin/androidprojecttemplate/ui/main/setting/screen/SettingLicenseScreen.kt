package land.sungbin.androidprojecttemplate.ui.main.setting.screen

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

private const val dummyLicensesActivity =
    "오픈소스 라이센스입니다니다."

@Composable
fun SettingLicenseScreen(
    onClickBack: () -> Unit,
) {
    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.open_source_license,
                )
            ) {
                onClickBack()
            }
        },
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        QuackBody1(text = dummyLicensesActivity)
    }
}