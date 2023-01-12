/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.solve.problem.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.duckie.app.android.shared.ui.compose.FadeAnimatedVisibility
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize

@Composable
internal fun TextAnswerBox(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    GraySurface(
        modifier = modifier.fillMaxWidth(),
        selected = selected,
        onClick = onClick,
    ) {
        TextAndCheck(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 16.dp,
                ),
            text = text,
            selected = selected,
        )
    }
}

@Composable
internal fun ImageAnswerBox(
    modifier: Modifier = Modifier,
    imageSrc: Any?,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    GraySurface(
        modifier = modifier.fillMaxWidth(),
        selected = selected,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            QuackImage(
                src = imageSrc,
                size = DpSize(all = 136.dp)
            )
            TextAndCheck(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 16.dp,
                    ),
                text = text,
                selected = selected,
            )
        }
    }
}

@Composable
private fun TextAndCheck(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackText(
            text = text,
            style = QuackTextStyle.Body1.change(
                color = when (selected) {
                    true -> QuackColor.DuckieOrange
                    else -> QuackColor.Black
                },
                textAlign = TextAlign.Center,
            ),
            singleLine = true,
        )
        FadeAnimatedVisibility(visible = selected) {
            QuackImage(
                src = QuackIcon.Check,
                tint = QuackColor.DuckieOrange,
                size = DpSize(all = 18.dp)
            )
        }
    }
}

@Composable
private fun GraySurface(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable (BoxScope.() -> Unit),
) {
    QuackSurface(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = QuackColor.Gray4,
        border = setBoxBorder(selected = selected),
        shape = RoundedCornerShape(size = 4.dp),
        onClick = onClick,
        content = content,
    )
}

@Stable
private fun setBoxBorder(selected: Boolean): QuackBorder? {
    return if (selected) {
        QuackBorder(color = QuackColor.DuckieOrange)
    } else {
        null
    }
}
