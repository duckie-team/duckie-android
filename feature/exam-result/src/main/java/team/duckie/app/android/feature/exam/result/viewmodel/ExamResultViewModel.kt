/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.exam.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.duckie.app.android.common.android.savedstate.getOrThrow
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.kotlin.exception.isHeartNotFound
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.domain.challengecomment.model.CommentOrderType
import team.duckie.app.android.domain.challengecomment.usecase.DeleteChallengeCommentHeartUseCase
import team.duckie.app.android.domain.challengecomment.usecase.DeleteChallengeCommentUseCase
import team.duckie.app.android.domain.challengecomment.usecase.GetChallengeCommentListUseCase
import team.duckie.app.android.domain.challengecomment.usecase.PostChallengeCommentHeartUseCase
import team.duckie.app.android.domain.challengecomment.usecase.ReportChallengeCommentUseCase
import team.duckie.app.android.domain.challengecomment.usecase.WriteChallengeCommentUseCase
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.examInstance.model.ExamInstance
import team.duckie.app.android.domain.examInstance.usecase.GetExamInstanceUseCase
import team.duckie.app.android.domain.examInstance.usecase.MakeExamInstanceSubmitUseCase
import team.duckie.app.android.domain.heart.model.Heart
import team.duckie.app.android.domain.ignore.usecase.UserIgnoreUseCase
import team.duckie.app.android.domain.quiz.usecase.GetQuizUseCase
import team.duckie.app.android.domain.quiz.usecase.MakeQuizUseCase
import team.duckie.app.android.domain.quiz.usecase.PostQuizReactionUseCase
import team.duckie.app.android.domain.quiz.usecase.SubmitQuizUseCase
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExamResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val makeExamInstanceSubmitUseCase: MakeExamInstanceSubmitUseCase,
    private val getExamInstanceUseCase: GetExamInstanceUseCase,
    private val submitQuizUseCase: SubmitQuizUseCase,
    private val getQuizUseCase: GetQuizUseCase,
    private val makeQuizUseCase: MakeQuizUseCase,
    private val postQuizReactionUseCase: PostQuizReactionUseCase,
    private val ignoreUseCase: UserIgnoreUseCase,
    // for 오답댓글쓰기
    private val getChallengeCommentListUseCase: GetChallengeCommentListUseCase,
    private val deleteChallengeCommentUseCase: DeleteChallengeCommentUseCase,
    private val deleteChallengeCommentHeartUseCase: DeleteChallengeCommentHeartUseCase,
    private val postChallengeCommentHeartUseCase: PostChallengeCommentHeartUseCase,
    private val reportChallengeCommentUseCase: ReportChallengeCommentUseCase,
    private val writeChallengeCommentUseCase: WriteChallengeCommentUseCase,
) : ViewModel(),
    ContainerHost<ExamResultState, ExamResultSideEffect> {

    override val container: Container<ExamResultState, ExamResultSideEffect> = container(
        ExamResultState.Loading,
    )

    // 오답 댓글쓰기 START
    fun ignoreUser(userId: Int, nickname: String) = intent {
        val state = state as ExamResultState.Success
        ignoreUseCase(targetId = userId)
            .onSuccess {
                reduce {
                    val comments = state.comments.filter { it.userId != userId }.toImmutableList()
                    state.copy(
                        comments = comments,
                    )
                }
                postSideEffect(ExamResultSideEffect.SendIgnoreUserToast(nickname))
            }.onFailure { exception ->
                postSideEffect(ExamResultSideEffect.ReportError(exception))
            }
    }

    fun transferCommentOrderType() = intent {
        val state = state as ExamResultState.Success
        val orderType = when (state.commentOrderType) {
            CommentOrderType.DATE -> CommentOrderType.LIKE
            CommentOrderType.LIKE -> CommentOrderType.DATE
        }
        reduce { state.copy(commentOrderType = orderType) }
        getChallengeCommentList()
    }

    fun deleteChallengeComment(commentId: Int) = intent {
        val state = state as ExamResultState.Success
        deleteChallengeCommentUseCase(commentId = commentId)
            .onSuccess { success ->
                if (success) {
                    reduce {
                        val comments =
                            state.comments.filter { it.id != commentId }.toImmutableList()
                        state.copy(
                            comments = comments,
                            commentsTotal = state.commentsTotal.minus(1),
                        )
                    }
                    postSideEffect(ExamResultSideEffect.SendDeleteCommentSuccessToast)
                }
            }.onFailure { exception ->
                postSideEffect(ExamResultSideEffect.ReportError(exception))
            }
    }

    fun reportChallengeComment(commentId: Int) = intent {
        val state = state as ExamResultState.Success
        reportChallengeCommentUseCase(commentId = commentId)
            .onSuccess { success ->
                if (success) {
                    reduce {
                        val comments =
                            state.comments.filter { it.id != commentId }.toImmutableList()
                        state.copy(
                            comments = comments,
                            commentsTotal = state.commentsTotal.minus(1),
                        )
                    }
                    postSideEffect(ExamResultSideEffect.SendReportCommentSuccessToast)
                }
            }.onFailure { exception ->
                postSideEffect(ExamResultSideEffect.ReportError(exception))
            }
    }

    fun getChallengeCommentList() = intent {
        val state = state as ExamResultState.Success
        getChallengeCommentListUseCase(
            problemId = state.wrongProblemId,
            order = state.commentOrderType,
        ).onSuccess { result ->
            val commentsUiModel =
                result.data?.fastMap { it.toUiModel(isMine = it.isMine(state.userId)) }
                    ?.toImmutableList()
                    ?: persistentListOf()
            reduce {
                state.copy(
                    comments = commentsUiModel,
                    commentsTotal = result.total ?: 0,
                )
            }
        }.onFailure { exception ->
            postSideEffect(ExamResultSideEffect.ReportError(exception))
        }
    }

    fun writeChallengeComment() = intent {
        val state = state as ExamResultState.Success
        writeChallengeCommentUseCase(
            challengeId = state.examId,
            message = state.myWrongComment,
        ).onSuccess { challenge ->
            val comments = state.comments.toMutableList().apply {
                add(0, challenge.toUiModel(isMine = true))
            }.toImmutableList()
            reduce {
                state.copy(
                    isWriteComment = true,
                    comments = comments,
                    commentCreateAt = Date(),
                    commentsTotal = state.commentsTotal.plus(1),
                )
            }
        }.onFailure { exception ->
            postSideEffect(ExamResultSideEffect.ReportError(exception))
        }
    }

    fun heartWrongComment(commentId: Int) = intent {
        val state = state as ExamResultState.Success
        state.comments
            .firstOrNull { it.id == commentId }
            ?.let { comment ->
                if (comment.isHeart) {
                    deleteCommentHeart(commentId)
                } else {
                    postCommentHeart(commentId)
                }
            } ?: postSideEffect(ExamResultSideEffect.SendErrorToast(COMMENT_NOT_FOUND_MESSAGE))
    }

    private fun postCommentHeart(commentId: Int) = intent {
        val state = state as ExamResultState.Success
        postChallengeCommentHeartUseCase(commentId = commentId)
            .onSuccess { heartId ->
                reduce {
                    val comments = postHeartId(
                        comments = state.comments,
                        commentId = commentId,
                        heartId = heartId,
                    )
                    state.copy(
                        comments = comments,
                    )
                }
            }.onFailure { exception ->
                postSideEffect(ExamResultSideEffect.ReportError(exception))
            }
    }

    private fun deleteCommentHeart(commentId: Int) = intent {
        val state = state as ExamResultState.Success
        state.comments.firstOrNull { it.id == commentId }?.heart?.id
            ?.let { heartId ->
                deleteChallengeCommentHeartUseCase(heartId = heartId)
                    .onSuccess {
                        reduce {
                            val comments = deleteHeartId(
                                comments = state.comments,
                                commentId = commentId,
                            )
                            state.copy(
                                comments = comments,
                            )
                        }
                    }.onFailure { exception ->
                        when {
                            exception.isHeartNotFound -> postSideEffect(
                                ExamResultSideEffect.SendErrorToast(
                                    HEART_NOT_FOUND_MESSAGE
                                )
                            )

                            else -> postSideEffect(ExamResultSideEffect.ReportError(exception))
                        }
                    }
            } ?: postSideEffect(ExamResultSideEffect.SendErrorToast(HEART_NOT_FOUND_MESSAGE))
    }

    private fun postHeartId(
        comments: ImmutableList<ExamResultState.Success.ChallengeCommentUiModel>,
        commentId: Int,
        heartId: Int,
    ) = comments.fastMap { comment ->
        if (comment.id == commentId) {
            comment.copy(
                heart = Heart(id = heartId),
                isHeart = true,
                heartCount = comment.heartCount.plus(1),
            )
        } else {
            comment
        }
    }.toImmutableList()

    private fun deleteHeartId(
        comments: ImmutableList<ExamResultState.Success.ChallengeCommentUiModel>,
        commentId: Int,
    ) = comments.fastMap { comment ->
        if (comment.id == commentId) {
            comment.copy(
                heart = null,
                isHeart = false,
                heartCount = comment.heartCount.minus(1)
            )
        } else {
            comment
        }
    }.toImmutableList()
    // 오답 댓글쓰기 END

    private fun postQuizReaction() = intent {
        val state = state as ExamResultState.Success
        postQuizReactionUseCase(
            examId = state.examId,
            reaction = state.reaction,
        ).onSuccess { success ->
            if (success) {
                postSideEffect(ExamResultSideEffect.SendReactionSuccessToast)
            }
        }.onFailure { exception ->
            postSideEffect(ExamResultSideEffect.ReportError(exception))
        }
    }

    fun initState() {
        val examId = savedStateHandle.getOrThrow<Int>(Extras.ExamId)
        val isQuiz = savedStateHandle.getOrThrow<Boolean>(Extras.IsQuiz)
        if (isQuiz) {
            val updateQuizParam =
                savedStateHandle.getOrThrow<SubmitQuizUseCase.Param>(Extras.UpdateQuizParam)
            updateQuiz(
                examId = examId,
                updateQuizParam = updateQuizParam,
            )
        } else {
            val submitted =
                savedStateHandle.getStateFlow<Array<String>>(Extras.Submitted, emptyArray()).value
            val isPassed = savedStateHandle.getOrThrow<Boolean>(Extras.IsPassed)
            if (isPassed) {
                getReportWhenAlreadySolved(examId = examId)
            } else {
                getReport(
                    examId = examId,
                    submitted = ExamInstanceSubmitBody(
                        submitted = submitted.toList().toImmutableList(),
                    ),
                )
            }
        }
    }

    fun updateWrongComment(comment: String) = intent {
        val state = state as ExamResultState.Success
        reduce {
            state.copy(
                myWrongComment = comment,
            )
        }
    }

    private fun getReport(
        examId: Int,
        submitted: ExamInstanceSubmitBody,
    ) = intent {
        reduce {
            ExamResultState.Loading
        }
        makeExamInstanceSubmitUseCase(
            id = examId,
            body = submitted,
        ).onSuccess { submit: ExamInstanceSubmit ->
            reduce {
                ExamResultState.Success(
                    reportUrl = submit.examScoreImageUrl,
                    isQuiz = false,
                )
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                ExamResultState.Error(exception = it)
            }
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    private fun getReportWhenAlreadySolved(examId: Int) = intent {
        reduce { ExamResultState.Loading }
        getExamInstanceUseCase(examId).onSuccess { result: ExamInstance ->
            reduce {
                ExamResultState.Success(
                    reportUrl = result.scoreImageUrl ?: "",
                    isQuiz = false,
                )
            }
        }.onFailure {
            it.printStackTrace()
            reduce {
                ExamResultState.Error(exception = it)
            }
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    private fun updateQuiz(
        examId: Int,
        updateQuizParam: SubmitQuizUseCase.Param,
    ) = intent {
        reduce { ExamResultState.Loading }
        viewModelScope.launch {
            submitQuizUseCase(examId, updateQuizParam).onFailure {
                it.printStackTrace()
                reduce {
                    ExamResultState.Error(exception = it)
                }
                postSideEffect(ExamResultSideEffect.ReportError(it))
            }
        }.join()
        viewModelScope.launch {
            getQuizUseCase(examId).onSuccess { quizResult ->
                val isPerfectScore = quizResult.wrongProblem == null
                reduce {
                    with(quizResult) {
                        ExamResultState.Success(
                            examId = id,
                            wrongProblemId = wrongProblem?.id ?: 0,
                            reportUrl = if (isPerfectScore) {
                                exam.perfectScoreImageUrl ?: ""
                            } else {
                                wrongProblem?.solution?.solutionImageUrl ?: ""
                            },
                            isQuiz = true,
                            correctProblemCount = correctProblemCount,
                            time = time,
                            mainTag = exam.mainTag?.name ?: "",
                            ranking = ranking ?: 0,
                            wrongAnswerMessage = wrongProblem?.solution?.wrongAnswerMessage ?: "",
                            requirementPlaceholder = exam.requirementPlaceholder ?: "",
                            requirementQuestion = exam.requirementQuestion ?: "",
                            timer = exam.timer ?: 0,
                            originalExamId = exam.id,
                            isPerfectScore = isPerfectScore,
                            thumbnailUrl = exam.thumbnailUrl,
                            solvedCount = exam.solvedCount ?: 0,
                            isBestRecord = isBestRecord,
                            myAnswer = updateQuizParam.wrongAnswer ?: "",
                            me = user,
                            mostWrongAnswerData = wrongAnswer?.mostData ?: "",
                            mostWrongAnswerTotal = wrongAnswer?.mostTotal ?: 0,
                        )
                    }
                }
            }.onFailure {
                it.printStackTrace()
                reduce {
                    ExamResultState.Error(exception = it)
                }
                postSideEffect(ExamResultSideEffect.ReportError(it))
            }
        }
    }

    fun updateReaction(reaction: String) = intent {
        val state = state as ExamResultState.Success

        reduce {
            state.copy(
                reaction = reaction,
            )
        }
    }

    fun updateReactionDialogVisible(visible: Boolean) = intent {
        val state = state as ExamResultState.Success

        reduce {
            state.copy(
                isReactionValid = visible,
            )
        }
    }

    fun clickRetry() = intent {
        val state = state as ExamResultState.Success
        makeQuizUseCase(examId = state.originalExamId).onSuccess { result ->
            postSideEffect(
                ExamResultSideEffect.NavigateToStartExam(
                    examId = result.id,
                    requirementQuestion = state.requirementQuestion,
                    requirementPlaceholder = state.requirementPlaceholder,
                    timer = state.timer,
                ),
            )
        }.onFailure {
            postSideEffect(ExamResultSideEffect.ReportError(it))
        }
    }

    fun updateExamResultScreen(screen: ExamResultScreen) = intent {
        require(state is ExamResultState.Success)
        reduce {
            (state as ExamResultState.Success).copy(
                currentScreen = screen,
            )
        }
    }

    fun postReaction() = intent {
        val state = state as ExamResultState.Success
        if (state.isReactionValid) {
            postQuizReaction()
        }
    }

    fun exitExam() = intent {
        postSideEffect(ExamResultSideEffect.FinishExamResult)
    }

    private companion object {
        val COMMENT_NOT_FOUND_MESSAGE = "댓글을 찾을 수 없습니다."
        val HEART_NOT_FOUND_MESSAGE = "좋아요를 찾을 수 없습니다."
    }
}
