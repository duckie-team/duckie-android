/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.centerVertical
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.util.DpSize

private val HomeProfileSize: DpSize = DpSize(
    all = 32.dp,
)

private const val UserInfoBlockUserProfileLayoutId = "UserInfoContentUserProfile"
private const val UserInfoBlockUserNameLayoutId = "UserInfoBlockUserName"
private const val UserInfoBlockUserDescriptionLayoutId = "UserInfoBlockUserDescription"
private const val UserInfoBlockFollowingButtonLayoutId = "UserInfoBlockFollowingButton"

/**
 * 유저를 추천할 때 사용하는 유저 팔로잉 레이아웃입니다
 * 유저에 대한 기본적인 정보를 확인할 수 있으며 팔로우/언팔로우 기능이 있습니다.
 *
 * @param userId 유저의 id
 * @param profileImgUrl 유저 프로필 이미지 url
 * @param nickname 닉네임
 * @param favoriteTag 관심 태그
 * @param tier 현재 덕력
 * @param isMine 추천 유저가 본인인지 여부
 * @param isFollowing 팔로우 여부
 * @param onClickUserProfile 프로필 사진을 클릭 했을 때 실행되는 람다
 * @param onClickFollow 팔로우 버튼을 클릭 했을 때 실행되는 람다
 */
@Composable
fun UserFollowingLayout(
    modifier: Modifier = Modifier,
    userId: Int,
    profileImgUrl: String,
    nickname: String,
    favoriteTag: String,
    tier: String,
    isMine: Boolean = false,
    isFollowing: Boolean,
    onClickUserProfile: ((Int) -> Unit)? = null,
    onClickFollow: (Boolean) -> Unit,
    isLoading: Boolean = false,
) {
    Layout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 12.dp)
            .quackClickable(
                rippleEnabled = false,
            ) {
                if (onClickUserProfile != null) {
                    onClickUserProfile(userId)
                }
            },
        content = {
            if (profileImgUrl.isEmpty()) {
                Image(
                    modifier = Modifier
                        .layoutId(UserInfoBlockUserProfileLayoutId)
                        .size(HomeProfileSize)
                        .clip(SquircleShape)
                        .skeleton(isLoading),
                    painter = painterResource(id = QuackIcon.DefaultProfile),
                    contentDescription = null,
                )
            } else {
                QuackImage(
                    modifier = Modifier
                        .layoutId(UserInfoBlockUserProfileLayoutId)
                        .skeleton(isLoading),
                    src = profileImgUrl,
                    size = HomeProfileSize,
                    shape = SquircleShape,
                    onClick = {
                        if (onClickUserProfile != null) {
                            onClickUserProfile(userId)
                        }
                    },
                )
            }
            QuackSubtitle2(
                modifier = Modifier
                    .layoutId(UserInfoBlockUserNameLayoutId)
                    .padding(start = 8.dp, top = 2.dp)
                    .skeleton(isLoading),
                text = nickname,
            )
            QuackBody3(
                modifier = Modifier
                    .layoutId(UserInfoBlockUserDescriptionLayoutId)
                    .padding(start = 8.dp)
                    .skeleton(isLoading),
                text = tier + if (favoriteTag.isNotEmpty()) "· $favoriteTag" else "",
            )
            QuackBody2(
                modifier = Modifier
                    .layoutId(UserInfoBlockFollowingButtonLayoutId)
                    .skeleton(isLoading),
                text = stringResource(id = if (isFollowing) R.string.following else R.string.follow),
                color = if (isFollowing) QuackColor.Gray1 else QuackColor.DuckieOrange,
                onClick = {
                    onClickFollow(!isFollowing)
                },
                rippleEnabled = false,
            )
        },
        measurePolicy = getUserFollowingLayoutMeasurePolicy(
            nameVisible = tier.isEmpty() || favoriteTag.isEmpty(),
            isMine = isMine,
        ),
    )
}

private fun getUserFollowingLayoutMeasurePolicy(
    nameVisible: Boolean,
    isMine: Boolean,
) = MeasurePolicy { measurables, constraints ->
    val extraLooseConstraints = constraints.asLoose(width = true)

    val userProfilePlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == UserInfoBlockUserProfileLayoutId
    }?.measure(extraLooseConstraints) ?: npe()

    val userNamePlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == UserInfoBlockUserNameLayoutId
    }?.measure(extraLooseConstraints) ?: npe()

    val userDescriptionPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == UserInfoBlockUserDescriptionLayoutId
    }?.measure(extraLooseConstraints) ?: npe()

    val userFollowingButtonPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == UserInfoBlockFollowingButtonLayoutId
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

        if (!isMine) {
            userFollowingButtonPlaceable.place(
                x = constraints.maxWidth - userFollowingButtonPlaceable.width,
                y = constraints.centerVertical(userFollowingButtonPlaceable.height),
            )
        }
    }
}
