/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.applyAnimatedQuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

internal fun getCreateProblemMeasurePolicy(
    topLayoutId: String,
    contentLayoutId: String,
    bottomLayoutId: String,
) = MeasurePolicy { measurableItems, constraints ->
    val looseConstraints = constraints.asLoose()

    // 1. topAppBar 높이값 측정
    val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
        measureItem.layoutId == topLayoutId
    }?.measure(looseConstraints) ?: npe()
    val topAppBarHeight = topAppBarMeasurable.height

    // 2. bottomLayout, 높이값 측정
    val bottomLayoutMeasurable = measurableItems.fastFirstOrNull { measureItem ->
        measureItem.layoutId == bottomLayoutId
    }?.measure(looseConstraints) ?: npe()
    // TODO(riflockle7): 왜 이걸 더해야하는지 모르겠음.. padding 을 더하면 이렇게 됨...
    val bottomLayoutHeight = topAppBarMeasurable.height + 72.toDp().toPx().toInt()

    // 3. createProblemButton 높이값 측정
    val contentThresholdHeight = constraints.maxHeight - topAppBarHeight - bottomLayoutHeight
    val contentConstraints = constraints.copy(
        minHeight = contentThresholdHeight,
        maxHeight = contentThresholdHeight,
    )
    val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
        measurable.layoutId == contentLayoutId
    }?.measure(contentConstraints) ?: npe()

    // 3. 위에서 추출한 값들을 활용해 레이아웃 위치 처리
    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarMeasurable.place(
            x = 0,
            y = 0,
        )
        contentMeasurable.place(
            x = 0,
            y = topAppBarHeight,
        )
        bottomLayoutMeasurable.place(
            x = 0,
            y = topAppBarHeight + contentThresholdHeight,
        )
    }
}

@Composable
internal fun CreateProblemBottomLayout(
    modifier: Modifier,
    leftButtonLeadingIcon: QuackIcon? = null,
    leftButtonText: String? = null,
    leftButtonClick: (() -> Unit)? = null,
    tempSaveButtonClick: () -> Unit,
    nextButtonClick: () -> Unit,
    isMaximumProblemCount: Boolean? = null,
    isValidateCheck: () -> Boolean,
) {
    val isValidate = isValidateCheck()
    Column(modifier = modifier.background(QuackColor.White.composeColor)) {
        QuackDivider()
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            leftButtonClick?.let { createButtonClick ->
                requireNotNull(isMaximumProblemCount)
                requireNotNull(leftButtonText)
                Row(
                    modifier = Modifier
                        .quackClickable {
                            if (!isMaximumProblemCount) {
                                createButtonClick()
                            }
                        }
                        .padding(4.dp),
                ) {
                    // TODO(riflockle7): 추후 비활성화 될 때의 resouce 이미지 필요
                    leftButtonLeadingIcon?.let { QuackImage(src = it, size = DpSize(16.dp, 16.dp)) }
                    QuackSubtitle2(
                        text = leftButtonText,
                        color = if (isMaximumProblemCount) QuackColor.Gray2 else QuackColor.Black,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 임시저장 버튼
            QuackSubtitle(
                modifier = Modifier
                    .clip(RoundedCornerShape(size = 8.dp))
                    .background(QuackColor.White.composeColor)
                    .quackClickable { tempSaveButtonClick() }
                    .applyAnimatedQuackBorder(
                        QuackBorder(1.dp, QuackColor.Gray3),
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    .padding(vertical = 12.dp, horizontal = 19.dp),
                color = QuackColor.Black,
                text = stringResource(id = R.string.create_problem_temp_save_button),
            )

            // 다음 버튼
            QuackSubtitle(
                modifier = Modifier
                    .clip(RoundedCornerShape(size = 8.dp))
                    .background(
                        if (isValidate) {
                            QuackColor.DuckieOrange.composeColor
                        } else {
                            QuackColor.Gray2.composeColor
                        }
                    )
                    .quackClickable {
                        if (isValidate) {
                            nextButtonClick()
                        }
                    }
                    .applyAnimatedQuackBorder(
                        QuackBorder(
                            1.dp,
                            if (isValidate) {
                                QuackColor.DuckieOrange
                            } else {
                                QuackColor.Gray2
                            }
                        ),
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    .padding(
                        vertical = 12.dp,
                        horizontal = 19.dp,
                    ),
                color = QuackColor.White,
                text = stringResource(id = R.string.next),
            )
        }

        QuackDivider()
    }
}
