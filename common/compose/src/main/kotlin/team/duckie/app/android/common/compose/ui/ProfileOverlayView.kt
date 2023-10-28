/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.quackquack.material.QuackColor

/** 프로필 중첩 View */
@Composable
fun ProfileOverlayView(profileThumbnailUrls: List<String?>) {
    Row(horizontalArrangement = Arrangement.spacedBy((-5).dp)) {
        profileThumbnailUrls.forEach { url ->
            // 프로필 이미지
            QuackProfileImage(
                modifier = Modifier
                    .size(DpSize(width = 16.dp, height = 16.dp))
                    .border(
                        width = 1.dp,
                        brush = QuackColor.White.toBrush(),
                        shape = RoundedCornerShape(size = 5.dp),
                    ),
                profileUrl = url,
            )
        }
    }
}
