/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.user.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.follow.model.Follow
import team.duckie.app.android.domain.tag.model.Tag

@Immutable
@Parcelize
data class User(
    val id: Int,
    val nickname: String,
    val profileImageUrl: String?,
    val status: UserStatus?,
    val duckPower: DuckPower?,
    val follow: Follow?,
    val favoriteTags: List<Tag>?,
    val favoriteCategories: List<Category>?,
    val permissions: List<String>?,
) : Parcelable {
    companion object {
        fun empty() = User(
            id = 0,
            nickname = "",
            profileImageUrl = null,
            status = null,
            duckPower = null,
            follow = null,
            favoriteTags = null,
            favoriteCategories = null,
            permissions = null,
        )
    }
}
