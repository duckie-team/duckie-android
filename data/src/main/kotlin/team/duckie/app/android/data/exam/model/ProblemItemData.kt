/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.exam.model.QuestionData.Text.text
import team.duckie.app.android.data.exam.model.QuestionData.Text.type

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
    val type: String,
    @field:JsonProperty("text")
    val text: String,
) {
    object Text : QuestionData(type, text)

    data class Image(
        @field:JsonProperty("imageUrl")
        val imageUrl: String
    ) : QuestionData(type, text)

    data class Audio(
        @field:JsonProperty("audioUrl")
        val audioUrl: String
    ) : QuestionData(type, text)

    data class Video(
        @field:JsonProperty("videoUrl")
        val videoUrl: String
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
    val type: String
) {
    data class ShortAnswer(
        @field:JsonProperty("shortAnswer")
        val shortAnswer: String
    ) : AnswerData(type)

    data class Choice(
        @field:JsonProperty("choice")
        val choices: List<ChoiceData>
    ) : AnswerData(type)

    data class ImageChoice(
        @field:JsonProperty("imageChoice")
        val imageChoice: List<ImageChoiceData>
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
