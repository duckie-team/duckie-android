/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("ConstPropertyName", "PrivatePropertyName")

package team.duckie.app.android.feature.onboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.onboard.R
import team.duckie.app.android.feature.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.onboard.constant.OnboardStep
import team.duckie.app.android.feature.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.kotlin.fastAny
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.fastMapIndexedNotNull
import team.duckie.app.android.common.kotlin.npe
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackGridLayout
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackSelectableImageType
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.util.DpSize

private val currentStep = OnboardStep.Category

private const val CategoryScreenTopAppBarLayoutId = "CategoryScreenTopAppBar"
private const val CategoryScreenTitleAndDescriptionLayoutId = "CategoryScreenTitleAndDescription"
private const val CategoryScreenCategoriesGridLayoutId = "CategoryScreenQuackGridLayout"
private const val CategoryScreenNextButtonLayoutId = "CategoryScreenQuackAnimatedVisibility"

private val CategoryScreenMeasurePolicy = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()

    val topAppBarPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == CategoryScreenTopAppBarLayoutId
    }?.measure(looseConstraints) ?: npe()

    val titileAndDescriptionPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == CategoryScreenTitleAndDescriptionLayoutId
    }?.measure(looseConstraints) ?: npe()

    val categoriesGridPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == CategoryScreenCategoriesGridLayoutId
    }?.measure(looseConstraints) ?: npe()

    val goneableNextButtonPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == CategoryScreenNextButtonLayoutId
    }?.measure(looseConstraints)

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarPlaceable.place(
            x = 0,
            y = 0,
        )
        titileAndDescriptionPlaceable.place(
            x = 0,
            y = topAppBarPlaceable.height,
        )
        categoriesGridPlaceable.place(
            x = 0,
            y = topAppBarPlaceable.height + titileAndDescriptionPlaceable.height,
        )
        goneableNextButtonPlaceable?.place(
            x = 0,
            y = constraints.maxHeight - goneableNextButtonPlaceable.height,
        )
    }
}

@Composable
internal fun CategoryScreen(vm: OnboardViewModel = activityViewModel()) {
    val onboardState by vm.collectAsState()

    val categoriesSelectedIndex = remember(onboardState.categories, onboardState.selectedCategories) {
        mutableStateListOf(
            elements = Array(
                size = onboardState.categories.size,
                init = { index ->
                    onboardState.selectedCategories.contains(onboardState.categories[index])
                },
            ),
        )
    }

    Layout(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = systemBarPaddings.calculateBottomPadding() + 16.dp),
        content = {
            OnboardTopAppBar(
                modifier = Modifier.layoutId(CategoryScreenTopAppBarLayoutId),
                currentStep = currentStep,
            )
            TitleAndDescription(
                modifier = Modifier
                    .layoutId(CategoryScreenTitleAndDescriptionLayoutId)
                    .padding(
                        top = 12.dp,
                        start = 20.dp,
                        end = 20.dp,
                    ),
                titleRes = R.string.category_title,
                descriptionRes = R.string.category_description,
            )
            QuackGridLayout(
                modifier = Modifier
                    .layoutId(CategoryScreenCategoriesGridLayoutId)
                    .padding(
                        top = 24.dp,
                        start = 20.dp,
                        end = 20.dp,
                    )
                    .fillMaxSize(),
                verticalSpacing = 24.dp,
                horizontalSpacing = 10.dp,
                items = onboardState.categories.toImmutableList(),
                key = { _, category -> category.id },
            ) { index, category ->
                category.thumbnailUrl?.run {
                    CategoryItem(
                        imageUrl = this,
                        name = category.name,
                        isSelected = categoriesSelectedIndex[index],
                        onClick = {
                            categoriesSelectedIndex[index] = !categoriesSelectedIndex[index]
                        },
                    )
                }
            }
            QuackAnimatedVisibility(
                modifier = Modifier
                    .layoutId(CategoryScreenNextButtonLayoutId)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                visible = categoriesSelectedIndex.fastAny { it },
            ) {
                QuackLargeButton(
                    type = QuackLargeButtonType.Fill,
                    enabled = true,
                    text = stringResource(R.string.button_next),
                ) {
                    vm.updateUserSelectCategories(
                        categories = categoriesSelectedIndex.fastMapIndexedNotNull { index, selected ->
                            onboardState.categories[index].takeIf { selected }
                        },
                    )
                    vm.navigateStep(currentStep + 1)
                }
            }
        },
        measurePolicy = CategoryScreenMeasurePolicy,
    )
}

// FIXME(sungbin): 디자인상 100.dp 가 맞는데, 100 을 주면 디바이스 너비에 압축됨
private val CategoryImageSize = DpSize(all = 80.dp)
private val CategoryItemShape = RoundedCornerShape(size = 12.dp)

@Composable
private fun CategoryItem(
    imageUrl: String,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackSelectableImage(
            src = imageUrl,
            size = CategoryImageSize,
            shape = CategoryItemShape,
            selectableType = QuackSelectableImageType.CheckOverlay,
            isSelected = isSelected,
            onClick = onClick,
        )
        QuackTitle2(text = name)
    }
}
