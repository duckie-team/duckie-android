/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import team.duckie.app.android.domain.exam.model.Question.Text.text
import team.duckie.app.android.domain.exam.model.Question.Text.type

data class ProblemItem(
    val questionObject: Question,
    val answerObject: Answer,
    val correctAnswer: String,
    val hint: String?,
    val memo: String?,
)

sealed class Question(
    val type: String,
    val text: String,
) {
    object Text : Question(type, text)
    data class Image(
        val imageUrl: String,
    ) : Question(type, text)

    data class Audio(
        val audioUrl: String,
    ) : Question(type, text)

    data class Video(
        val videoUrl: String,
    ) : Question(type, text)
}

sealed class Answer(
    val type: String,
) {
    data class ShortAnswer(
        val shortAnswer: String,
    ) : Answer(type)

    data class Choice(
        val choices: List<ChoiceModel>,
    ) : Answer(type)

    data class ImageChoice(
        val imageChoice: List<ImageChoiceModel>,
    ) : Answer(type)
}

@JvmInline
value class ChoiceModel(
    val text: String,
)

data class ImageChoiceModel(
    val text: String,
    val imageUrl: String,
)
