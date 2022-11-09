package land.sungbin.androidprojecttemplate.ui.main.setting.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.component.BackArrowTopAppBar
import land.sungbin.androidprojecttemplate.ui.main.setting.component.BaseAppSettingLayout
import land.sungbin.androidprojecttemplate.ui.main.setting.component.PaddingTitleAndContentText
import team.duckie.quackquack.ui.component.QuackTitle2

@Composable
fun SettingInquiryScreen(
    onClickBack: () -> Unit,
) {
    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.inquiry,
                )
            ) {
                onClickBack()
            }
        },
    ) {

        QuackTitle2(
            modifier = Modifier.padding(
                vertical = 12.dp,
            ),
            text = stringResource(
                id = R.string.contact_us,
            ),
        )

        PaddingTitleAndContentText(
            text = stringResource(
                id = R.string.email,
            ),
            content = "sh007100@naver.com",
        )

        PaddingTitleAndContentText(
            text = stringResource(
                id = R.string.instagram,
            ),
            content = "limsaehyun",
        )
    }
}