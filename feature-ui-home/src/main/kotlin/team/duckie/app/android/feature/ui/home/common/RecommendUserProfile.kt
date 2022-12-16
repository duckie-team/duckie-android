/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.home.R
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.util.DpSize

private val HomeProfileSize: DpSize = DpSize(
    all = 32.dp,
)

// TODO(limsaehyun): 추후에 QuackQuack Shape 으로 대체 필요
private val HomeProfileShape: RoundedCornerShape = RoundedCornerShape(
    size = 16.dp
)

private const val UserInfoBlockUserProfileLayoutId = "UserInfoContentUserProfile"
private const val UserInfoBlockUserNameLayoutId = "UserInfoBlockUserName"
private const val UserInfoBlockUserDescriptionLayoutId = "UserInfoBlockUserDescription"
private const val UserInfoBlockFollowingButtonLayoutId = "UserInfoBlockFollowingButton"

@Composable
internal fun RecommendUserProfile(
    modifier: Modifier = Modifier,
    profile: String,
    name: String,
    takers: Int,
    createAt: String,
    onClickUserProfile: (() -> Unit)? = null,
    isFollowing: Boolean,
    onClickFollowing: (Boolean) -> Unit,
) {
    Layout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(
                vertical = 12.dp,
            ),
        content = {
            QuackImage(
                modifier = Modifier.layoutId(UserInfoBlockUserProfileLayoutId),
                src = profile,
                size = HomeProfileSize,
                shape = HomeProfileShape,
                onClick = {
                    if (onClickUserProfile != null) {
                        onClickUserProfile()
                    }
                },
            )

            QuackSubtitle2(
                modifier = Modifier
                    .layoutId(UserInfoBlockUserNameLayoutId)
                    .padding(start = 8.dp, top = 2.dp),
                text = name,
            )

            QuackBody3(
                modifier = Modifier
                    .layoutId(UserInfoBlockUserDescriptionLayoutId)
                    .padding(start = 8.dp),
                text = "${stringResource(id = R.string.taker)} $takers  ·  $createAt",
            )

            QuackBody2(
                modifier = Modifier
                    .layoutId(UserInfoBlockFollowingButtonLayoutId),
                text = if (isFollowing) stringResource(id = R.string.follow)
                else stringResource(id = R.string.following),
                color = if (isFollowing) QuackColor.Gray1 else QuackColor.DuckieOrange,
                onClick = { onClickFollowing(!isFollowing) },
                rippleEnabled = false,
            )
        }
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
