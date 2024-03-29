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
import team.duckie.app.android.common.kotlin.exception.duckieSimpleResponseFieldNpe
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.data.category.mapper.toDomain
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamBlockDetailResponse
import team.duckie.app.android.data.exam.model.ExamBlockResponse
import team.duckie.app.android.data.exam.model.ExamBodyData
import team.duckie.app.android.data.exam.model.ExamData
import team.duckie.app.android.data.exam.model.ExamFundingData
import team.duckie.app.android.data.exam.model.ExamFundingsResponseData
import team.duckie.app.android.data.exam.model.ExamInfoEntity
import team.duckie.app.android.data.exam.model.ExamInstanceBodyData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitBodyData
import team.duckie.app.android.data.exam.model.ExamInstanceSubmitData
import team.duckie.app.android.data.exam.model.ExamMeBlocksResponse
import team.duckie.app.android.data.exam.model.ExamMeFollowingResponseData
import team.duckie.app.android.data.exam.model.ExamTagsResponseData
import team.duckie.app.android.data.exam.model.ExamThumbnailBodyData
import team.duckie.app.android.data.exam.model.ExamUncomingsResponseData
import team.duckie.app.android.data.exam.model.ExamsData
import team.duckie.app.android.data.exam.model.ImageChoiceData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.exam.model.ProfileExamData
import team.duckie.app.android.data.exam.model.ProfileExamDatas
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.data.exam.model.QuizInfoResponse
import team.duckie.app.android.data.exam.model.SolutionData
import team.duckie.app.android.data.heart.mapper.toDomain
import team.duckie.app.android.data.home.mapper.toDomain
import team.duckie.app.android.data.tag.mapper.toDomain
import team.duckie.app.android.data.tag.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.data.user.model.UserResponse
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamBlock
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ExamFunding
import team.duckie.app.android.domain.exam.model.ExamFundingsResponse
import team.duckie.app.android.domain.exam.model.ExamInfo
import team.duckie.app.android.domain.exam.model.ExamInstanceBody
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmit
import team.duckie.app.android.domain.exam.model.ExamInstanceSubmitBody
import team.duckie.app.android.domain.exam.model.ExamMeFollowingResponse
import team.duckie.app.android.domain.exam.model.ExamTagsResponse
import team.duckie.app.android.domain.exam.model.ExamThumbnailBody
import team.duckie.app.android.domain.exam.model.ExamUncomingsResponse
import team.duckie.app.android.domain.exam.model.IgnoreExam
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
    myRecord = myRecord?.toDomain(),
    totalProblemCount = totalProblemCount,
    contributors = contributors?.fastMap(UserResponse::toDomain)?.toImmutableList(),
    contributorCount = contributorCount,
    sampleQuestion = sampleQuestion?.toDomain(),
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
    type = type,
    totalProblemCount = totalProblemCount,
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
    question = question.toData(),
    answer = answer.toData(),
    correctAnswer = correctAnswer,
    hint = hint,
    memo = memo,
)

internal fun Question?.toData() = when (this) {
    is Question.Text -> QuestionData.Text(
        text = this.text,
    )

    is Question.Video -> QuestionData.Video(
        videoUrl = this.videoUrl,
        text = this.text,
    )

    is Question.Image -> QuestionData.Image(
        imageUrl = this.imageUrl,
        text = this.text,
    )

    is Question.Audio -> QuestionData.Audio(
        audioUrl = this.audioUrl,
        text = this.text,
    )

    else -> null
}

internal fun Answer?.toData() = when (this) {
    is Answer.Short -> AnswerData.ShortAnswer()

    is Answer.Choice -> AnswerData.Choice(
        choices = choices.map {
            it.toData()
        }.toList(),
    )

    is Answer.ImageChoice -> AnswerData.ImageChoice(
        choices = imageChoice.map {
            it.toData()
        }.toList(),
    )

    else -> null
}

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

internal fun ProfileExamDatas.toDomain() =
    exams?.fastMap(ProfileExamData::toDomain)?.toImmutableList()
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exams")

internal fun QuizInfoResponse.toDomain() = QuizInfo(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    correctProblemCount = correctProblemCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.correctProblemCount"),
    score = score ?: duckieResponseFieldNpe("${this::class.java.simpleName}.score"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
    time = time ?: duckieResponseFieldNpe("${this::class.java.simpleName}.time"),
    reaction = reaction,
    ranking = ranking,
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

internal fun ExamMeBlocksResponse.toDomain(): List<IgnoreExam> =
    exams?.fastMap { it.toDomain() } ?: duckieSimpleResponseFieldNpe<ExamMeBlocksResponse>("exams")

internal fun ExamBlockDetailResponse.toDomain() = IgnoreExam(
    id = id ?: duckieSimpleResponseFieldNpe<ExamBlockDetailResponse>("exams"),
    title = title ?: duckieSimpleResponseFieldNpe<ExamBlockDetailResponse>("title"),
    thumbnailUrl = thumbnailUrl
        ?: duckieSimpleResponseFieldNpe<ExamBlockDetailResponse>("thumbnailUrl"),
    user = user?.toDomain() ?: duckieSimpleResponseFieldNpe<ExamBlockDetailResponse>("user"),
    examBlock = examBlock?.toDomain()
        ?: duckieSimpleResponseFieldNpe<ExamBlockDetailResponse>("examBlock"),
)

internal fun ExamBlockResponse.toDomain() = ExamBlock(
    id = id ?: duckieSimpleResponseFieldNpe<ExamBlockResponse>("id"),
)

internal fun ExamFundingsResponseData.toDomain() = ExamFundingsResponse(
    exams = exams?.map { it.toDomain() }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exams"),
    page = page ?: duckieResponseFieldNpe("${this::class.java.simpleName}.page"),
)

internal fun ExamUncomingsResponseData.toDomain() = ExamUncomingsResponse(
    exams = exams?.map { it.toDomain() }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.exams"),
    page = page ?: duckieResponseFieldNpe("${this::class.java.simpleName}.page"),
)

internal fun ExamFundingData.toDomain() = ExamFunding(
    id = id ?: duckieResponseFieldNpe("${this::class.java.simpleName}.id"),
    title = title ?: duckieResponseFieldNpe("${this::class.java.simpleName}.title"),
    thumbnailUrl = thumbnailUrl
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.thumbnailUrl"),
    contributorCount = contributorCount
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.contributorCount"),
    user = user?.toDomain() ?: duckieResponseFieldNpe("${this::class.java.simpleName}.user"),
)

internal fun ExamTagsResponseData.toDomain() = ExamTagsResponse(
    tags = tags?.map { it.toDomain() }
        ?: duckieResponseFieldNpe("${this::class.java.simpleName}.tags"),
)
