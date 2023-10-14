/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.tag.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class Tag(
    val id: Int,
    val name: String,
) : Parcelable {
    companion object {
        private const val ALL = -1
        private const val EMPTY_TAG = 0

        /**
         * [Tag] 의 Empty Model 을 제공합니다.
         * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
         */
        fun empty() = Tag(
            id = EMPTY_TAG,
            name = "",
        )

        /**
         * [Tag] 의 전체를 제공합니다.
         * 일부 UI 에서 사용됩니다. (ex. 출제중 화면)
         */
        fun all() = Tag(
            id = ALL,
            name = "전체",
        )
    }
}
