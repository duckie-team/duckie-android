/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.onboard.R
import team.duckie.app.android.feature.ui.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.kotlin.fastAny
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.component.QuackGridLayout
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.util.DpSize

// TODO: 카테고리 가져오는 방식이 바뀔 수 있어서 임시 하드코딩
private val categories = persistentListOf(
    "연예인" to R.drawable.img_category_celebrity,
    "영화" to R.drawable.img_category_movie,
    "만화/애니" to R.drawable.img_category_cartoon,
    "웹툰" to R.drawable.img_category_webtoon,
    "게임" to R.drawable.img_category_game,
    "밀리터리" to R.drawable.img_category_military,
    "IT" to R.drawable.img_category_it,
)

@Composable
internal fun CategoryScreen() {
    val vm = LocalViewModel.current as OnboardViewModel
    val categoriesSelection = remember {
        mutableStateListOf(
            elements = Array(
                size = categories.size,
                init = { false },
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 12.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 28.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TitleAndDescription(
            titleRes = R.string.category_title,
            descriptionRes = R.string.category_description,
        )
        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            // TODO: 사이 간격 조정
            QuackGridLayout(
                modifier = Modifier.fillMaxSize(),
                items = categories,
                key = { _, (name, _) -> name },
            ) { index, (name, imageRes) ->
                CategoryItem(
                    imageRes = imageRes,
                    name = name,
                    isSelected = categoriesSelection[index],
                    onClick = {
                        categoriesSelection[index] = !categoriesSelection[index]
                    },
                )
            }
            // TODO: QuackAnimatedVisibility
            this@Column.AnimatedVisibility(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                visible = categoriesSelection.fastAny { it },
                enter = fadeIn(animationSpec = QuackAnimationSpec()),
                exit = fadeOut(animationSpec = QuackAnimationSpec()),
            ) {
                // TODO: fading edge support
                QuackLargeButton(
                    type = QuackLargeButtonType.Fill,
                    enabled = true,
                    text = stringResource(R.string.button_next),
                ) {
                    vm.updateStep(vm.currentStep + 1)
                }
            }
        }
    }
}

private val CategoryImageSize = DpSize(all = 100.dp)
private val CategoryItemShape = RoundedCornerShape(size = 12.dp)

@Composable
private fun CategoryItem(
    @DrawableRes imageRes: Int,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    println(isSelected)
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        // TODO: selection 방식 변경 (이미지 위에 체크 오버레이 타입 추가)
        // TODO: shape 인자 추가
        // FIXME: isSelected 가 작동하지 않음
        QuackSelectableImage(
            modifier = Modifier.clip(CategoryItemShape),
            src = imageRes,
            size = CategoryImageSize,
            isSelected = isSelected,
            onClick = onClick,
        )
        QuackTitle2(text = name)
    }
}
