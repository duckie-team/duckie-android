/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class Category(
    val id: Int,
    val name: String,
    val popularTags: ImmutableList<CategoryModel> = persistentListOf()
)

@Immutable
data class CategoryModel(
    val id: Int,
    val name: String
)
