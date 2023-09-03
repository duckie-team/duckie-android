/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui.quack.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.icon.quackicon.OutlinedGroup
import team.duckie.quackquack.material.icon.quackicon.outlined.ArrowDown
import team.duckie.quackquack.material.quackBorder
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText

/**
 * 덕키에서 Drop Down 을 표시하는 컴포넌트를 구현합니다.
 * [QuackDropDownCard] 는 다음과 같은 특징을 갖습니다.
 *
 * - 항상 trailing content 로 [QuackIcon.ArrowDown] 을 갖습니다.
 * - 단순 rounding 형태의 Card 를 갖기 때문에 이름에 Card 가 추가됐습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param showBorder 테두리를 표시할지 여부
 * @param onClick 클릭했을 때 호출될 람다
 */
@Composable
fun QuackDropDownCard(
    modifier: Modifier = Modifier,
    text: String,
    showBorder: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = Row(
    modifier = modifier
        .clip(shape = RoundedCornerShape(size = 8.dp))
        .quackClickable(
            onClick = onClick,
        )
        .background(
            color = QuackColor.White.value,
            shape = RoundedCornerShape(size = 8.dp),
        )
        .runIf(showBorder) {
            quackBorder(
                border = QuackBorder(
                    color = QuackColor.Gray3,
                ),
                shape = RoundedCornerShape(size = 8.dp),
            )
        },
    verticalAlignment = Alignment.CenterVertically,
) {
    QuackText(
        modifier = Modifier.padding(
            PaddingValues(
                top = 8.dp,
                bottom = 8.dp,
                start = 12.dp,
                end = 4.dp,
            ),
        ),
        text = text,
        typography = QuackTypography.Body1,
        singleLine = true,
    )
    QuackIcon(
        modifier = Modifier
            .size(DpSize(16.dp, 16.dp))
            .padding(PaddingValues(end = 8.dp)),
        icon = OutlinedGroup.ArrowDown,
        tint = QuackColor.Gray1,
    )
}
