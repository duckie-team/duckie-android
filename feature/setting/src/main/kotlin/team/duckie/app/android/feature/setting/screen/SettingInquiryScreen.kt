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
import team.duckie.app.android.feature.setting.R
import team.duckie.app.android.feature.setting.component.SettingContentLayout
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

/**
 * 덕키 공식 이메일
 */
const val DUCKIE_EMAIL = "duckieteam.hi@gmail.com"

/**
 * 덕키 공식 인스타그램 아이디
 */
const val DUCKIE_INSTAGRAM = "@duckie_team"

@Composable
fun SettingInquiryScreen() {
    val rememberInquiryItems: ImmutableList<Pair<Int, String>> = remember {
        persistentListOf(
            R.string.email to DUCKIE_EMAIL,
            R.string.instagram to DUCKIE_INSTAGRAM,
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackText(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(id = R.string.contact_inquiry),
            typography = QuackTypography.Subtitle2,
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(rememberInquiryItems) { item ->
                SettingContentLayout(
                    title = stringResource(id = item.first),
                    content = item.second,
                    isBold = false,
                )
            }
        }
    }
}
