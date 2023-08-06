/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.centerVertical
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.npe
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.sugar.QuackBody3
import team.duckie.quackquack.ui.sugar.QuackSubtitle2

@Stable
private object UserContentWithButtonDefaults {
    val HomeProfileSize: DpSize = DpSize(32.dp, 32.dp)

    const val ProfileLayoutId = "ProfileLayoutId"
    const val UserNameLayoutId = "UserNameLayoutId"
    const val UserDescriptionLayoutId = "UserDescriptionLayoutId"
    const val TrailingButtonLayoutId = "TrailingButtonLayoutId"
}

/**
 * 해당 버튼을 사용하기 위해선 [trailingButton] 에 modifier 를 넣어야 합니다.
 */
@Composable
private fun DefaultUserContentLayout(
    modifier: Modifier = Modifier,
    userId: Int,
    profileImgUrl: String,
    nickname: String,
    favoriteTag: String,
    tier: String,
    onClickUserProfile: ((Int) -> Unit)? = null,
    visibleTrailingButton: Boolean = false,
    trailingButton: @Composable (modifier: Modifier) -> Unit,
    isLoading: Boolean = false,
) = with(UserContentWithButtonDefaults) {
    Layout(
        modifier = modifier
            .quackClickable(
                onClick = {
                    if (onClickUserProfile != null) {
                        onClickUserProfile(userId)
                    }
                },
            )
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 12.dp)
            .padding(horizontal = 16.dp),
        content = {
            QuackProfileImage(
                modifier = Modifier
                    .layoutId(UserInfoBlockUserProfileLayoutId)
                    .skeleton(isLoading),
                profileUrl = profileImgUrl,
                size = HomeProfileSize,
            )
            QuackSubtitle2(
                modifier = Modifier
                    .layoutId(UserNameLayoutId)
                    .padding(start = 8.dp, top = 2.dp)
                    .skeleton(isLoading),
                text = nickname,
            )
            QuackBody3(
                modifier = Modifier
                    .layoutId(UserDescriptionLayoutId)
                    .padding(start = 8.dp)
                    .skeleton(isLoading),
                text = tier + if (favoriteTag.isNotEmpty()) "· $favoriteTag" else "",
            )
            trailingButton.invoke(
                modifier
                    .layoutId(TrailingButtonLayoutId)
                    .skeleton(isLoading)
            )
        },
        measurePolicy = getUserFollowingLayoutMeasurePolicy(
            nameVisible = tier.isEmpty() || favoriteTag.isEmpty(),
            visibleTrailingButton = visibleTrailingButton,
        ),
    )
}

private fun getUserFollowingLayoutMeasurePolicy(
    nameVisible: Boolean,
    visibleTrailingButton: Boolean,
) = MeasurePolicy { measurables, constraints ->
    with(UserContentWithButtonDefaults) {
        val extraLooseConstraints = constraints.asLoose(width = true)

        val userProfilePlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == ProfileLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        val userNamePlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == UserNameLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        val userDescriptionPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == UserDescriptionLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        val userFollowingButtonPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == TrailingButtonLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            userProfilePlaceable.place(
                x = 0,
                y = 0,
            )

            userNamePlaceable.place(
                x = userProfilePlaceable.width,
                y = if (nameVisible) {
                    constraints.centerVertical(userNamePlaceable.height)
                } else {
                    0
                },
            )

            userDescriptionPlaceable.place(
                x = userProfilePlaceable.width,
                y = userNamePlaceable.height,
            )

            if (visibleTrailingButton) {
                userFollowingButtonPlaceable.place(
                    x = constraints.maxWidth - userFollowingButtonPlaceable.width,
                    y = constraints.centerVertical(userFollowingButtonPlaceable.height),
                )
            }
        }
    }
}


@Composable
fun UserIgnoreLayout(
    modifier: Modifier = Modifier,
    userId: Int,
    profileImgUrl: String,
    nickname: String,
    favoriteTag: String,
    tier: String,
    onClickUserProfile: ((Int) -> Unit)? = null,
    visibleTrailingButton: Boolean = true,
    onClickTrailingButton: (Int) -> Unit,
    isLoading: Boolean = false,
) {
    DefaultUserContentLayout(
        isLoading = isLoading,
        modifier = modifier,
        userId = userId,
        profileImgUrl = profileImgUrl,
        nickname = nickname,
        favoriteTag = favoriteTag,
        tier = tier,
        onClickUserProfile = onClickUserProfile,
        trailingButton = {
            QuackSmallButton(
                modifier = it,
                text = stringResource(id = R.string.cancel_igonre),
                type = QuackSmallButtonType.Border,
                enabled = true,
                onClick = {
                    onClickTrailingButton(userId)
                }
            )
        },
        visibleTrailingButton = visibleTrailingButton,
    )
}


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
    DefaultUserContentLayout(
        isLoading = isLoading,
        modifier = modifier,
        userId = userId,
        profileImgUrl = profileImgUrl,
        nickname = nickname,
        favoriteTag = favoriteTag,
        tier = tier,
        onClickUserProfile = onClickUserProfile,
        trailingButton = {
            QuackText(
                modifier = it
                    .quackClickable(
                        onClick = {
                            onClickTrailingButton(!isFollowing)
                        },
                        rippleEnabled = false,
                    ),
                text = stringResource(id = if (isFollowing) R.string.following else R.string.follow),
                typography = QuackTypography.Body2.change(
                    color = if (isFollowing) QuackColor.Gray1 else QuackColor.DuckieOrange,
                ),
            )
        },
        visibleTrailingButton = visibleTrailingButton,
    )
}
