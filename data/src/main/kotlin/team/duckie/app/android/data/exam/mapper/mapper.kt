/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamRequest
import team.duckie.app.android.data.exam.model.ExamResponse
import team.duckie.app.android.data.exam.model.ImageChoiceData
import team.duckie.app.android.data.exam.model.ProblemItemData
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.data.exam.model.TagData
import team.duckie.app.android.data.user.mapper.toDomain
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.Exam
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.ProblemItem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.Tag

internal fun ExamParam.toData() = ExamRequest(
    title = title,
    description = description,
    mainTag = mainTag.toData(),
    subTag = subTag.map { it.toData() },
    userId = userId,
    certifyingStatement = certifyingStatement,
    thumbnailImageUrl = thumbnailImageUrl,
    thumbnailType = thumbnailType,
    problems = problems.toData(),
    isPublic = isPublic,
    buttonTitle = buttonTitle,
)

internal fun ProblemItem.toData() = ProblemItemData(
    questionObject = when (questionObject) {
        is Question.Text -> QuestionData.Text(
            type = questionObject.type,
            text = questionObject.text,
        )
        is Question.Video -> QuestionData.Video(
            videoUrl = (questionObject as Question.Video).videoUrl,
            type = questionObject.type,
            text = questionObject.text,
        )
        is Question.Image -> QuestionData.Image(
            imageUrl = (questionObject as Question.Image).imageUrl,
            type = questionObject.type,
            text = questionObject.text,
        )
        is Question.Audio -> QuestionData.Audio(
            audioUrl = (questionObject as Question.Audio).audioUrl,
            type = questionObject.type,
            text = questionObject.text,
        )
    },
    answerObject = when (answerObject) {
        is Answer.ShortAnswer -> AnswerData.ShortAnswer(
            shortAnswer = (answerObject as Answer.ShortAnswer).shortAnswer,
            type = answerObject.type,
        )
        is Answer.Choice -> AnswerData.Choice(
            choices = (answerObject as Answer.Choice).choices.map {
                it.toData()
            }.toImmutableList(),
            type = answerObject.type,
        )
        is Answer.ImageChoice -> AnswerData.ImageChoice(
            imageChoice = (answerObject as Answer.ImageChoice).imageChoice.map {
                it.toData()
            }.toImmutableList(),
            type = answerObject.type,
        )
    },
    correctAnswer = correctAnswer,
    hint = hint,
    memo = memo,
)

internal fun ChoiceModel.toData() = ChoiceData(
    text = text,
)

internal fun ImageChoiceModel.toData() = ImageChoiceData(
    text = text,
    imageUrl = imageUrl,
)

internal fun Tag.toData() = TagData(
    id = id,
    name = name,
)

internal fun TagData.toDomain() = Tag(
    id = id,
    name = name,
)

internal fun ExamResponse.toDomain() = Exam(
    title = title,
    description = description,
    thumbnailUrl = thumbnailUrl,
    certifyingStatement = certifyingStatement,
    buttonTitle = buttonTitle,
    isPublic = isPublic,
    tags = tags.map { it.toDomain() }.toImmutableList(),
    user = user.toDomain(),
    deletedAt = deletedAt,
    id = id,
    createdAt = createdAt,
    updatedAt = updatedAt,
    solvedCount = solvedCount,
    answerRate = answerRate,
    canRetry = canRetry,
)
