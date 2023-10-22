
import team.duckie.app.android.data.exam.mapper.toData
import team.duckie.app.android.data.problem.model.ProblemBodyData
import team.duckie.app.android.domain.problem.model.ProblemBody

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */
internal fun ProblemBody.toData() = ProblemBodyData(
    answer = this.answer.toData(),
    correctAnswer = this.correctAnswer,
    examId = this.examId,
    hint = this.hint,
    memo = this.memo,
    status = this.status,
    question = this.question.toData(),
    solutionImageUrl = this.solutionImageUrl,
    wrongAnswerMessage = this.wrongAnswerMessage,
)
