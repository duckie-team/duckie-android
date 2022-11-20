package team.duckie.app.android.ui.main.setting.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.R
import team.duckie.app.android.domain.constants.AccountType
import team.duckie.app.ui.component.BackArrowTopAppBar
import team.duckie.app.ui.main.setting.component.BaseAppSettingLayout
import team.duckie.app.ui.main.setting.component.PaddingQuackBody1
import team.duckie.app.ui.main.setting.component.PaddingQuackTitle2
import team.duckie.app.ui.main.setting.component.PaddingTitleAndContentText
import team.duckie.quackquack.ui.component.QuackDivider

@Composable
internal fun SettingAccountInformationScreen(
    onClickBack: () -> Unit,
    onClickLogOut: () -> Unit,
    onClickSignOut: () -> Unit,
    accountType: team.duckie.app.android.domain.constants.AccountType,
    email: String,
) {

    BackHandler {
        onClickBack()
    }

    BaseAppSettingLayout(
        topAppBar = {
            BackArrowTopAppBar(
                text = stringResource(
                    id = R.string.account_information,
                )
            ) {
                onClickBack()
            }
        },
        content = {
            PaddingQuackTitle2(
                text = accountType.kor + stringResource(
                    id = R.string.account,
                ),
            )

            PaddingTitleAndContentText(
                text = stringResource(
                    id = R.string.email,
                ),
                content = email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            QuackDivider()

            Spacer(modifier = Modifier.height(16.dp))

            PaddingQuackBody1(
                text = stringResource(
                    id = R.string.log_out,
                ),
                onClick = onClickLogOut,
            )

            PaddingQuackBody1(
                text = stringResource(
                    id = R.string.sign_out,
                ),
                onClick = onClickSignOut,
            )
        }
    )
}
