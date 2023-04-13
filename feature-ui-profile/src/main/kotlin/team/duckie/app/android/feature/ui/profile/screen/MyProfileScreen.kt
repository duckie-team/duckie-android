/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
package team.duckie.app.android.feature.ui.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.feature.ui.profile.component.EmptyText
import team.duckie.app.android.feature.ui.profile.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.profile.screen.section.EditSection
import team.duckie.app.android.feature.ui.profile.screen.section.ExamSection
import team.duckie.app.android.feature.ui.profile.screen.section.FavoriteTagSection
import team.duckie.app.android.shared.ui.compose.Create
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.shared.ui.compose.Notice
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
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
    onClickFavoriteTag: () -> Unit,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMakeExam: () -> Unit,
    onClickTag: (String) -> Unit,
    onClickFriend: (FriendsType, Int) -> Unit,
) {
    val tags = remember(userProfile.user?.favoriteTags) {
        userProfile.user?.favoriteTags?.toImmutableList() ?: persistentListOf()
    }
    val submittedExams = remember(userProfile.createdExams) {
        /*userProfile.createdExams?.map { it.toUiModel() }?.toImmutableList()
            ?: persistentListOf()*/
        persistentListOf<DuckTestCoverItem>()
    }

    ProfileScreen(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding(),
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
                            onClick = onClickSetting,
                        )
                        QuackImage(
                            src = QuackIcon.Notice,
                            size = DpSize(all = 24.dp),
                            onClick = onClickNotification,
                        )
                    }
                },
            )
        },
        tagSection = {
            FavoriteTagSection(
                title = stringResource(id = R.string.my_favorite_tag),
                tags = tags,
                emptySection = {
                    QuackLargeButton(
                        type = QuackLargeButtonType.Compact,
                        text = stringResource(id = R.string.add_favorite_tag),
                        onClick = onClickFavoriteTag,
                    )
                },
                onClickTag = onClickTag,
            )
        },
        submittedExamSection = {
            ExamSection(
                icon = QuackIcon.Create,
                title = stringResource(id = R.string.submitted_exam),
                exams = submittedExams,
                onClickExam = onClickExam,
                onClickMore = null,
                emptySection = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        EmptyText(message = stringResource(id = R.string.not_yet_submit_exam))
                        Spacer(modifier = Modifier.padding(8.dp))
                        QuackLargeButton(
                            type = QuackLargeButtonType.Compact,
                            text = stringResource(id = R.string.make_exam),
                            onClick = onClickMakeExam,
                        )
                    }
                },
            )
        },
        onClickExam = onClickExam,
        onClickFriend = onClickFriend,
    )
}
