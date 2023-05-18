/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.quiz.model.QuizInfo
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.user.model.User

@Immutable
data class Exam(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnailUrl: String,
    val thumbnailType: String?,
    val type: String?,
    val buttonTitle: String?,
    val certifyingStatement: String?,
    val solvedCount: Int?,
    val answerRate: Float?,
    val user: User?,
    val category: Category?,
    val mainTag: Tag?,
    val subTags: ImmutableList<Tag>?,
    val status: String?,
    val heart: Heart?,
    val heartCount: Int?,
    val problems: ImmutableList<Problem>?,
    val quizs: ImmutableList<QuizInfo>?,
    val perfectScoreImageUrl: String?,
) {
    companion object {
        /*
        * User 의 Empty Model 을 제공합니다.
        * 초기화 혹은 Skeleton UI 등에 필요한 Mock Data 로 쓰입니다.
        * */
        fun empty() = Exam(
            id = 0,
            title = "",
            description = null,
            thumbnailUrl = "",
            thumbnailType = null,
            type = null,
            buttonTitle = null,
            certifyingStatement = null,
            solvedCount = null,
            answerRate = null,
            user = null,
            category = null,
            mainTag = null,
            subTags = null,
            status = null,
            heart = null,
            heartCount = null,
            quizs = null,
            perfectScoreImageUrl = null,
            problems = null,
        )
    }
}
