/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.ui.QuackText

@Composable
private fun MedalInternal(
    modifier: Modifier = Modifier,
    medalColor: MedalColor,
    number: Int,
) = with(medalColor) {
    Box(
        modifier = modifier
            .size(28.dp)
            .background(
                color = outlineColor,
                shape = CircleShape,
            )
            .padding(all = 4.dp)
            .background(
                color = innerColor,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        QuackText(
            text = "$number",
            typography = QuackTypography.Title2.change(
                color = QuackColor(numberColor),
            ),
        )
    }
}

@AllowMagicNumber("메달 고유 색을 활용하기 위함")
private enum class MedalColor(
    val outlineColor: Color,
    val innerColor: Color,
    val numberColor: Color,
) {
    GOLD(
        outlineColor = Color(0xFFFFE777),
        innerColor = Color(0xFFFFD200),
        numberColor = Color(0xFFA65B00),
    ),
    SILVER(
        outlineColor = Color(0xFFE8E8EA),
        innerColor = Color(0xFFCFCFD0),
        numberColor = Color(0xFF47474E),
    ),
    BRONZE(
        outlineColor = Color(0xFFE7C2A6),
        innerColor = Color(0xFFD1AB8F),
        numberColor = Color(0xFF453128),
    ),
}

@AllowMagicNumber("set GoldMedalLine offset")
@Composable
fun DuckieMedal(
    score: Int,
) {
    when (score) {
        1 -> Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MedalInternal(
                modifier = Modifier
                    .zIndex(2f),
                number = score,
                medalColor = MedalColor.GOLD,
            )
            Image(
                modifier = Modifier
                    .offset(y = (-5).dp)
                    .zIndex(1f),
                painter = painterResource(id = R.drawable.ic_gold_medal_line),
                contentDescription = null,
            )
        }

        2 -> MedalInternal(
            number = score,
            medalColor = MedalColor.SILVER,
        )

        3 -> MedalInternal(
            number = score,
            medalColor = MedalColor.BRONZE,
        )

        else -> {
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center,
            ) {
                QuackText(
                    text = score.toString(),
                    typography = QuackTypography.Title2.change(
                        color = QuackColor.Gray1,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMedal() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DuckieMedal(score = 1)
        DuckieMedal(score = 2)
        DuckieMedal(score = 3)
        DuckieMedal(score = 4)
    }
}
