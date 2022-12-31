/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.mapper

import team.duckie.app.android.data.exam.model.AnswerData
import team.duckie.app.android.data.exam.model.ChoiceData
import team.duckie.app.android.data.exam.model.ExamBodyData
import team.duckie.app.android.data.exam.model.ImageChoiceData
import team.duckie.app.android.data.exam.model.ProblemData
import team.duckie.app.android.data.exam.model.QuestionData
import team.duckie.app.android.domain.exam.model.Answer
import team.duckie.app.android.domain.exam.model.ChoiceModel
import team.duckie.app.android.domain.exam.model.ExamBody
import team.duckie.app.android.domain.exam.model.ImageChoiceModel
import team.duckie.app.android.domain.exam.model.Problem
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.util.kotlin.OutOfDateApi
import team.duckie.app.android.util.kotlin.fastMap

@OutOfDateApi
internal fun ExamBody.toData() = ExamBodyData(
    title = title,
    description = description,
    mainTagId = mainTagId,
    subTagIds = subTagIds,
    categoryId = categoryId,
    certifyingStatement = certifyingStatement,
    thumbnailImageUrl = thumbnailImageUrl,
    thumbnailType = thumbnailType?.value,
    problems = problems.fastMap(Problem::toData),
    isPublic = isPublic,
    buttonTitle = buttonTitle,
    userId = userId,
)

internal fun Problem.toData() = ProblemData(
    question = question.let { question ->
        when (question) {
            is Question.Text -> QuestionData.Text(
                type = question.type.key,
                text = question.text,
            )
            is Question.Video -> QuestionData.Video(
                videoUrl = question.videoUrl,
                type = question.type.key,
                text = question.text,
            )
            is Question.Image -> QuestionData.Image(
                imageUrl = question.imageUrl,
                type = question.type.key,
                text = question.text,
            )
            is Question.Audio -> QuestionData.Audio(
                audioUrl = question.audioUrl,
                type = question.type.key,
                text = question.text,
            )
        }
    },
    answer = answer.let { answer ->
        when (answer) {
            is Answer.Short -> AnswerData.ShortAnswer(
                shortAnswer = answer.answer,
                type = answer.type,
            )
            is Answer.Choice -> AnswerData.Choice(
                choices = answer.choices.map {
                    it.toData()
                }.toList(),
                type = answer.type,
            )
            is Answer.ImageChoice -> AnswerData.ImageChoice(
                imageChoice = answer.imageChoice.map {
                    it.toData()
                }.toList(),
                type = answer.type,
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
