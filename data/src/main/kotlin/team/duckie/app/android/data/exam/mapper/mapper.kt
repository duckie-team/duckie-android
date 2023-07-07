/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.common.kotlin.AllowCyclomaticComplexMethod
import team.duckie.app.android.common.kotlin.exception.duckieResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.randomString
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamBodyData
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ExamInfoEntity
import team.duckie.app.android.data.exam.model.ExamInstanceBodyData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitBodyData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitData
import team.duckie.app.android.data.exam.model.ExamMeFollowingResponseData
import team.duckie.app.android.data.exam.model.ExamThumbnailBodyData
import team.duckie.app.android.data.exam.model.ExamsData
import team.duckie.app.android.data.exam.model.ImageChoiceData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.exam.model.ProfileExamData
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.data.exam.model.QuizInfoResponse
import team.duckie.app.android.data.exam.model.SolutionData
import team.duckie.app.android.data.heart.mapper.toDomain
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamInfo
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.exam.model.ExamMeFollowingResponse
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.ProfileExam
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.Solution
import team.duckie.app.android.domain.quiz.model.QuizInfo

@AllowCyclomaticComplexMethod
internal fun ExamData.toDomain() = Exam(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    description = description,
    thumbnailUrl = thumbnailUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.thumbnailUrl"),
    thumbnailType = thumbnailType,
    type = type,
    buttonTitle = buttonTitle,
    certifyingStatement = certifyingStatement,
    solvedCount = solvedCount,
    answerRate = answerRate,
    user = user?.toDomain(),
    category = category?.toDomain(),
    mainTag = mainTag?.toDomain(),
    subTags = subTags?.fastMap(TagData::toDomain)?.toImmutableList(),
    status = status,
    heart = heart?.toDomain(),
    heartCount = heartCount,
    quizs = quizs?.fastMap(QuizInfoResponse::toDomain)?.toImmutableList(),
    perfectScoreImageUrl = perfectScoreImageUrl,
    problems = problems?.fastMap(ProblemData::toDomain)?.toImmutableList(),
    timer = timer,
    requirementPlaceholder = requirementPlaceholder,
    requirementQuestion = requirementQuestion,
    problemCount = problemCount,
    myRecord = QuizInfo.dummy(), // FIXME(limsaehyun) for real!
)

internal fun ExamsData.toDomain() = exams?.fastMap { examData -> examData.toDomain() }
    ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exams")

internal fun ProblemData.toDomain() = Problem(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    question = question?.toDomain()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.question"),
    answer = answer?.toDomain(),
    correctAnswer = correctAnswer,
    hint = hint,
    memo = memo,
    solution = solution?.toDomain(),
)

internal fun QuestionData.toDomain() = when (this) {
    is QuestionData.Text -> Question.Text(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
    )

    is QuestionData.Image -> Question.Image(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
        imageUrl = imageUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.imageUrl"),
    )

    is QuestionData.Audio -> Question.Audio(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
        audioUrl = audioUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.audioUrl"),
    )

    is QuestionData.Video -> Question.Video(
        text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
        videoUrl = videoUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.videoUrl"),
    )
}

internal fun AnswerData.toDomain(): Answer = when (this) {
    is AnswerData.ShortAnswer -> Answer.Short()

    is AnswerData.Choice -> Answer.Choice(
        choices = choices?.fastMap(ChoiceData::toDomain)?.toImmutableList()
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.choices"),
    )

    is AnswerData.ImageChoice -> Answer.ImageChoice(
        imageChoice = choices?.fastMap(ImageChoiceData::toDomain)?.toImmutableList()
            ?: duckieResponseFieldNpe("${this::class.java.simpleName}.imageChoice"),
    )
}

