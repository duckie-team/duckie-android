/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.screen.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.domain.quiz.model.QuizInfo
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.common.DetailContentLayout
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.app.android.shared.ui.compose.Crown
import team.duckie.app.android.shared.ui.compose.Spacer
import team.duckie.app.android.util.kotlin.fastForEachIndexed
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.util.DpSize

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
private fun RankingSection(
    state: DetailState.Success,
    userContentClick: (Int) -> Unit,
) {
    val quizRankings = remember { checkNotNull(state.exam.quizs) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 28.dp),
    ) {
        QuackTitle2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.quiz_ranking_title, state.mainTagNames),
        )
        Spacer(space = 8.dp)
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
private fun RankingContent(
    rank: Int,
    quizInfo: QuizInfo,
    onClick: (Int) -> Unit,
) = with(quizInfo) {
    val textColor = if (rank == 1) {
        QuackColor.DuckieOrange
    } else {
        QuackColor.Black
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .quackClickable {
                    onClick(user.id)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(
                        start = 16.dp,
                        end = 8.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.widthIn(min = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (rank == 1) {
                        QuackImage(
                            src = QuackIcon.Crown,
                            size = DpSize(all = 12.dp),
                        )
                    }
                    QuackSubtitle(
                        text = "${rank}등",
                        color = textColor,
                    )
                }
                Spacer(space = 12.dp)
                QuackImage(
                    src = user.profileImageUrl,
                    shape = SquircleShape,
                    size = DpSize(all = 44.dp),
                )
                Spacer(space = 8.dp)
                QuackTitle2(
                    text = user.nickname,
                    color = textColor,
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                user.duckPower?.tier?.let {
                    QuackBody2(text = it)
                    QuackBody2(text = "|")
                }
                QuackBody2(text = "${time}초")
            }
        }
        Divider(color = QuackColor.Gray4.composeColor)
    }
}
