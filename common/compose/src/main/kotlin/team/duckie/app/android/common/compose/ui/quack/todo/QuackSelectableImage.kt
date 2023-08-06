/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import team.duckie.app.android.common.compose.ui.icon.v1.CheckId
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSelectableImageType.CheckOverlay
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSelectableImageType.TopEndCheckBox
import team.duckie.app.android.common.compose.ui.quack.todo.animation.QuackRoundCheckBox
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.ui.QuackImage

/**
 * 오른쪽 상단에 체크박스와 함께 이미지 혹은 [QuackIcon] 을 표시합니다.
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param isSelected 현재 이미지가 선택됐는지 여부
 * @param src 표시할 리소스. 만약 null 이 들어온다면 리소스를 그리지 않습니다.
 * @param size 리소스의 크기를 지정합니다. null 이 들어오면 기본 크기로 표시합니다.
 * @param tint 적용할 틴트 값
 * @param shape 컴포넌트의 모양
 * @param selectableType selection 이 표시될 방식
 * @param rippleEnabled 클릭됐을 때 ripple 발생 여부
 * @param onClick 클릭됐을 때 실행할 람다식
 * @param contentScale 적용할 content scale 정책
 * @param contentDescription 이미지의 설명
 */
@Composable
fun QuackSelectableImage(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    src: Any?,
    size: DpSize? = null,
    tint: QuackColor = QuackColor.Unspecified,
    shape: Shape = RectangleShape,
    selectableType: QuackSelectableImageType = TopEndCheckBox,
    rippleEnabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScale: ContentScale = ContentScale.FillBounds,
    contentDescription: String? = null,
) {
    QuackSurface(
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, QuackColor.Gray3.value)
            .takeIf { isSelected }
            .takeIf { selectableType == TopEndCheckBox },
        rippleEnabled = rippleEnabled,
        onClick = onClick,
        contentAlignment = Alignment.TopEnd,
    ) {
        QuackImage(
            src = src,
            modifier = Modifier
                .zIndex(1f)
                .runIf(size != null) { size(size!!) },
            tint = tint,
            contentScale = contentScale,
            contentDescription = contentDescription,
        )

        when (selectableType) {
            TopEndCheckBox -> {
                QuackRoundCheckBox(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(all = 7.dp))
                        .zIndex(2f),
                    checked = isSelected,
                )
            }

            CheckOverlay -> {
                AnimatedVisibility(
                    modifier = Modifier
                        .matchParentSize()
                        .zIndex(2f),
                    visible = isSelected,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = QuackColor.Dimmed.value),
                        contentAlignment = Alignment.Center,
                    ) {
                        QuackImage(
                            src = QuackIcon.CheckId,
                            modifier = Modifier.size(selectableType.size!!),
                            tint = selectableType.tint!!,
                        )
                    }
                }
            }
        }
    }
}


/**
 * [QuackSelectableImage] 에서 selection 이 표시될 방식을 나타냅니다.
 *
 * @property TopEndCheckBox 오른쪽 상단에 [QuackRoundCheckBox] 로 표시
 * @property CheckOverlay 이미지 전체에 [QuackIcon.Check] 로 오버레이 표시 및
 * [QuackColor.Dimmed] 로 dimmed 처리
 *
 * @param size 만약 [CheckOverlay] 방식일 때 [QuackIcon.Check] 의 사이즈
 * @param tint 만약 [CheckOverlay] 방식일 때 [QuackIcon.Check] 의 틴트
 */
sealed class QuackSelectableImageType(
    internal val size: DpSize? = null,
    internal val tint: QuackColor? = null,
) {
    object TopEndCheckBox : QuackSelectableImageType()
    object CheckOverlay : QuackSelectableImageType(
        size = DpSize(width = 28.dp, height = 28.dp),
        tint = QuackColor.White,
    )
}
