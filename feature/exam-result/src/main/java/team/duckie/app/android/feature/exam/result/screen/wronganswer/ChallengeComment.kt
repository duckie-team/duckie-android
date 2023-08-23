/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.exam.result.screen.wronganswer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.DraggableBox
import team.duckie.app.android.common.compose.PullToDeleteButton
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v2.FilledHeart
import team.duckie.app.android.common.compose.ui.icon.v2.IcBlock24
import team.duckie.app.android.common.compose.ui.icon.v2.IcTrash24
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.animation.animateQuackColorAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.Outlined
import team.duckie.quackquack.material.icon.quackicon.outlined.Flag
import team.duckie.quackquack.material.icon.quackicon.outlined.Heart
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackTitle2

@Composable
internal fun DraggableChallengeComment(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues,
    wrongComment: ExamResultState.Success.ChallengeCommentUiModel,
    onHeartClick: (Int) -> Unit,
    isMine: Boolean,
    onDeleteComment: (() -> Unit)? = null,
    onIgnoreComment: (() -> Unit)? = null,
    onReportComment: (() -> Unit)? = null,
    visibleHeart: Boolean = false,
) {
    val (isRevealed, onChange) = remember { mutableStateOf(false) }

    DraggableBox(
        modifier = modifier,
        isRevealed = isRevealed,
        onRevealedChanged = { onChange(it) },
        backgroundContent = { modifier ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                if (isMine) {
                    PullToDeleteButton(
                        modifier = modifier,
                        icon = QuackIcon.IcTrash24,
                        onClick = {
                            onChange(false)
                            onDeleteComment?.invoke()
                        },
                    )
                } else {
                    PullToDeleteButton(
                        modifier = modifier,
                        icon = QuackIcon.IcBlock24,
                        onClick = {
                            onChange(false)
                            onReportComment?.invoke()
                        },
                    )
                    PullToDeleteButton(
                        modifier = modifier,
                        icon = QuackIcon.Outlined.Flag,
                        onClick = {
                            onChange(false)
                            onIgnoreComment?.invoke()
                        },
                    )
                }
            }
        },
        content = { modifier ->
            ChallengeComment(
                modifier = modifier,
                wrongComment = wrongComment,
                onHeartClick = onHeartClick,
                innerPaddingValues = innerPaddingValues,
                visibleHeart = visibleHeart,
            )
        },
    )
}

@Composable
private fun ChallengeComment(
    modifier: Modifier = Modifier,
    wrongComment: ExamResultState.Success.ChallengeCommentUiModel,
    innerPaddingValues: PaddingValues = PaddingValues(),
    onHeartClick: (Int) -> Unit,
    visibleHeart: Boolean,
) {
    val animateHeartColor =
        animateQuackColorAsState(targetValue = if (wrongComment.isHeart) QuackColor.Gray1 else QuackColor.Gray2)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(QuackColor.White.value)
            .padding(innerPaddingValues)
            .padding(vertical = 8.dp),
    ) {
        QuackProfileImage(
            profileUrl = wrongComment.userProfileImg,
            size = DpSize(width = 40.dp, height = 40.dp),
        )
        Spacer(space = 8.dp)
        Column {
            QuackText(
                text = stringResource(id = R.string.other_answer, wrongComment.userNickname),
                typography = QuackTypography.Body3.change(
                    color = QuackColor.Gray1,
                ),
            )
            Spacer(space = 2.dp)
            QuackTitle2(text = wrongComment.wrongAnswer)
            Spacer(space = 6.dp)
            QuackBody1(text = wrongComment.message)
            Spacer(space = 6.dp)
            QuackText(
                text = wrongComment.createdAt,
                typography = QuackTypography.Body3.change(
                    color = QuackColor.Gray2,
                ),
            )
        }
        Spacer(weight = 1f)
        if (visibleHeart) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                QuackIcon(
                    modifier = Modifier
                        .size(24.dp)
                        .quackClickable(
                            onClick = {
                                onHeartClick(wrongComment.id)
                            },
                        ),
                    icon = if (wrongComment.isHeart) {
                        QuackIcon.FilledHeart
                    } else {
                        QuackIcon.Outlined.Heart
                    },
                )
                QuackText(
                    text = wrongComment.heartCount.toString(),
                    typography = QuackTypography.Body3.change(
                        color = animateHeartColor.value,
                    ),
                )
            }
        }
    }
}
