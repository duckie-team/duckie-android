/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.screen.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.feature.ui.home.component.HeadLineTopAppBar
import team.duckie.app.android.feature.ui.home.screen.mypage.section.EditSection
import team.duckie.app.android.feature.ui.home.screen.mypage.section.ExamSection
import team.duckie.app.android.feature.ui.home.screen.mypage.section.FollowSection
import team.duckie.app.android.feature.ui.home.screen.mypage.section.MyFavoriteTagSection
import team.duckie.app.android.feature.ui.home.screen.mypage.section.ProfileSection
import team.duckie.app.android.feature.ui.home.screen.ranking.dummy.skeletonExams
import team.duckie.app.android.shared.ui.compose.Create
import team.duckie.app.android.shared.ui.compose.Notice
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun MyPageScreen(
    navigateToMyPage: () -> Unit,
    navigateToSettingPage: () -> Unit,
    isMe: Boolean = true,
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        HeadLineTopAppBar(
            title = stringResource(id = R.string.mypage),
            rightIcons = {
                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                    QuackImage(
                        src = QuackIcon.Setting,
                        size = DpSize(all = 24.dp),
                        onClick = navigateToSettingPage,
                    )
                    QuackImage(
                        src = QuackIcon.Notice,
                        size = DpSize(all = 24.dp),
                        onClick = navigateToMyPage,
                    )
                }
            },
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(
                    horizontal = 16.dp,
                    vertical = 36.dp,
                ),
        ) {
            ProfileSection(
                profile = "",
                duckPower = "2222",
                follower = 100,
                following = 100,
                introduce = "dd",
                isLoading = false,
            )
            Spacer(space = 20.dp)
            if (isMe) {
                EditSection(
                    onClickEditProfile = { /*TODO*/ },
                    onClickEditTag = {},
                )
            } else {
                FollowSection(
                    enabled = true,
                    onClick = {},
                )
            }
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

