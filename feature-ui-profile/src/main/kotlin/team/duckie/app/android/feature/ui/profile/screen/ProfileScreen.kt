/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.user.model.UserProfile
import team.duckie.app.android.feature.ui.profile.R
import team.duckie.app.android.feature.ui.profile.component.EmptyText
import team.duckie.app.android.feature.ui.profile.screen.section.ExamSection
import team.duckie.app.android.feature.ui.profile.screen.section.ProfileSection
import team.duckie.app.android.feature.ui.profile.viewmodel.mapper.toPresentation
import team.duckie.app.android.shared.ui.compose.DuckTestCoverItem
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    isLoading: Boolean,
    topBar: @Composable () -> Unit,
    editSection: @Composable () -> Unit,
    tagSection: @Composable () -> Unit,
    submittedExamSection: @Composable () -> Unit,
    onClickExam: (DuckTestCoverItem) -> Unit,
    onClickMore: (() -> Unit)? = null,
) {
    val scrollState = rememberScrollState()
    val solvedExams = remember(userProfile.solvedExamInstances) {
        userProfile.solvedExamInstances?.map { it.toPresentation() }
            ?.toImmutableList()
            ?: persistentListOf()
    }
    val heartedExams = remember(userProfile.heartExams) {
        userProfile.heartExams?.map { it.toPresentation() }?.toImmutableList()
            ?: persistentListOf()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .systemBarsPadding()
            .navigationBarsPadding(),
    ) {
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
                    introduce = user?.introduction
                        ?: stringResource(id = R.string.please_input_introduce),
                    isLoading = isLoading,
                )
            }
            Spacer(space = 20.dp)
            editSection()
            Spacer(space = 36.dp)
            tagSection()
            Spacer(space = 44.dp)
            submittedExamSection()
            Spacer(space = 44.dp)
            ExamSection(
                icon = QuackIcon.Badge.drawableId,
                title = stringResource(id = R.string.solved_exam),
                exams = solvedExams,
                onClickExam = onClickExam,
                onClickMore = onClickMore,
                emptySection = {
                    EmptyText(message = stringResource(id = R.string.not_yet_submit_exam))
                },
            )
            Spacer(space = 44.dp)
            ExamSection(
                icon = QuackIcon.Heart.drawableId,
                title = stringResource(id = R.string.hearted_exam),
                exams = heartedExams,
                onClickExam = onClickExam,
                onClickMore = onClickMore,
                emptySection = {
                    EmptyText(message = stringResource(id = R.string.not_yet_heart_exam))
                },
            )
        }
    }
}
