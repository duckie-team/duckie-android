/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.recommendation.model

import androidx.compose.runtime.Immutable
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag

enum class JumbotronType(
    val original: String,
) {
    Text("text");

    companion object {
        fun toJumbotronType(type: String): JumbotronType {
            return values().firstOrNull { it.original == type } ?: Text
        }
    }
}

@Immutable
data class RecommendationJumbotronItem(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailUrl: String?,
    val buttonTitle: String,
    val certifyingStatement: String,
    val solvedCount: Int,
    val answerRate: Float,
    val category: Category,
    val mainTag: Tag,
    val subTags: List<Tag>,
    val type: JumbotronType,
)
