/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.component.SettingContentLayout
import team.duckie.app.android.feature.setting.constans.SettingDesignToken
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.sugar.QuackSubtitle2

// private val KakaoColor: Color = Color(0xFFFEE500)

@Suppress("UnusedPrivateMember") // TODO(limsaehyun) 추후 이메일 작업 필요
@Composable
fun SettingAccountInfoScreen(
    email: String,
    onClickLogOut: () -> Unit,
    onClickWithdraw: () -> Unit,
) = with(SettingDesignToken) {
    val rememberAccountInfoItems: ImmutableList<Pair<Int, () -> Unit>> = remember {
        persistentListOf(
            R.string.log_out to onClickLogOut,
            R.string.withdraw to onClickWithdraw,
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackSubtitle2(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(id = R.string.sign_in_account),
        )
//        이메일 로직: 필요시 주석 해제 후 사용
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(44.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            QuackBody1(
//                text = stringResource(id = R.string.email),
//            )
//            Spacer(space = 12.dp)
//            Box(
//                modifier = Modifier
//                    .size(18.dp)
//                    .background(
//                        color = KakaoColor,
//                        shape = RoundedCornerShape(2.dp),
//                    ),
//                contentAlignment = Alignment.Center,
//            ) {
//                QuackImage(
//                    modifier = Modifier.size(12.dp, 10.dp),
//                    src = R.drawable.ic_setting_kakao,
//                )
//            }
//            Spacer(space = 4.dp)
//            QuackText(
//                text = email,
//                typography = SettingHorizontalResultTypography,
//            )
//        }
        QuackMaxWidthDivider(modifier = Modifier.padding(vertical = 16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(rememberAccountInfoItems) { index ->
                SettingContentLayout(
                    title = stringResource(id = index.first),
                    onClick = index.second,
                    typography = QuackTypography.Body1,
                )
            }
        }
    }
}
