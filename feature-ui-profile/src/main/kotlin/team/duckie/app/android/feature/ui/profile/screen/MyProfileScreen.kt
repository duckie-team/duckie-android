/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.feature.ui.profile.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.profile.section.EditSection
import team.duckie.app.android.shared.ui.compose.Notice
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

@Composable
fun MyProfileScreen(
    userProfile: UserProfile,
    isLoading: Boolean,
    onClickSetting: () -> Unit,
    onClickNotification: () -> Unit,
    onClickEditProfile: () -> Unit,
    onClickEditTag: () -> Unit,
    onClickExam: () -> Unit,
) {
    ProfileScreen(
        userProfile = userProfile,
        isLoading = isLoading,
        editSection = {
            EditSection(
                onClickEditProfile = onClickEditProfile,
                onClickEditTag = onClickEditTag,
            )
        },
        topBar = {
            HeadLineTopAppBar(
                title = stringResource(id = R.string.mypage),
                rightIcons = {
                    Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        QuackImage(
                            src = QuackIcon.Setting,
                            size = DpSize(all = 24.dp),
                            onClick = onClickNotification,
                        )
                        QuackImage(
                            src = QuackIcon.Notice,
                            size = DpSize(all = 24.dp),
                            onClick = onClickSetting,
                        )
                    }
                },
            )
        },
        onClickExam = onClickExam,
    )
}
