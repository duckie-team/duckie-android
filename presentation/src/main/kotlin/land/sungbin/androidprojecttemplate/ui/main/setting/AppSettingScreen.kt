package land.sungbin.androidprojecttemplate.ui.main.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.component.BackArrowTopAppBar

@Composable
internal fun AppSettingScreen(
    vm: AppSettingViewModel,
    onClickBack: () -> Unit,
    onClickAccountInformation: () -> Unit,
    onClickNotification: () -> Unit,
    onClickInquiry: () -> Unit,
    onClickDuckieInformation: () -> Unit,
) {
    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.app_settings,
                )
            ) {
                onClickBack()
            }
        },
        content = {
            PaddingQuackTitle2(
                text = stringResource(
                    id = R.string.account_information,
                ),
                onClick = onClickAccountInformation,
            )

            PaddingQuackTitle2(
                text = stringResource(
                    id = R.string.app_settings,
                ),
                onClick = onClickNotification,
            )

            PaddingQuackTitle2(
                text = stringResource(
                    id = R.string.inquiry,
                ),
                onClick = onClickInquiry,
            )

            PaddingQuackTitle2(
                text = stringResource(
                    id = R.string.duckie_information,
                ),
                onClick = onClickDuckieInformation,
            )
        },
    )
}
@Preview
@Composable
fun PreviewAppSettingScreen() {
    AppSettingScreen(
        vm = AppSettingViewModel(),
        onClickBack = { /*TODO*/ },
        onClickAccountInformation = { /*TODO*/ },
        onClickNotification = { /*TODO*/ },
        onClickInquiry = { /*TODO*/ }) {

    }
}