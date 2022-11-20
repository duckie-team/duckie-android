package team.duckie.app.android.ui.main.setting.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import team.duckie.app.R
import team.duckie.app.ui.component.BackArrowTopAppBar
import team.duckie.app.ui.main.setting.component.BaseAppSettingLayout
import team.duckie.app.ui.main.setting.component.PaddingQuackTitle2

@Composable
internal fun SettingMainScreen(
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
    SettingMainScreen(
        onClickBack = { /*TODO*/ },
        onClickAccountInformation = { /*TODO*/ },
        onClickNotification = { /*TODO*/ },
        onClickInquiry = { /*TODO*/ },
    ) {

    }
}
