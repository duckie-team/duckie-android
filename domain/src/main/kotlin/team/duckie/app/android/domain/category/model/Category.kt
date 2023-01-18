/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.category.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import team.duckie.app.android.domain.tag.model.Tag

@Immutable
@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val thumbnailUrl: String,
    val popularTags: List<Tag>?,
) : Parcelable
