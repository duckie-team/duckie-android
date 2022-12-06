/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

data class ProblemItem(
    val questionObject: Question,
    val answerObject: Answer,
    val correctAnswer: String,
    val hint: String?,
    val memo: String?,
)

sealed class Question(
    open val type: String,
    open val text: String,
) {
    data class Text(
        override val text: String,
        override val type: String,
    ) : Question(type, text)

    data class Image(
        override val text: String,
        override val type: String,
        val imageUrl: String,
    ) : Question(type, text)

    data class Audio(
        override val text: String,
        override val type: String,
        val audioUrl: String,
    ) : Question(type, text)

    data class Video(
        override val text: String,
        override val type: String,
        val videoUrl: String,
    ) : Question(type, text)
}

sealed class Answer(
    open val type: String,
) {
    data class ShortAnswer(
        override val type: String,
        val shortAnswer: String,
    ) : Answer(type)

    data class Choice(
        override val type: String,
        val choices: List<ChoiceModel>,
    ) : Answer(type)

    data class ImageChoice(
        override val type: String,
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
