/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.category.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.category.model.CategoryData
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap

internal fun CategoryData.toDomain() = Category(
    id = id ?: duckieResponseFieldNpe("id"),
    name = name ?: duckieResponseFieldNpe("name"),
    thumbnailUrl = thumbnailUrl,
    popularTags = popularTags?.fastMap(TagData::toDomain)?.toImmutableList(),
)
