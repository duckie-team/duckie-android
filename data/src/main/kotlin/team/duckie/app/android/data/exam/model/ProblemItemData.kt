/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ProblemItemData(
    @field:JsonProperty("question")
    val questionObject: QuestionData,
    @field:JsonProperty("answer")
    val answerObject: AnswerData,
    @field:JsonProperty("correctAnswer")
    val correctAnswer: String,
    @field:JsonProperty("hint")
    val hint: String?,
    @field:JsonProperty("memo")
    val memo: String?
)

sealed class QuestionData(
    @field:JsonProperty("type")
    open val type: String,
    @field:JsonProperty("text")
    open val text: String,
) {
    data class Text(
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)

    data class Image(
        @field:JsonProperty("imageUrl")
        val imageUrl: String,
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)

    data class Audio(
        @field:JsonProperty("audioUrl")
        val audioUrl: String,
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)

    data class Video(
        @field:JsonProperty("videoUrl")
        val videoUrl: String,
        override val type: String,
        override val text: String,
    ) : QuestionData(type, text)
}

data class TagData(
    @field:JsonProperty("id")
    val id: Int,
    @field:JsonProperty("name")
    val name: String
)

sealed class AnswerData(
    @field:JsonProperty("type")
    open val type: String
) {
    data class ShortAnswer(
        @field:JsonProperty("shortAnswer")
        val shortAnswer: String,
        override val type: String,
    ) : AnswerData(type)

    data class Choice(
        @field:JsonProperty("choice")
        val choices: List<ChoiceData>,
        override val type: String,
    ) : AnswerData(type)

    data class ImageChoice(
        @field:JsonProperty("imageChoice")
        val imageChoice: List<ImageChoiceData>,
        override val type: String,
    ) : AnswerData(type)
}

@JvmInline
value class ChoiceData(
    @field:JsonProperty("text")
    val text: String
)

data class ImageChoiceData(
    @field:JsonProperty("text")
    val text: String,
    @field:JsonProperty("imageUrl")
    val imageUrl: String
)
