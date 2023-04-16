/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.shared.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.util.kotlin.fastForEach
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHighlightTextInfo
import team.duckie.quackquack.ui.component.internal.QuackClickableText
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

// TODO(EvergreenTree97) QuackQuack 에서 AnnotatedText 를 제공해 주기 전 사용할 AnnotatedText
@Composable
fun QuackAnnotatedText(
    modifier: Modifier = Modifier,
    text: String,
    highlightTextPairs: ImmutableList<Pair<String, (() -> Unit)?>>,
    underlineEnabled: Boolean = true,
    style: QuackTextStyle = QuackTextStyle.HeadLine2,
    rippleEnabled: Boolean = false,
    singleLine: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick: (() -> Unit)? = null,
) {
    val highlightTextInfo = remember(
        key1 = highlightTextPairs,
    ) {
        buildList {
            var startIndexOffset = 0
            highlightTextPairs.fastForEach { (targetText, targetTextClickEvent) ->
                val subStringStartIndex = text
                    .substring(
                        startIndex = startIndexOffset,
                    ).indexOf(
                        string = targetText,
                    )

                if (subStringStartIndex != -1) {
                    val realStartIndex = startIndexOffset + subStringStartIndex
                    val realEndIndex = realStartIndex + targetText.length

                    targetTextClickEvent?.let {
                        add(
                            element = QuackHighlightTextInfo(
                                text = targetText,
                                startIndex = realStartIndex,
                                endIndex = realEndIndex,
                                onClick = targetTextClickEvent,
                            ),
                        )
                    }

                    startIndexOffset = realEndIndex
                }
            }
        }.toImmutableList()
    }

    QuackClickableText(
        modifier = modifier.quackClickable(
            rippleEnabled = rippleEnabled,
            onClick = onClick,
        ),
        clickEventTextInfo = highlightTextInfo,
        text = buildAnnotatedString {
            append(
                text = text,
            )

            highlightTextInfo.fastForEach { (text, startIndex, endIndex, onClick) ->
                onClick?.let {
                    addStringAnnotation(
                        tag = text,
                        annotation = "",
                        start = startIndex,
                        end = endIndex,
                    )
                }

                val textDecoration = TextDecoration.Underline.takeIf {
                    underlineEnabled
                }
                addStyle(
                    style = SpanStyle(
                        color = QuackColor.DuckieOrange.composeColor,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp,
                        textDecoration = textDecoration,
                    ),
                    start = startIndex,
                    end = endIndex,
                )
            }
        },
        style = style,
        singleLine = singleLine,
        overflow = overflow,
        defaultOnClick = onClick,
    )
}
