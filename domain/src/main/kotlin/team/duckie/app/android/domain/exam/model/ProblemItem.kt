/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.exam.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class Problem(
    val question: Question,
    val answer: Answer,
    val correctAnswer: String,
    val hint: String?,
    val memo: String?,
)

@Immutable
sealed class Question(
    open val type: String,
    open val text: String,
) {
    @Immutable
    data class Text(
        override val text: String,
        override val type: String,
    ) : Question(type, text)

    @Immutable
    data class Image(
        override val text: String,
        override val type: String,
        val imageUrl: String,
    ) : Question(type, text)

    @Immutable
    data class Audio(
        override val text: String,
        override val type: String,
        val audioUrl: String,
    ) : Question(type, text)

    @Immutable
    data class Video(
        override val text: String,
        override val type: String,
        val videoUrl: String,
    ) : Question(type, text)
}

@Immutable
sealed class Answer(
    open val type: String,
) {
    @Immutable
    data class ShortAnswer(
        override val type: String,
        val shortAnswer: String,
    ) : Answer(type)

    @Immutable
    data class Choice(
        override val type: String,
        val choices: ImmutableList<ChoiceModel>,
    ) : Answer(type)

    @Immutable
    data class ImageChoice(
        override val type: String,
        val imageChoice: ImmutableList<ImageChoiceModel>,
    ) : Answer(type)
}

@Immutable
@JvmInline
value class ChoiceModel(
    val text: String,
)

@Immutable
data class ImageChoiceModel(
    val text: String,
    val imageUrl: String,
)