internal fun ChoiceData.toDomain() =
    ChoiceModel(text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"))

internal fun ImageChoiceData.toDomain() = ImageChoiceModel(
    text = text ?: duckieResponseFieldNpe("${this::class.java.simpleName}.text"),
    imageUrl = imageUrl ?: duckieResponseFieldNpe("${this::class.java.simpleName}.imageUrl"),
)

internal fun ExamBody.toData() = ExamBodyData(
    title = title,
    description = description,
    thumbnailUrl = thumbnailUrl,
    thumbnailType = thumbnailType.value,
    mainTagId = mainTagId,
    categoryId = categoryId,
    subTagIds = subTagIds,
    certifyingStatement = certifyingStatement,
    buttonTitle = buttonTitle,
    problems = problems.fastMap(Problem::toData),
    status = status,
)

internal fun ExamThumbnailBody.toData() = ExamThumbnailBodyData(
    title = title,
    mainTag = mainTag,
    category = category,
    nickName = nickName,
    certifyingStatement = certifyingStatement,
    type = type,
)

internal fun Problem.toData() = ProblemData(
    question = question.let { question ->
        when (question) {
            is Question.Text -> QuestionData.Text(
                text = question.text,
            )

            is Question.Video -> QuestionData.Video(
                videoUrl = question.videoUrl,
                text = question.text,
            )

            is Question.Image -> QuestionData.Image(
                imageUrl = question.imageUrl,
                text = question.text,
            )

            is Question.Audio -> QuestionData.Audio(
                audioUrl = question.audioUrl,
                text = question.text,
            )
        }
    },
    answer = answer?.let { answer ->
        when (answer) {
            is Answer.Short -> AnswerData.ShortAnswer()

            is Answer.Choice -> AnswerData.Choice(
                choices = answer.choices.map {
                    it.toData()
                }.toList(),
            )

            is Answer.ImageChoice -> AnswerData.ImageChoice(
                choices = answer.imageChoice.map {
                    it.toData()
                }.toList(),
            )
        }
    },
    correctAnswer = correctAnswer,
    hint = hint,
    memo = memo,
)

internal fun ChoiceModel.toData() = ChoiceData(text = text)

internal fun ImageChoiceModel.toData() = ImageChoiceData(
    text = text,
    imageUrl = imageUrl,
)

internal fun ExamInstanceBody.toData() = ExamInstanceBodyData(
    examId = this.examId,
)

internal fun ExamInstanceSubmitBody.toData() = ExamInstanceSubmitBodyData(
    submitted = this.submitted,
)

internal fun ExamInstanceSubmitData.toDomain() = ExamInstanceSubmit(
    examScoreImageUrl = examScoreImageUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.examScoreImageUrl"),
)

internal fun ExamMeFollowingResponseData.toDomain() = ExamMeFollowingResponse(
    exams = exams?.fastMap { it.toDomain() }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exams"),
    page = page ?: duckieResponseFieldNpe("${this::class.java.simpleName}.page"),
)

internal fun ExamInfoEntity.toDomain() = ExamInfo(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    solvedCount = solvedCount,
    nickname = nickname,
)

internal fun ExamInfo.toData() = ExamInfoEntity(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    solvedCount = solvedCount,
    nickname = nickname,
)

internal fun ProfileExamData.toDomain() = ProfileExam(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    thumbnailUrl = thumbnailUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.thumbnailUrl"),
    solvedCount = solvedCount,
    heartCount = heartCount,
    user = user?.toDomain(),
)

internal fun QuizInfoResponse.toDomain() = QuizInfo(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    correctProblemCount = correctProblemCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.correctProblemCount"),
    score = score ?: duckieResponseFieldNpe("${this::class.java.simpleName}.score"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
    time = time ?: duckieResponseFieldNpe("${this::class.java.simpleName}.time"),
    reaction = randomString(20), // FIXME(limsaehyun) for real!
)

internal fun SolutionData.toDomain() = Solution(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    solutionImageUrl = solutionImageUrl,
    correctAnswer = correctAnswer,
    wrongAnswerMessage = wrongAnswerMessage,
    emptyAnswerMessage = emptyAnswerMessage,
    title = title,
    description = description,
)
