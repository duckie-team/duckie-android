/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail.screen.music

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.DuckieMedal
import team.duckie.app.android.common.compose.ui.QuackMaxWidthDivider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v2.Crown
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.common.kotlin.isFirstRanked
import team.duckie.app.android.common.kotlin.isTopRanked
import team.duckie.app.android.domain.exam.model.MusicExamInstance
import team.duckie.app.android.domain.exam.model.MyMusicRecord
import team.duckie.app.android.domain.examInstance.model.ExamStatus
import team.duckie.app.android.feature.detail.R
import team.duckie.app.android.feature.detail.screen.quiz.ProfileImageSize
import team.duckie.app.android.feature.detail.viewmodel.state.DetailState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun MusicRankingSection(
    modifier: Modifier = Modifier,
    state: DetailState.Success,
    userContentClick: (Int) -> Unit,
) {
    val musicExamInstances = state.exam.musicExamInstances

    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            QuackHeadLine2(
                text = stringResource(id = R.string.music_ranking_title, state.mainTagNames),
            )
            Spacer(space = 12.dp)
            MyRankingSection(
                nickname = state.appUser.nickname,
                myMusicRecord = state.exam.myMusicRecord,
            )
            QuackMaxWidthDivider()
            Spacer(space = 24.dp)
            QuackText(
                text = stringResource(id = R.string.all_rank),
                typography = QuackTypography.Body2.change(
                    color = QuackColor.Gray1,
                ),
            )
            Spacer(space = 4.dp)
        }
        if (musicExamInstances.isNullOrEmpty()) {
            QuackText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.detail_ranker_not_found),
                typography = QuackTypography.Title2.change(
                    color = QuackColor.Gray1,
                    textAlign = TextAlign.Center,
                ),
            )
        } else {
            musicExamInstances.fastForEachIndexed { index, item ->
                key(item.user) {
                    RankingContent(
                        rank = index + 1,
                        musicExamInstance = item,
                        onClick = userContentClick,
                    )
                }
            }
        }
        Spacer(space = 26.dp)
    }
}

@Composable
private fun ColumnScope.MyRankingSection(
    nickname: String,
    myMusicRecord: MyMusicRecord?,
) {
    QuackBody2(text = stringResource(id = R.string.my_record))
    Spacer(space = 4.dp)
    Row(
        modifier = Modifier.height(68.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (myMusicRecord == null) {
            QuackTitle2(text = "-")
        } else {
            DuckieMedal(score = myMusicRecord.ranking ?: 0)
        }
        Spacer(space = 12.dp)
        QuackProfileImage(
            profileUrl = myMusicRecord?.user?.profileImageUrl ?: "",
            size = DpSize(width = 44.dp, height = 44.dp),
        )
        Spacer(space = 8.dp)
        Column {
            QuackTitle2(text = nickname)
            QuackText(
                text = buildString {
                    append(
                        stringResource(
                            id = R.string.score,
                            myMusicRecord?.correctProblemCount ?: "-",
                        ),
                    )
                    append(" / ")
                    append(stringResource(id = R.string.time, myMusicRecord?.takenTime ?: "-"))
                },
                typography = QuackTypography.Body1.change(color = QuackColor.Gray1),
            )
        }
    }
}

@AllowMagicNumber
@Composable
private fun RankingContent(
    rank: Int,
    musicExamInstance: MusicExamInstance,
    onClick: (Int) -> Unit,
) = with(musicExamInstance) {
    val user = musicExamInstance.user
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .quackClickable(
                    onClick = { onClick(user.id) },
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DuckieMedal(score = rank)
                Spacer(space = 12.dp)
                QuackProfileImage(
                    profileUrl = user.profileImageUrl,
                    size = ProfileImageSize,
                )
            }
            Spacer(space = 8.dp)
            Column(
                modifier = Modifier.heightIn(min = ProfileImageSize.height),
                verticalArrangement = Arrangement.Top,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (rank.isFirstRanked()) {
                        QuackIcon(icon = QuackIcon.Crown)
                        Spacer(space = 2.dp)
                    }
                    QuackTitle2(text = user.nickname)
                }
                Spacer(space = 6.dp)
                if (rank.isFirstRanked()) {
                    QuackText(
                        text = stringResource(id = R.string.detail_first_rank_history)
                                + getUserPerformanceString(
                            score = score ?: 0.0,
                            correctProblemCount = correctProblemCount ?: 0,
                            takenTime = takenTime ?: 0.0,
                        ),
                        typography = QuackTypography.Subtitle2.change(
                            color = QuackColor.DuckieOrange,
                        ),
                    )
                } else if (rank.isTopRanked() && !rank.isFirstRanked()) {
                    QuackText(
                        text = getUserPerformanceString(
                            score = score ?: 0.0,
                            correctProblemCount = correctProblemCount ?: 0,
                            takenTime = takenTime ?: 0.0,
                        ),
                        typography = QuackTypography.Body2.change(
                            color = QuackColor.Gray1,
                        ),
                    )
                }
                Spacer(space = 2.dp)
                StatusText(examStatus = musicExamInstance.status ?: ExamStatus.Ready)
            }
        }
        Divider(color = QuackColor.Gray4.value)
    }
}

@Composable
private fun StatusText(examStatus: ExamStatus) {
    val textToColor = when (examStatus) {
        ExamStatus.Ready -> {
            R.string.detail_music_solving to QuackColor.DuckieOrange
        }

        ExamStatus.Submitted -> {
            R.string.detail_music_complete_solve to QuackColor.Success
        }
    }
    QuackText(
        text = stringResource(id = textToColor.first),
        typography = QuackTypography.Body3.change(
            color = textToColor.second,
        ),
    )
}


@Composable
private fun getUserPerformanceString(
    score: Double,
    correctProblemCount: Int,
    takenTime: Double,
) =
    buildString {
        append(stringResource(id = R.string.score, takenTime))
        append(stringResource(id = R.string.amount, correctProblemCount))
        append(" / ")
        append(stringResource(id = R.string.time, score))
    }

