/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
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
 * @param onClickUserProfile 프로필 사진을 클릭 했을 때 실행되는 람다
 * @param initalFollow 팔로우 여부
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
    isFollowing: Boolean,
    onClickUserProfile: ((Int) -> Unit)? = null,
    onClickFollow: (Boolean) -> Unit,
) {
    Layout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(
                vertical = 12.dp,
            ),
        content = {
            if (profileImgUrl.isEmpty()) {
                // TODO(limsaehyun): 추후에 기본 프로필 이미지로 변경해야 함
                Box(
                    modifier = Modifier
                        .layoutId(UserInfoBlockUserProfileLayoutId)
                        .size(HomeProfileSize)
                        .background(
                            color = QuackColor.Gray2.composeColor,
                            shape = SquircleShape,
                        )
                        .quackClickable {
                            if (onClickUserProfile != null) {
                                onClickUserProfile(userId)
                            }
                        },
                )
            } else {
                QuackImage(
                    modifier = Modifier.layoutId(UserInfoBlockUserProfileLayoutId),
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
                    .padding(start = 8.dp, top = 2.dp),
                text = nickname,
            )
            QuackBody3(
                modifier = Modifier
                    .layoutId(UserInfoBlockUserDescriptionLayoutId)
                    .padding(start = 8.dp),
                text = "$tier · $favoriteTag",
            )
            QuackBody2(
                modifier = Modifier.layoutId(UserInfoBlockFollowingButtonLayoutId),
                text = if (isFollowing) stringResource(id = R.string.following) else stringResource(
                    id = R.string.follow
                ),
                color = if (isFollowing) QuackColor.Gray1 else QuackColor.DuckieOrange,
                onClick = {
                    onClickFollow(!isFollowing)
                },
                rippleEnabled = false,
            )
        },
    ) { measurables, constraints ->
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
                y = 0,
            )

            userDescriptionPlaceable.place(
                x = userProfilePlaceable.width,
                y = userNamePlaceable.height,
            )

            userFollowingButtonPlaceable.place(
                x = constraints.maxWidth - userFollowingButtonPlaceable.width,
                y = Alignment.CenterVertically.align(
                    size = userFollowingButtonPlaceable.height,
                    space = constraints.maxHeight,
                ),
            )
        }
    }
}
