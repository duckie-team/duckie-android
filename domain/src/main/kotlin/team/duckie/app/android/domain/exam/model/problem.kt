/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class Problem(
    val question: Question,
    val answer: Answer,
    val correctAnswer: String,
    val hint: String?,
    val memo: String?,
)

@Immutable
sealed class Question(val type: Type, open val text: String) {
    enum class Type(val key: String) {
        Text("text"),
        Image("image"),
        Audio("audio"),
        Video("video"),
    }

    @Immutable
    data class Text(override val text: String) : Question(type = Type.Text, text = text)

    @Immutable
    data class Image(override val text: String, val imageUrl: String) : Question(
        type = Type.Image,
        text = text,
    )

    @Immutable
    data class Audio(override val text: String, val audioUrl: String) : Question(
        type = Type.Audio,
        text = text,
    )

    @Immutable
    data class Video(override val text: String, val videoUrl: String) : Question(
        type = Type.Video,
        text = text,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Question) return false

        if (type != other.type) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}

@Immutable
sealed class Answer(val type: Type) {
    enum class Type(val key: String, val title: String) {
        ShortAnswer("shortAnswer", "주관식"),
        Choice("choice", "객관식/글"),
        ImageChoice("imageChoice", "객관식/사진"),
    }

    @Immutable
    class Short(val answer: String) : Answer(type = Type.ShortAnswer)

    @Immutable
    class Choice(val choices: ImmutableList<ChoiceModel>) : Answer(type = Type.Choice)

    @Immutable
    class ImageChoice(val imageChoice: ImmutableList<ImageChoiceModel>) :
        Answer(type = Type.ImageChoice)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Answer) return false
        if (type != other.type) return false

        if (this is Short && other is Short) {
            return this.answer == other.answer
        }
        if (this is Choice && other is Choice) {
            return this.choices == other.choices
        }
        if (this is ImageChoice && other is ImageChoice) {
            return this.imageChoice == other.imageChoice
        }

        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

@JvmInline
@Immutable
value class ChoiceModel(val text: String)

@Immutable
data class ImageChoiceModel(
    val text: String,
    val imageUrl: String,
)

/** [Answer] -> [Answer.Short] */
fun Answer.toShort(newAnswer: String?) = when (this) {
    is Answer.Short -> Answer.Short(newAnswer ?: this.answer)
    is Answer.Choice -> Answer.Short(newAnswer ?: this.choices.firstOrNull()?.text ?: "")
    is Answer.ImageChoice -> Answer.Short(
        newAnswer ?: this.imageChoice.firstOrNull()?.text ?: ""
    )
}

/** [Answer] -> [Answer.Choice] */
fun Answer.toChoice(answerIndex: Int, newAnswer: String?) = when (this) {
    is Answer.Short -> Answer.Choice(persistentListOf())
    is Answer.Choice -> Answer.Choice(
        choices.toMutableList().apply {
            this[answerIndex] = ChoiceModel(newAnswer ?: this[answerIndex].text)
        }.toPersistentList()
    )

    is Answer.ImageChoice -> Answer.Choice(
        imageChoice.mapIndexed { index, imageChoiceModel ->
            if (index == answerIndex)
                ChoiceModel(newAnswer ?: imageChoiceModel.text)
            else
                ChoiceModel(imageChoiceModel.text)
        }.toPersistentList()
    )
}

/** [Answer] -> [Answer.ImageChoice] */
fun Answer.toImageChoice(
    answerIndex: Int,
    newAnswer: String?,
    newUrlSource: String?,
) = when (this) {
    is Answer.Short -> Answer.ImageChoice(persistentListOf())
    is Answer.Choice -> Answer.ImageChoice(
        choices.mapIndexed { index, choiceModel ->
            if (index == answerIndex)
                ImageChoiceModel(
                    newAnswer ?: choiceModel.text,
                    newUrlSource ?: ""
                )
            else
                ImageChoiceModel(choiceModel.text, "")
        }.toPersistentList()
    )

    is Answer.ImageChoice -> Answer.ImageChoice(
        imageChoice.toMutableList().apply {
            this[answerIndex] = ImageChoiceModel(
                newAnswer ?: this[answerIndex].text,
                newUrlSource ?: this[answerIndex].imageUrl
            )
        }.toPersistentList()
    )
}
