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
import team.duckie.app.android.common.kotlin.fastMapIndexed

@Immutable
data class Problem(
    val id: Int,
    val question: Question,
    val answer: Answer?,
    val correctAnswer: String?,
    val hint: String?,
    val memo: String?,
    val solution: Solution?,
) {
    companion object {
        fun Problem.isSubjective(): Boolean {
            return answer?.isShortAnswer() == true
        }
    }
}

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

    val mediaUri: String
        get() = when (this) {
            is Text -> ""
            is Image -> this.imageUrl
            is Audio -> this.audioUrl
            is Video -> this.videoUrl
        }

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

    fun validate(): Boolean = when (this) {
        is Text -> this.text.isNotEmpty()
        is Image -> this.text.isNotEmpty() && this.imageUrl.isNotEmpty()
        is Audio -> this.text.isNotEmpty() && this.audioUrl.isNotEmpty()
        is Video -> this.text.isNotEmpty() && this.videoUrl.isNotEmpty()
    }

    fun isImage(): Boolean = this.type == Type.Image
}

@Immutable
sealed class Answer(val type: Type) {
    enum class Type(val key: String, val title: String) {
        ShortAnswer("shortAnswer", "주관식"),
        Choice("choice", "객관식/글"),
        ImageChoice("imageChoice", "객관식/사진"),
    }

    @Immutable
    class Short(
        /** [correctAnswer] 는 문제 풀기 화면에서만 사용된다. */
        val correctAnswer: String = "",
    ) : Answer(type = Type.ShortAnswer)

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
            return this.correctAnswer == other.correctAnswer
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

    fun isShortAnswer(): Boolean = this.type == Type.ShortAnswer

    fun validate(): Boolean = when (this) {
        is Short -> true

        is Choice ->
            this.choices
                .asSequence()
                .map { it.text.isNotEmpty() }
                .reduce { acc, next -> acc && next }

        is ImageChoice ->
            this.imageChoice
                .asSequence()
                .map { it.text.isNotEmpty() && it.imageUrl.isNotEmpty() }
                .reduce { acc, next -> acc && next }
    }
}

@Immutable
data class Solution(
    val id: Int,
    val solutionImageUrl: String?,
    val correctAnswer: String?,
    val wrongAnswerMessage: String?,
    val emptyAnswerMessage: String?,
    val title: String?,
    val description: String?,
)

/** [문제 타입][Answer.Type]에 맞는 기본 [답안][Answer] 를 가져옵니다. */
fun Answer.Type.getDefaultAnswer(): Answer = when (this) {
    Answer.Type.ShortAnswer -> Answer.Short()
    Answer.Type.Choice -> Answer.Choice(
        persistentListOf(
            getDefaultAnswerModel(),
            getDefaultAnswerModel(),
        ),
    )

    Answer.Type.ImageChoice -> Answer.ImageChoice(
        persistentListOf(
            getDefaultAnswerModel(),
            getDefaultAnswerModel(),
        ),
    )
}

@Immutable
interface AnswerModel

// TODO(riflockle7): value class 적용 검토하기
@Immutable
object ShortModel : AnswerModel

// TODO(riflockle7): value class 적용 검토하기
@Immutable
data class ChoiceModel(val text: String) : AnswerModel

@Immutable
data class ImageChoiceModel(
    val text: String,
    val imageUrl: String,
) : AnswerModel

/** [문제 타입][Answer.Type]에 맞는 기본 [답안 모델][AnswerModel]을 가져옵니다. */
@Suppress("UNCHECKED_CAST")
fun <T : AnswerModel> Answer.Type.getDefaultAnswerModel(): T = when (this) {
    Answer.Type.ShortAnswer -> ShortModel as T
    Answer.Type.Choice -> ChoiceModel("") as T
    Answer.Type.ImageChoice -> ImageChoiceModel("", "") as T
}

/** [Answer] -> [Answer.Short] */
fun Answer.toShort() = Answer.Short()

/** [Answer] -> [Answer.Choice] */
fun Answer.toChoice(answerIndex: Int? = null, newAnswer: String? = null) = when (this) {
    is Answer.Short -> Answer.Type.Choice.getDefaultAnswer()
    is Answer.Choice -> Answer.Choice(
        choices.fastMapIndexed { index, choiceModel ->
            if (index == answerIndex) {
                ChoiceModel(newAnswer ?: choiceModel.text)
            } else {
                choiceModel
            }
        }.toPersistentList(),
    )

    is Answer.ImageChoice -> Answer.Choice(
        imageChoice.fastMapIndexed { index, imageChoiceModel ->
            if (index == answerIndex) {
                ChoiceModel(newAnswer ?: imageChoiceModel.text)
            } else {
                ChoiceModel(imageChoiceModel.text)
            }
        }.toPersistentList(),
    )
}

/** [Answer] -> [Answer.ImageChoice] */
fun Answer.toImageChoice(
    answerIndex: Int? = null,
    newAnswer: String? = null,
    newUrlSource: String? = null,
) = when (this) {
    is Answer.Short -> Answer.Type.Choice.getDefaultAnswer()
    is Answer.Choice -> Answer.ImageChoice(
        choices.fastMapIndexed { index, choiceModel ->
            if (index == answerIndex) {
                ImageChoiceModel(newAnswer ?: choiceModel.text, newUrlSource ?: "")
            } else {
                ImageChoiceModel(choiceModel.text, "")
            }
        }.toPersistentList(),
    )

    is Answer.ImageChoice -> Answer.ImageChoice(
        imageChoice.fastMapIndexed { index, imageChoiceModel ->
            if (index == answerIndex) {
                ImageChoiceModel(
                    newAnswer ?: imageChoiceModel.text,
                    newUrlSource ?: imageChoiceModel.imageUrl,
                )
            } else {
                imageChoiceModel
            }
        }.toPersistentList(),
    )
}

/** client 용 correctAnswer (index) -> 백앤드용 correctAnswer (answerValue) */
fun String.toCorrectAnswerData(answers: Answer) = when (answers) {
    is Answer.ImageChoice -> answers.imageChoice[this.toInt()].text

    is Answer.Choice -> answers.choices[this.toInt()].text

    else -> this
}
