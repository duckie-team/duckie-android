/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.ui.QuackDivider
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.npe
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText

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

    // 3. createExamButton 높이값 측정
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
    leftButtonLeadingIcon: ImageVector? = null,
    leftButtonText: String? = null,
    leftButtonClick: (() -> Unit)? = null,
    tempSaveButtonText: String? = null,
    tempSaveButtonClick: (() -> Unit)? = null,
    nextButtonText: String,
    nextButtonClick: () -> Unit,
    isCreateProblemValidate: Boolean? = null,
    isValidateCheck: () -> Boolean,
) {
    val isValidate = isValidateCheck()
    Column(modifier = modifier.background(QuackColor.White.value)) {
        QuackDivider()
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            leftButtonClick?.let { createButtonClick ->
                requireNotNull(isCreateProblemValidate)
                requireNotNull(leftButtonText)
                Row(
                    modifier = Modifier
                        .quackClickable {
                            if (isCreateProblemValidate) {
                                createButtonClick()
                            }
                        }
                        .padding(4.dp),
                ) {
                    // TODO(riflockle7): 추후 비활성화 될 때의 resouce 이미지 필요
                    leftButtonLeadingIcon?.let {
                        QuackIcon(
                            modifier = modifier.size(DpSize(16.dp, 16.dp)),
                            icon = it,
                        )
                    }
                    QuackText(
                        text = leftButtonText,
                        typography = QuackTypography.Subtitle2.change(
                            if (isCreateProblemValidate) {
                                QuackColor.Black
                            } else {
                                QuackColor.Gray2
                            },
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 임시저장 버튼
            tempSaveButtonClick?.let {
                requireNotNull(tempSaveButtonText)
                QuackText(
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 8.dp))
                        .background(QuackColor.White.value)
                        .quackClickable(onClick = tempSaveButtonClick)
                        .quackBorder(
                            QuackBorder(1.dp, QuackColor.Gray3),
                            shape = RoundedCornerShape(size = 8.dp),
                        )
                        .padding(vertical = 12.dp, horizontal = 19.dp),
                    typography = QuackTypography.Subtitle.change(QuackColor.Black),
                    text = tempSaveButtonText,
                )
            }

            // 다음 버튼
            QuackText(
                modifier = Modifier
                    .clip(RoundedCornerShape(size = 8.dp))
                    .background(
                        if (isValidate) {
                            QuackColor.DuckieOrange.value
                        } else {
                            QuackColor.Gray2.value
                        },
                    )
                    .quackClickable {
                        if (isValidate) {
                            nextButtonClick()
                        }
                    }
                    .quackBorder(
                        QuackBorder(
                            1.dp,
                            if (isValidate) {
                                QuackColor.DuckieOrange
                            } else {
                                QuackColor.Gray2
                            },
                        ),
                        shape = RoundedCornerShape(size = 8.dp),
                    )
                    .padding(
                        vertical = 12.dp,
                        horizontal = 19.dp,
                    ),
                typography = QuackTypography.Subtitle.change(QuackColor.White),
                text = nextButtonText,
            )
        }

        QuackDivider()
    }
}
