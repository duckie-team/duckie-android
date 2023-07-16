/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.profile.screen.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.Divider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v1.DefaultProfile
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.common.kotlin.FriendsType
import team.duckie.app.android.feature.profile.R
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.shape.SquircleShape
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackBody2
import team.duckie.quackquack.ui.sugar.QuackBody3
import team.duckie.quackquack.ui.sugar.QuackSubtitle2
import team.duckie.quackquack.ui.icon.QuackIcon as QuackV1Icon

@Composable
internal fun ProfileSection(
    userId: Int,
    profile: String,
    duckPower: String,
    follower: Int,
    following: Int,
    introduce: String,
    isLoading: Boolean,
    onClickFriend: (FriendsType, Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (profile.isNotEmpty()) {
                QuackImage(
                    modifier = Modifier
                        .clip(SquircleShape)
                        .size(DpSize(44.dp, 44.dp))
                        .skeleton(isLoading),
                    src = profile,
                    contentScale = ContentScale.Crop,
                )
            } else {
                QuackImage(
                    modifier = Modifier
                        .clip(SquircleShape)
                        .size(DpSize(44.dp, 44.dp))
                        .skeleton(isLoading),
                    src = QuackV1Icon.Companion.DefaultProfile,
                    contentScale = ContentScale.Crop,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                EachInformation(
                    value = { duckPower },
                    title = stringResource(id = R.string.duck_power),
                    isLoading = isLoading,
                )
                Divider(height = 12.dp)
                EachInformation(
                    value = { follower.toString() },
                    title = stringResource(id = R.string.follower),
                    isLoading = isLoading,
                    onClick = {
                        onClickFriend(FriendsType.Follower, userId)
                    },
                )
                Divider(height = 12.dp)
                EachInformation(
                    value = { following.toString() },
                    title = stringResource(id = R.string.following),
                    isLoading = isLoading,
                    onClick = {
                        onClickFriend(FriendsType.Following, userId)
                    },
                )
            }
        }
        Spacer(space = 20.dp)
        QuackBody2(
            modifier = Modifier.skeleton(isLoading),
            text = introduce,
        )
    }
}

@Composable
private fun EachInformation(
    value: () -> String,
    title: String,
    isLoading: Boolean,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.quackClickable(
            rippleEnabled = false,
            onClick = onClick,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        QuackSubtitle2(
            modifier = Modifier.skeleton(isLoading),
            text = value(),
        )
        QuackBody3(text = title)
    }
}
