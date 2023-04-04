/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.feature.ui.profile.component.BackPressHeadLineTopAppBar
import team.duckie.app.android.feature.ui.profile.dummy.skeletonExams
import team.duckie.app.android.feature.ui.profile.section.ExamSection
import team.duckie.app.android.feature.ui.profile.section.FollowSection
import team.duckie.app.android.feature.ui.profile.section.MyFavoriteTagSection
import team.duckie.app.android.feature.ui.profile.section.ProfileSection
import team.duckie.app.android.shared.ui.compose.Create
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun OtherProfileScreen(
    userProfile: UserProfile,
    isLoading: Boolean,
    follow: Boolean,
    onClickFollow: () -> Unit,
) {
    ProfileScreen(
        userProfile = userProfile,
        isLoading = isLoading,
        editSection = {
            FollowSection(
                enabled = follow,
                onClick = onClickFollow,
            )
        },
        topBar = {
            BackPressHeadLineTopAppBar(
                title = userProfile.user?.nickname ?: "",
                isLoading = isLoading,
            )
        }
    )
}


@Composable
internal fun ProfileScreen(
    userProfile: UserProfile,
    isLoading: Boolean,
    topBar: @Composable () -> Unit,
    editSection: @Composable () -> Unit,
    onClickExam: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        topBar()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(
                    horizontal = 16.dp,
                    vertical = 36.dp,
                ),
        ) {
            with(userProfile) {
                ProfileSection(
                    profile = user?.profileImageUrl ?: "",
                    duckPower = user?.duckPower?.tier ?: "",
                    follower = followerCount,
                    following = followingCount,
                    introduce = user?.introduction ?: "",
                    isLoading = isLoading,
                )
            }
            Spacer(space = 20.dp)
            editSection()
            Spacer(space = 36.dp)
            MyFavoriteTagSection(
                title = "내 관심태그",
                tags = persistentListOf(
                    Tag(0, "태그"), Tag(1, "태그1")
                )
            )
            Spacer(space = 44.dp)
            ExamSection(
                icon = QuackIcon.Create,
                title = stringResource(id = R.string.submitted_exam),
                exams = skeletonExams(),
                onClickShowAll = {},
            )
            Spacer(space = 44.dp)
            ExamSection(
                icon = QuackIcon.Badge.drawableId,
                title = stringResource(id = R.string.solved_exam),
                exams = skeletonExams(),
                onClickShowAll = {},
            )
            Spacer(space = 44.dp)
            ExamSection(
                icon = QuackIcon.Heart.drawableId,
                title = stringResource(id = R.string.hearted_exam),
                exams = skeletonExams(),
                onClickShowAll = {},
            )
        }
    }
}
