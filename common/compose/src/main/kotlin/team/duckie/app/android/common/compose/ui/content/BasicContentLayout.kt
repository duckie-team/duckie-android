/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.content

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.centerVertical
import team.duckie.app.android.common.compose.ui.skeleton
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.npe
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.sugar.QuackBody3
import team.duckie.quackquack.ui.sugar.QuackSubtitle2

@Stable
private object UserContentWithButtonDefaults {
    const val LeadingImageLayoutId = "LeadingImageLayoutId"
    const val TitleLayoutId = "TitleLayoutId"
    const val DescriptionLayoutId = "DescriptionLayoutId"
    const val TrailingButtonLayoutId = "TrailingButtonLayoutId"
}

/**
 * 해당 버튼을 사용하기 위해선 [trailingButton] 에 modifier 를 넣어야 합니다.
 */
@Composable
internal fun BasicContentWithButtonLayout(
    modifier: Modifier = Modifier,
    contentId: Int,
    nickname: String,
    description: String,
    onClickLayout: ((Int) -> Unit)? = null,
    visibleTrailingButton: Boolean = false,
    isTitleCenter: Boolean = false,
    visibleHorizontalPadding: Boolean = true,
    leadingImageContent: @Composable (modifier: Modifier) -> Unit,
    trailingButton: @Composable (modifier: Modifier) -> Unit,
    isLoading: Boolean = false,
) = with(UserContentWithButtonDefaults) {
    Layout(
        modifier = modifier
            .quackClickable(
                onClick = {
                    if (onClickLayout != null) {
                        onClickLayout(contentId)
                    }
                },
            )
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 12.dp)
            .runIf(visibleHorizontalPadding) {
                padding(horizontal = 16.dp)
            },
        content = {
            leadingImageContent(
                modifier = modifier
                    .layoutId(LeadingImageLayoutId)
                    .skeleton(isLoading),
            )
            QuackSubtitle2(
                modifier = Modifier
                    .layoutId(TitleLayoutId)
                    .padding(start = 8.dp)
                    .skeleton(isLoading),
                text = nickname,
            )
            QuackBody3(
                modifier = Modifier
                    .layoutId(DescriptionLayoutId)
                    .padding(start = 8.dp)
                    .skeleton(isLoading),
                text = description,
            )
            trailingButton.invoke(
                modifier = modifier
                    .layoutId(TrailingButtonLayoutId)
                    .skeleton(isLoading)
            )
        },
        measurePolicy = getUserContentLayoutMeasurePolicy(
            isTitleCenter = isTitleCenter,
            visibleTrailingButton = visibleTrailingButton,
        ),
    )
}

private fun getUserContentLayoutMeasurePolicy(
    isTitleCenter: Boolean,
    visibleTrailingButton: Boolean,
) = MeasurePolicy { measurables, constraints ->
    with(UserContentWithButtonDefaults) {
        val extraLooseConstraints = constraints.asLoose(width = true)

        val leadingImagePlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == LeadingImageLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        val titlePlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == TitleLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        val descriptionPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == DescriptionLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        val trailingButtonPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == TrailingButtonLayoutId
        }?.measure(extraLooseConstraints) ?: npe()

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            leadingImagePlaceable.place(
                x = 0,
                y = 0,
            )

            titlePlaceable.place(
                x = leadingImagePlaceable.width,
                y = if (isTitleCenter) {
                    constraints.centerVertical(titlePlaceable.height)
                } else {
                    0
                },
            )

            descriptionPlaceable.place(
                x = leadingImagePlaceable.width,
                y = titlePlaceable.height,
            )

            if (visibleTrailingButton) {
                trailingButtonPlaceable.place(
                    x = constraints.maxWidth - trailingButtonPlaceable.width,
                    y = constraints.centerVertical(trailingButtonPlaceable.height),
                )
            }
        }
    }
}

