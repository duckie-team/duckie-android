/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.ui.setting.component.SettingContentLayout
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2

@Composable
fun SettingAccountInfoScreen(
    email: String,
    onClickLogOut: () -> Unit,
    onClickWithdraw: () -> Unit,
) {
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackBody1(text = stringResource(id = R.string.email))
            Spacer(space = 12.dp)
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(
                        color = Color.Yellow,
                        shape = RoundedCornerShape(2.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                QuackImage(
                    modifier = Modifier.size(12.dp, 10.dp),
                    src = R.drawable.ic_setting_kakao,
                )
            }
            Spacer(space = 4.dp)
            QuackBody1(
                text = email,
                color = QuackColor.Gray1,
            )
            QuackDivider(modifier = Modifier.padding(vertical = 16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(rememberAccountInfoItems) { index ->
                    SettingContentLayout(
                        title = stringResource(id = index.first),
                        isBold = false,
                        onClick = index.second,
                    )
                }
            }
        }
    }
}
