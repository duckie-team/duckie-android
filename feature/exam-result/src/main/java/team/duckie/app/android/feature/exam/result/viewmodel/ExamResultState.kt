/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

import android.annotation.SuppressLint
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.kotlin.isTopRanked
import java.time.LocalDateTime

enum class ExamResultScreen {
    EXAM_RESULT,
    SHARE_EXAM_RESULT,
}

sealed class ExamResultState {
    object Loading : ExamResultState()

    data class Success(
        val currentScreen: ExamResultScreen = ExamResultScreen.EXAM_RESULT,

        val reportUrl: String = "",
        val examId: Int = 0,
        val isQuiz: Boolean = true,
        val correctProblemCount: Int = 0,
        val time: Double = 0.0,
        val mainTag: String = "",
        val ranking: Int = 0,
        val wrongAnswerMessage: String = "",
        val isPerfectScore: Boolean = false,
        val requirementQuestion: String = "",
        val requirementPlaceholder: String = "",
        val timer: Int = 0,
        val originalExamId: Int = 0,
        val nickname: String = "",
        val thumbnailUrl: String = "",
        val solvedCount: Int = 0,

        // user input state
        val reaction: String = "",
        val isBestRecord: Boolean = false,

        // dialog visible
        val isReactionValid: Boolean = ranking.isTopRanked() && isBestRecord,

        // wrong answer
        val myAnswer: String = "",
        val profileImg: String = "",
        val equalAnswerCount: Int = 0,
        val wrongComment: String = "",
        val otherComments: kotlinx.collections.immutable.ImmutableList<WrongAnswerComment> =
            (0..3).map { // TODO(limsaehyun) for using UI test, delete it before deploy
                WrongAnswerComment.dummy()
            }.toImmutableList(),

    ) : ExamResultState() {
        val percent: Double = (ranking.toDouble() / solvedCount.toDouble()) * 100

        data class WrongAnswerComment(
            val id: Int,
            val profileImg: String,
            val nickname: String,
            val createAt: LocalDateTime,
            val answer: String,
            val comment: String,
            val isLike: Boolean = false,
            val likeCnt: Int,
        ) {
            companion object {
                @SuppressLint("NewApi")
                fun dummy() = WrongAnswerComment(
                    id = 0,
                    profileImg = "https://duckie-resource.s3.ap-northeast-2.amazonaws.com/profile/1676192267474",
                    nickname = "세현",
                    createAt = LocalDateTime.now(),
                    answer = "맹구",
                    comment = "아니 이건 아니지 ㅋㅋㅋ",
                    isLike = true,
                    likeCnt = 8,
                )
            }
        }
    }

    data class Error(
        val exception: Throwable,
    ) : ExamResultState()
}
