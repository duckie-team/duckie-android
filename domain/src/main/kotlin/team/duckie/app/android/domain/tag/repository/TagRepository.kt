/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.tag.repository

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.tag.model.Tag

@Immutable
interface TagRepository {
    suspend fun create(name: String): Tag

    suspend fun getPopularTags(): ImmutableList<Tag>
}
