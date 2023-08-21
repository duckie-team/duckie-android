/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.common.kotlin.getDiffDayFromToday
import team.duckie.app.android.common.kotlin.isTopRanked
import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.user.model.User
import java.time.LocalDateTime
import java.util.Date

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

        // 오답 댓글 쓰기
        val comments: ImmutableList<ChallengeCommentUiModel> = persistentListOf(),
        val commentsTotal: Int = 0,
        val commentOrderType: CommentOrderType = CommentOrderType.LIKE,
        val isWriteComment: Boolean = false,
        val commentCreateAt: Date = Date(),

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

        ) : ExamResultState() {

        val percent: Double = (ranking.toDouble() / solvedCount.toDouble()) * 100

        private fun Date.difference(other: Date): Long {
            return this.time - other.time
        }

        data class ChallengeCommentUiModel(
            val id: Int,
            val message: String,
            val wrongAnswer: String,
            val heartCount: Int,
            val createdAt: String,
            val user: User,
            val heart: Heart?,
            val isHeart: Boolean = false,
        )
    }

    data class Error(
        val exception: Throwable,
    ) : ExamResultState()
}

internal fun ChallengeComment.toUiModel() = ExamResultState.Success.ChallengeCommentUiModel(
    id = id,
    message = message,
    wrongAnswer = wrongAnswer,
    heartCount = heartCount,
    createdAt = createdAt.getDiffDayFromToday(),
    user = user,
    heart = heart,
    isHeart = heart != null,
)
