/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.user.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.constant.DuckieTier

@Immutable
data class User(
    val id: Int,
    val nickname: String,
    val profileImageUrl: String,
    val tier: DuckieTier,
    val favoriteTags: ImmutableList<Tag>?,
    val favoriteCategories: ImmutableList<Category>?,
)
