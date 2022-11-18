package land.sungbin.androidprojecttemplate.ui.onboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.constants.UiConstant.CATEGORY_COLUMN_COUNT
import land.sungbin.androidprojecttemplate.constants.UiConstant.WHITE_GRADIENT_HEIGHT
import land.sungbin.androidprojecttemplate.domain.model.constraint.LikeCategory
import land.sungbin.androidprojecttemplate.ui.component.BackArrowTopAppBar
import land.sungbin.androidprojecttemplate.ui.component.DuckieSimpleLayout
import land.sungbin.androidprojecttemplate.ui.component.TitleAndDescription
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackSelectableCardImage
import team.duckie.quackquack.ui.component.QuackSimpleGridLayout
import team.duckie.quackquack.ui.component.QuackTitle2

@Composable
internal fun OnboardCategoryScreen(
    isNextButtonVisible: Boolean,
    categories: PersistentList<LikeCategory>,
    selectedCategories: PersistentList<LikeCategory>,
    onClickCategory: (Boolean, LikeCategory) -> Unit,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
) {
    DuckieSimpleLayout(
        topAppBar = {
            BackArrowTopAppBar(
                onClick = onClickBack,
            )
        },
        content = {
            Spacer(modifier = Modifier.height(12.dp))
            TitleAndDescription(
                title = stringResource(R.string.onboard_category_title),
                description = stringResource(R.string.onboard_category_description),
            )
            Spacer(modifier = Modifier.height(24.dp))
            CategoryGridLayout(
                categories = categories,
                selectedCategories = selectedCategories,
                onClick = { index, checked ->
                    onClickCategory(checked, categories[index])
                },
            )
        },

        bottomContent = {
            OnboardingButton(
                title = stringResource(R.string.next),
                isVisible = isNextButtonVisible,
                onClick = onClickNext
            )
        }
    )
}

@Composable
private fun CategoryGridLayout(
    categories: PersistentList<LikeCategory>,
    selectedCategories: PersistentList<LikeCategory>,
    onClick: (Int, Boolean) -> Unit,
) {
    QuackSimpleGridLayout(
        columns = CATEGORY_COLUMN_COUNT,
        items = categories,
        horizontalSpace = 5.dp,
        verticalSpace = 12.dp,
        contentPadding = PaddingValues(bottom = WHITE_GRADIENT_HEIGHT),
        itemContent = { index, item ->
            CategoryItem(
                index = index,
                category = item,
                onClick = onClick,
                isChecked = selectedCategories.find { selectedCategory: LikeCategory ->
                    selectedCategory.id == item.id
                } != null
            )
        },
    )
}

@Composable
private fun CategoryItem(
    isChecked: Boolean,
    index: Int,
    category: LikeCategory,
    onClick: (Int, Boolean) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackSelectableCardImage(
            checked = isChecked,
            image = category.imageUrl,
            onClick = {
                onClick(index, !isChecked)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        QuackTitle2(
            text = category.title,
            color = when (isChecked) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.Black
            }
        )
    }
}