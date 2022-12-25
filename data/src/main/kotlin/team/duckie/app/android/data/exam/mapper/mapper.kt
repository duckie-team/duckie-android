/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.mapper

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.CategoryData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamRequest
import team.duckie.app.android.data.exam.model.ImageChoiceData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.data.exam.model.TagData
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.Category
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.ExamParam
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.domain.exam.model.Tag
import team.duckie.app.android.util.kotlin.DuckieResponseFieldNPE
import team.duckie.app.android.util.kotlin.fastMap

internal fun ExamParam.toData() = ExamRequest(
    title = title,
    description = description,
    mainTagId = mainTagId,
    subTagIds = subTagIds.toImmutableList(),
    categoryId = categoryId,
    certifyingStatement = certifyingStatement,
    thumbnailImageUrl = thumbnailImageUrl,
    thumbnailType = thumbnailType,
    problems = problems.map { it.toData() },
    isPublic = isPublic,
    buttonTitle = buttonTitle,
    userId = userId,
)

internal fun Problem.toData() = ProblemData(
    question = when (question) {
        is Question.Text -> QuestionData.Text(
            type = question.type,
            text = question.text,
        )
        is Question.Video -> QuestionData.Video(
            videoUrl = (question as Question.Video).videoUrl,
            type = question.type,
            text = question.text,
        )
        is Question.Image -> QuestionData.Image(
            imageUrl = (question as Question.Image).imageUrl,
            type = question.type,
            text = question.text,
        )
        is Question.Audio -> QuestionData.Audio(
            audioUrl = (question as Question.Audio).audioUrl,
            type = question.type,
            text = question.text,
        )
    },
    answer = when (answer) {
        is Answer.ShortAnswer -> AnswerData.ShortAnswer(
            shortAnswer = (answer as Answer.ShortAnswer).shortAnswer,
            type = answer.type,
        )
        is Answer.Choice -> AnswerData.Choice(
            choices = (answer as Answer.Choice).choices.map {
                it.toData()
            }.toList(),
            type = answer.type,
        )
        is Answer.ImageChoice -> AnswerData.ImageChoice(
            imageChoice = (answer as Answer.ImageChoice).imageChoice.map {
                it.toData()
            }.toList(),
            type = answer.type,
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

internal fun TagData.toDomain() = Tag(
    id = id ?: throw DuckieResponseFieldNPE("id"),
    name = name ?: throw DuckieResponseFieldNPE("name"),
)

internal fun CategoryData.toDomain() = Category(
    id = id ?: throw DuckieResponseFieldNPE("id"),
    name = name ?: throw DuckieResponseFieldNPE("name"),
    popularTags = popularTags?.fastMap { it.toDomain() }
        ?.toImmutableList(),
)
