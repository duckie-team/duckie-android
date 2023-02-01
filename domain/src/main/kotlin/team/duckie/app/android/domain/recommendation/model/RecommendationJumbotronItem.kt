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

/**
 * 덕질고사 타입
 *
 * [Text] 텍스트로 이루어진 타입
 *
 * TODO(limsaehyun): 백엔드 문서가 나오면 업데이트 필요
 */
enum class ExamType(
    val original: String,
) {
    Text(
        original = "text",
    ),
    ;

    companion object {
        fun toExamType(type: String): ExamType {
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
    val certifyingStatement: String,
    val solvedCount: Int,
    val buttonTitle: String,
    val type: ExamType,
    val answerRate: Float,
    val category: Category,
    val mainTag: Tag,
    val subTags: List<Tag>,
)
