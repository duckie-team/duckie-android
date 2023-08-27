/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.todo.animation.QuackAnimationSpec
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.animation.animateQuackColorAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackClickable

/**
 * 모든 Quack 컴포넌트에서 최하위로 사용되는 컴포넌트입니다.
 * 컴포넌트의 기본 모양을 정의합니다.
 *
 * **애니메이션 가능한 모든 요소들에는 자동으로 애니메이션이 적용됩니다.**
 * animationSpec 으로는 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param modifier 컴포저블에 적용할 [Modifier].
 * 기본값은 수정 없는 본질의 Modifier 입니다.
 * @param shape 컴포저블의 [Shape]. 기본값은 [RectangleShape] 입니다.
 * @param backgroundColor 컴포저블의 배경 색상.
 * 기본값은 정의되지 않은 색상인 [QuackColor.Unspecified] 입니다.
 * @param border 컴포저블의 테두리.
 * null 이 입력된다면 테두리를 설정하지 않습니다. 기본값은 null 입니다.
 * @param elevation 컴포저블의 그림자 고도.
 * 기본값은 0 입니다. 즉, 그림자를 사용하지 않습니다.
 * @param rippleEnabled 컴포저블이 클릭됐을 때 리플 효과를 적용할지 여부.
 * 기본값은 true 입니다.
 * @param rippleColor 컴포저블이 클릭됐을 때 리플 효과의 색상.
 * 기본값은 정해지지 않은 색상인 [QuackColor.Unspecified] 입니다.
 * [rippleEnabled] 이 켜져 있을 때만 사용됩니다.
 * @param onClick 컴포저블이 클릭됐을 때 실행할 람다식.
 * null 이 입력된다면 클릭 이벤트를 추가하지 않습니다.
 * 기본값은 null 입니다. 즉, 클릭 이벤트를 추가하지 않습니다.
 * @param contentAlignment 컴포저블의 정렬 상태. 기본값은 Center 입니다.
 * @param propagateMinConstraints 최소 제약 조건을 전파할지 여부. 기본값은 false 입니다.
 * @param content 표시할 컴포저블. [BoxScope] 를 receive 로 받습니다.
 */
@Composable
// TODO: Modifier.quackSurface 로 변경
// @NonRestartableComposable; 여기서 사용하는 Box 는 inline 됨
fun QuackSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: QuackColor = QuackColor.Unspecified,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: (() -> Unit)? = null,
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    val backgroundColorAnimation by animateQuackColorAsState(
        targetValue = backgroundColor,
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                clip = false,
            )
            .clip(
                shape = shape,
            )
            .background(
                color = backgroundColorAnimation.value,
                shape = shape,
            )
            .quackClickable(
                onClick = onClick,
                rippleEnabled = rippleEnabled,
                rippleColor = rippleColor,
            )
            .runIf(border != null) {
                border(
                    border = border!!,
                    shape = shape,
                )
            }
            .animateContentSize(
                animationSpec = QuackAnimationSpec(),
            ),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        content = content,
    )
}
