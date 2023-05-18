/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.exam.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.squareup.moshi.JsonClass

internal data class ProblemData(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:JsonProperty("id")
    val id: Int? = null,

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

    @field:JsonProperty("solution")
    val solution: SolutionData? = null,
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = QuestionData.Text::class, name = "text"),
    JsonSubTypes.Type(value = QuestionData.Image::class, name = "image"),
    JsonSubTypes.Type(value = QuestionData.Audio::class, name = "audio"),
    JsonSubTypes.Type(value = QuestionData.Video::class, name = "video"),
)
@JsonClass(generateAdapter = true)
internal sealed class QuestionData(
    open val type: String? = null,
    open val text: String? = null,
) {
    @JsonClass(generateAdapter = true)
    internal data class Text(
        @field:JsonProperty("text")
        override val text: String? = null,
    ) : QuestionData("text", text)

    @JsonClass(generateAdapter = true)
    internal data class Image(
        @field:JsonProperty("imageUrl")
        val imageUrl: String? = null,

        @field:JsonProperty("text")
        override val text: String? = null,
    ) : QuestionData("image", text)

    @JsonClass(generateAdapter = true)
    internal data class Audio(
        @field:JsonProperty("audioUrl")
        val audioUrl: String? = null,

        @field:JsonProperty("text")
        override val text: String? = null,
    ) : QuestionData("audio", text)

    @JsonClass(generateAdapter = true)
    internal data class Video(
        @field:JsonProperty("videoUrl")
        val videoUrl: String? = null,

        @field:JsonProperty("text")
        override val text: String? = null,
    ) : QuestionData("video", text)
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = AnswerData.ShortAnswer::class, name = "shortAnswer"),
    JsonSubTypes.Type(value = AnswerData.Choice::class, name = "choice"),
    JsonSubTypes.Type(value = AnswerData.ImageChoice::class, name = "imageChoice"),
)
@JsonClass(generateAdapter = true)
internal sealed class AnswerData(
    open val type: String? = null,
) {
    @JsonClass(generateAdapter = true)
    internal class ShortAnswer : AnswerData("shortAnswer")

    @JsonClass(generateAdapter = true)
    internal data class Choice(
        @field:JsonProperty("choices")
        val choices: List<ChoiceData>? = null,
    ) : AnswerData("choice")

    @JsonClass(generateAdapter = true)
    internal data class ImageChoice(
        @field:JsonProperty("choices")
        val choices: List<ImageChoiceData>? = null,
    ) : AnswerData("imageChoice")
}

internal data class ChoiceData(
    @field:JsonProperty("text")
    val text: String? = null,
)

internal data class ImageChoiceData(
    @field:JsonProperty("text")
    val text: String? = null,

    @field:JsonProperty("imageUrl")
    val imageUrl: String? = null,
)

internal data class SolutionData(
    @field:JsonProperty("id")
    val id: Int? = null,
    @field:JsonProperty("solutionImageUrl")
    val solutionImageUrl: String? = null,
    @field:JsonProperty("correctAnswer")
    val correctAnswer: String? = null,
    @field:JsonProperty("wrongAnswerMessage")
    val wrongAnswerMessage: String? = null,
    @field:JsonProperty("emptyAnswerMessage")
    val emptyAnswerMessage: String? = null,
    @field:JsonProperty("title")
    val title: String? = null,
    @field:JsonProperty("description")
    val description: String? = null,
)
