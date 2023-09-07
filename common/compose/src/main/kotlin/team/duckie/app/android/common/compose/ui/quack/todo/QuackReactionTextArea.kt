/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.getPlaceable
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.component.QuackReviewTextArea

private const val RANKER_REACTION_MAX_LENGTH: Int = 40
private const val RANKER_REACTION_PLACE_HOLDER: String = "소감을 적어주세요! 랭킹에 소감을 띄워드려요 :)"

private object QuackReactionTextAreaLayoutId {
    const val QuizReviewPlaceHolder = "QuizReviewPlaceHolder"
    const val QuizReviewTextArea = "QuizReviewTextArea"
    const val ReactionLimitText = "ReactionLimitText"
}

private fun getQuackReactionTextAreaMeasurePolicy(
    placeholderVisible: Boolean = true,
) = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()
    val extraLooseConstraints = constraints.asLoose(width = true)

    with(QuackReactionTextAreaLayoutId) {
        val quizReviewPlaceHolderPlaceable =
            measurables.getPlaceable(QuizReviewPlaceHolder, extraLooseConstraints)
        val quizReviewTextAreaPlaceable =
            measurables.getPlaceable(QuizReviewTextArea, extraLooseConstraints)
        val reactionLimitTextPlaceable =
            measurables.getPlaceable(ReactionLimitText, looseConstraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            quizReviewTextAreaPlaceable.placeRelative(x = 0, y = 0)
            if (placeholderVisible) {
                quizReviewPlaceHolderPlaceable.placeRelative(x = 0, y = 0)
            }
            reactionLimitTextPlaceable?.placeRelative(
                x = quizReviewTextAreaPlaceable.width - reactionLimitTextPlaceable.width,
                y = quizReviewTextAreaPlaceable.height - reactionLimitTextPlaceable.height,
            )
        }
    }
}

@Composable
fun QuackReactionTextArea(
    modifier: Modifier = Modifier,
    reaction: String,
    onReactionChanged: (String) -> Unit,
    maxLength: Int = RANKER_REACTION_MAX_LENGTH,
    placeHolderText: String = RANKER_REACTION_PLACE_HOLDER,
    visibleCurrentLength: Boolean = true,
) = with(QuackReactionTextAreaLayoutId) {
    Layout(
        modifier = modifier.height(100.dp),
        measurePolicy = getQuackReactionTextAreaMeasurePolicy(
            placeholderVisible = reaction.isEmpty(),
        ),
        content = {
            QuackReviewTextArea(
                modifier = Modifier
                    .layoutId(QuizReviewTextArea)
                    .fillMaxHeight(),
                text = reaction,
                onTextChanged = { str ->
                    if (str.length <= maxLength) {
                        onReactionChanged(str)
                    }
                },
                placeholderText = "",
                focused = true,
            )
            QuackText(
                modifier = Modifier
                    .layoutId(QuizReviewPlaceHolder)
                    .padding(all = 16.dp),
                text = placeHolderText,
                typography = QuackTypography.Body1.change(
                    color = QuackColor.Gray2,
                ),
            )
            if (visibleCurrentLength) {
                QuackText(
                    modifier = Modifier
                        .layoutId(ReactionLimitText)
                        .padding(
                            bottom = 12.dp,
                            end = 12.dp,
                        ),
                    text = "${reaction.length} / $RANKER_REACTION_MAX_LENGTH",
                    typography = QuackTypography.Body1.change(
                        color = QuackColor.Gray2,
                    ),
                )
            }
        },
    )
}
