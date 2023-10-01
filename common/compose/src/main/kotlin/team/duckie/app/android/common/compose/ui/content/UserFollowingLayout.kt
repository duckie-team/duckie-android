/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.VibrateOnTap
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.modifier.quackClickable

private val ProfileImageSize: DpSize = DpSize(32.dp, 32.dp)

@Composable
fun UserFollowingLayout(
    modifier: Modifier = Modifier,
    userId: Int,
    profileImgUrl: String,
    nickname: String,
    favoriteTag: String,
    tier: String,
    isFollowing: Boolean,
    onClickUserProfile: ((Int) -> Unit)? = null,
    visibleTrailingButton: Boolean = false,
    onClickTrailingButton: (Boolean) -> Unit,
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
            VibrateOnTap { vibrate ->
                QuackText(
                    modifier = Modifier
                        .quackClickable(
                            onClick = {
                                vibrate()
                                onClickTrailingButton(!isFollowing)
                            },
                            rippleEnabled = false,
                        ),
                    text = stringResource(id = if (isFollowing) R.string.following else R.string.follow),
                    typography = QuackTypography.Body2.change(
                        color = if (isFollowing) QuackColor.Gray1 else QuackColor.DuckieOrange,
                    ),
                )
            }
        },
        visibleTrailingButton = visibleTrailingButton,
        leadingImageContent = {
            QuackProfileImage(
                modifier = it,
                profileUrl = profileImgUrl,
                size = ProfileImageSize,
            )
        },
        isTitleCenter = tier.isEmpty() || favoriteTag.isEmpty(),
    )
}
