/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.screen.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.DuckieMedal
import team.duckie.app.android.common.compose.ui.LeftChatBubble
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v2.Crown
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.domain.quiz.model.QuizInfo
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.common.DetailContentLayout
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.shape.SquircleShape
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun QuizDetailContentLayout(
    modifier: Modifier = Modifier,
    state: DetailState.Success,
    tagItemClick: (String) -> Unit,
    moreButtonClick: () -> Unit,
    followButtonClick: () -> Unit,
    profileClick: (Int) -> Unit,
) {
    DetailContentLayout(
        modifier = modifier,
        state = state,
        tagItemClick = tagItemClick,
        moreButtonClick = moreButtonClick,
        followButtonClick = followButtonClick,
        profileClick = profileClick,
        additionalInfo = {
            if (state.exam.quizs.isNullOrEmpty().not()) {
                RankingSection(
                    state = state,
                    userContentClick = profileClick,
                )
            }
        },
    )
}

@Composable
private fun ColumnScope.MyRankingSection(
    quizInfo: QuizInfo?,
) {
    val user = quizInfo?.user

    QuackBody2(text = "내 기록")
    Spacer(space = 4.dp)
    Row(
        modifier = Modifier.height(68.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (quizInfo == null) {
            QuackTitle2(text = "-")
        } else {
            DuckieMedal(score = quizInfo.score.plus(1))
        }
        Spacer(space = 12.dp)
        UserProfileOrDefault(profileImageUrl = user?.profileImageUrl ?: "")
        Spacer(space = 8.dp)
        Column {
            QuackTitle2(text = user?.nickname ?: "")
            QuackText(
                text = buildString {
                    append(stringResource(id = R.string.score, quizInfo?.score ?: "-"))
                    append(" / ")
                    append(stringResource(id = R.string.time, quizInfo?.time ?: "-"))
                },
                typography = QuackTypography.Body1.change(color = QuackColor.Gray1),
            )
        }
    }
}

@Composable
private fun RankingSection(
    state: DetailState.Success,
    userContentClick: (Int) -> Unit,
) {
    val quizRankings = remember(state.exam.quizs) { checkNotNull(state.exam.quizs) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp)
            .padding(horizontal = 16.dp),
    ) {
        QuackText(
            text = stringResource(id = R.string.quiz_ranking_title, state.mainTagNames),
            typography = QuackTypography.Title2,
        )
        Spacer(space = 12.dp)
        MyRankingSection(quizInfo = state.exam.myRecord)
        QuackMaxWidthDivider()
        Spacer(space = 24.dp)
        QuackText(
            text = stringResource(id = R.string.all_rank),
            typography = QuackTypography.Body2.change(
                color = QuackColor.Gray1,
            ),
        )
        Spacer(space = 4.dp)
        quizRankings.fastForEachIndexed { index, item ->
            RankingContent(
                rank = index + 1,
                quizInfo = item,
                onClick = userContentClick,
            )
        }
    }
}

@Composable
fun UserProfileOrDefault(
    size: DpSize = DpSize(44.dp, 44.dp),
    profileImageUrl: String?,

    ) {
    profileImageUrl?.let {
        QuackImage(
            src = it,
            modifier = Modifier
                .size(size)
                .clip(SquircleShape),
            contentScale = ContentScale.FillBounds,
        )
    } ?: QuackImage(
        src = QuackIcon.Profile.drawableId,
        modifier = Modifier
            .size(size)
            .clip(SquircleShape),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
private fun RankingContent(
    rank: Int,
    quizInfo: QuizInfo,
    onClick: (Int) -> Unit,
) = with(quizInfo) {
    val user = quizInfo.user

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .quackClickable(
                    onClick = { onClick(user.id) },
                ),
        ) {
            DuckieMedal(score = rank)
            Spacer(space = 12.dp)
            UserProfileOrDefault(profileImageUrl = user.profileImageUrl)
            Spacer(space = 8.dp)
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (rank == 1) {
                        QuackImage(src = QuackIcon.Crown)
                    }
                    Spacer(space = 2.dp)
                    QuackTitle2(text = user.nickname)
                }
                Spacer(space = 4.dp)
                if (quizInfo.reaction != null) {
                    if (rank == 1) {
                        LeftChatBubble(
                            message = quizInfo.reaction ?: "",
                            backgroundColor = Color(0xFFFFEFCF),
                        )
                    } else {
                        QuackText(
                            text = quizInfo.reaction ?: "",
                            typography = QuackTypography.Body2.change(
                                color = QuackColor.Gray1,
                            ),
                        )
                    }
                }
                Spacer(space = 6.dp)
                if (rank == 1) {
                    QuackText(
                        text = getUserPerformanceString(correctProblemCount, time),
                        typography = QuackTypography.Subtitle2.change(
                            color = QuackColor.DuckieOrange,
                        ),
                    )
                } else {
                    QuackText(
                        text = getUserPerformanceString(correctProblemCount, time),
                        typography = QuackTypography.Body2.change(
                            color = QuackColor.Gray1,
                        ),
                    )
                }
            }
        }

        Divider(color = QuackColor.Gray4.value)
    }
}

@Composable
private fun getUserPerformanceString(correctProblemCount: Int, time: Int) =
    buildString {
        append(stringResource(id = R.string.score, correctProblemCount))
        append(" / ")
        append(stringResource(id = R.string.time, time))
    }
