/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.tag.mapper

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.tag.model.PopularTagsData
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.util.kotlin.ImmutableList
import team.duckie.app.android.util.kotlin.duckieResponseFieldNpe
import team.duckie.app.android.util.kotlin.fastMap

internal fun TagData.toDomain() = Tag(
    id = id ?: duckieResponseFieldNpe("id"),
    name = name ?: duckieResponseFieldNpe("name"),
)

internal fun PopularTagsData.toDomain(): ImmutableList<Tag> =
    this.popularTags.fastMap(TagData::toDomain).toImmutableList()
