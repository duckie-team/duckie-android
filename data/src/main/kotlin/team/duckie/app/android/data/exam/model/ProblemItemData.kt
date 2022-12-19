/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ProblemData(
    @field:JsonProperty("question")
    val question: QuestionData? = null,
    @field:JsonProperty("answer")
    val answer: AnswerData? = null,
    @field:JsonProperty("correctAnswer")
    val correctAnswer: String? = null,
    @field:JsonProperty("hint")
    val hint: String? = null,
    @field:JsonProperty("memo")
    val memo: String? = null,
)

sealed class QuestionData(
    @field:JsonProperty("type")
    open val type: String? = null,
    @field:JsonProperty("text")
    open val text: String? = null,
) {
    data class Text(
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)

    data class Image(
        @field:JsonProperty("imageUrl")
        val imageUrl: String? = null,
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)

    data class Audio(
        @field:JsonProperty("audioUrl")
        val audioUrl: String? = null,
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)

    data class Video(
        @field:JsonProperty("videoUrl")
        val videoUrl: String? = null,
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)
}

sealed class AnswerData(
    @field:JsonProperty("type")
    open val type: String? = null,
) {
    data class ShortAnswer(
        @field:JsonProperty("shortAnswer")
        val shortAnswer: String? = null,
        override val type: String,
    ) : AnswerData(type)

    data class Choice(
        @field:JsonProperty("choice")
        val choices: List<ChoiceData>? = null,
        override val type: String,
    ) : AnswerData(type)

    data class ImageChoice(
        @field:JsonProperty("imageChoice")
        val imageChoice: List<ImageChoiceData>? = null,
        override val type: String,
    ) : AnswerData(type)
}

@JvmInline
value class ChoiceData(
    @field:JsonProperty("text")
    val text: String? = null,
)

data class ImageChoiceData(
    @field:JsonProperty("text")
    val text: String? = null,
    @field:JsonProperty("imageUrl")
    val imageUrl: String? = null,
)
