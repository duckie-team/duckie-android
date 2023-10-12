/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.common.kotlin.getDiffDayFromToday
import team.duckie.app.android.common.kotlin.isTopRanked
import team.duckie.app.android.domain.challengecomment.model.ChallengeComment
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.user.model.User
import java.util.Date

internal const val CHALLENGE_COMMENT_MAX_LENGTH: Int = 200

enum class ExamResultScreen {
    EXAM_RESULT,
    SHARE_EXAM_RESULT,
}

sealed class ExamResultState {
    object Loading : ExamResultState()

    data class Success(
        // rooot
        val me: User? = null,
        val currentScreen: ExamResultScreen = ExamResultScreen.EXAM_RESULT,

        // defaut state
        val reportUrl: String = "",
        val examId: Int = 0,
        val wrongProblemId: Int = 0,
        val isQuiz: Boolean = true,
        val correctProblemCount: Int = 0,
        val time: Double = 0.0,
        val mainTag: String = "",
        val ranking: Int = 0,
        val wrongAnswerMessage: String = "",
        val thumbnailUrl: String = "",
        val solvedCount: Int = 0,

        // onlyQuiz
        val isPerfectChallenge: Boolean = false,
        val isPerfectScore: Boolean = false,
        val requirementQuestion: String = "",
        val requirementPlaceholder: String = "",
        val timer: Int = 0,
        val originalExamId: Int = 0,
        val isRefreshing: Boolean = false,
        val solveRank: Int = 0,
        val percentileRank: String = "",

        // challenge comment
        val popularComments: ImmutableList<ChallengeCommentUiModel> = persistentListOf(),
        val allComments: ImmutableList<ChallengeCommentUiModel> = persistentListOf(),
        val commentsTotal: Int = 0,
        val commentOrderType: CommentOrderType = CommentOrderType.LIKE,
        val isWriteComment: Boolean = false,
        val commentCreateAt: Date = Date(),
        val myAnswer: String = "",
        val equalAnswerCount: Int = 0,
        val myWrongComment: String = "",
        val mostWrongAnswerTotal: Int = 0,
        val mostWrongAnswerData: String = "",

        // user input state
        val reaction: String = "",
        val isBestRecord: Boolean = false,

        // dialog visible
        val isReactionValid: Boolean = ranking.isTopRanked() && isBestRecord,
    ) : ExamResultState() {

        val profileImg = me?.profileImageUrl ?: ""
        val userId = me?.id ?: 0
        val nickname = me?.nickname ?: ""

        val commentCreateAtWithDiff = commentCreateAt.getDiffDayFromToday(false)

        val percent: Double = (ranking.toDouble() / solvedCount.toDouble()) * 100

        fun updateCommentCreateAt() = copy(
            commentCreateAt = Date(),
        )

        data class ChallengeCommentUiModel(
            val id: Int,
            val message: String,
            val wrongAnswer: String,
            val heartCount: Int,
            val createdAt: String,
            val userProfileImg: String = "",
            val userNickname: String = "",
            val userId: Int,
            val heart: Heart?,
            val isHeart: Boolean = false,
            val isMine: Boolean = false,
        )
    }

    data class Error(
        val exception: Throwable,
    ) : ExamResultState()
}

internal fun ChallengeComment.toUiModel(isMine: Boolean = false) =
    ExamResultState.Success.ChallengeCommentUiModel(
        id = id,
        message = message,
        wrongAnswer = wrongAnswer,
        heartCount = heartCount,
        createdAt = createdAt.getDiffDayFromToday(isShowSecond = false),
        userProfileImg = user.profileImageUrl ?: "",
        userNickname = user.nickname,
        heart = heart,
        isHeart = heart != null,
        isMine = isMine,
        userId = user.id,
    )
