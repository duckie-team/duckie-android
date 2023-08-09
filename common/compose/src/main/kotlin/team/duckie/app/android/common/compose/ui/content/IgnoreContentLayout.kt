/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.common.compose.ui.content

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.CoverImageRatio
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private val ProfileImageSize: DpSize = DpSize(32.dp, 32.dp)

@Composable
fun ExamIgnoreLayout(
    modifier: Modifier = Modifier,
    examId: Int,
    examThumbnailUrl: String,
    name: String,
    likeNum: Int? = null,
    solverNum: Int? = null,
    onClickUserProfile: ((Int) -> Unit)? = null,
    visibleTrailingButton: Boolean = true,
    onClickTrailingButton: (Int) -> Unit,
    isLoading: Boolean = false,
    rippleEnabled: Boolean = false,
) {
    BasicContentWithButtonLayout(
        isLoading = isLoading,
        modifier = modifier,
        contentId = examId,
        nickname = name,
        onClickLayout = onClickUserProfile,
        description = buildString {
            append(solverNum ?: "")
            append(if (likeNum != null) "· 좋아요 $likeNum" else "")
        },
        trailingButton = {
            QuackButton( // FIXME(limsaehyun) enabled 버튼 모양이 disabled로 바뀌어버림
                modifier = it.quackClickable {
                    onClickTrailingButton(examId)
                },
                text = stringResource(id = R.string.cancel_igonre),
                style = QuackButtonStyle.PrimaryOutlinedSmall,
                onClick = { },
                enabled = false,
            )
        },
        visibleTrailingButton = visibleTrailingButton,
        leadingImageContent = {
            QuackImage(
                modifier = it
                    .width(68.dp)
                    .aspectRatio(CoverImageRatio),
                src = examThumbnailUrl,
                contentScale = ContentScale.Crop,
            )
        },
        isTitleCenter = likeNum == null && solverNum == null,
        visibleHorizontalPadding = false,
        rippleEnabled = rippleEnabled,
    )
}

@Composable
fun UserIgnoreLayout(
    modifier: Modifier = Modifier,
    userId: Int,
    profileImageIrl: String,
    nickname: String,
    favoriteTag: String,
    rippleEnabled: Boolean = true,
    tier: String,
    onClickUserProfile: ((Int) -> Unit)? = null,
    visibleTrailingButton: Boolean = true,
    onClickTrailingButton: (Int) -> Unit,
    isLoading: Boolean = false,
) {
    BasicContentWithButtonLayout(
        isLoading = isLoading,
        modifier = modifier,
        contentId = userId,
        nickname = nickname,
        description = tier + if (favoriteTag.isNotEmpty()) "· $favoriteTag" else "",
        onClickLayout = onClickUserProfile,
        trailingButton = {
            QuackButton( // FIXME(limsaehyun) enabled 버튼 모양이 disabled로 바뀌어버림
                modifier = it.quackClickable {
                    onClickTrailingButton(userId)
                },
                text = stringResource(id = R.string.cancel_igonre),
                style = QuackButtonStyle.PrimaryOutlinedSmall,
                onClick = { },
                enabled = false,
            )
        },
        visibleTrailingButton = visibleTrailingButton,
        leadingImageContent = {
            QuackProfileImage(
                modifier = it,
                profileUrl = profileImageIrl,
                size = ProfileImageSize,
            )
        },
        isTitleCenter = tier.isEmpty() || favoriteTag.isEmpty(),
        visibleHorizontalPadding = false,
        rippleEnabled = rippleEnabled,
    )
}
