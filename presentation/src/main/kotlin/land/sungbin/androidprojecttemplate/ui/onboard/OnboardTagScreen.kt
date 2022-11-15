package land.sungbin.androidprojecttemplate.ui.onboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.constants.UiConstant.WHITE_GRADIENT_HEIGHT
import land.sungbin.androidprojecttemplate.data.model.auth.Category
import land.sungbin.androidprojecttemplate.data.model.auth.Tag
import land.sungbin.androidprojecttemplate.ui.component.DuckieSimpleLayout
import land.sungbin.androidprojecttemplate.ui.component.TitleAndDescription
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackMultiLineTagRow
import team.duckie.quackquack.ui.component.QuackSingleRowTag
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun OnboardTagScreen(
    categories: PersistentList<land.sungbin.androidprojecttemplate.data.model.auth.Category>,
    onClickBack: () -> Unit,
    onClickComplete: () -> Unit,
) {
    val tags = categories.map { it.popularTags }.flatten()
    val keyboardController = LocalSoftwareKeyboardController.current


    val itemsSelection = remember {
        mutableStateListOf(
            elements = Array(
                size = tags.size,
                init = { false },
            )
        )
    }
    val coroutineScope = rememberCoroutineScope()

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val selectedTags = remember { mutableStateListOf<land.sungbin.androidprojecttemplate.data.model.auth.Tag>() }

    LaunchedEffect(bottomSheetState.isVisible) {
        if (!bottomSheetState.isVisible) {
            keyboardController?.hide()
        }
    }

    AddTagBottomSheet(
        bottomSheetState = bottomSheetState,
        onClickComplete = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        }
    ) {
        DuckieSimpleLayout(
            topAppBar = {
                QuackTopAppBar(
                    leadingIcon = QuackIcon.ArrowBack,
                    onClickLeadingIcon = onClickBack,
                    trailingText = stringResource(R.string.skip),
                    onClickTrailingText = onClickComplete,
                )
            },
            content = {
                Spacer(modifier = Modifier.height(14.dp))
                TitleAndDescription(
                    title = stringResource(R.string.onboard_tag_title),
                    description = stringResource(R.string.onboard_tag_description),
                )
                Spacer(modifier = Modifier.height(28.dp))
                QuackMultiLineTagRow(
                    title = stringResource(R.string.adding_tag),
                    items = selectedTags.map { it.title }.toPersistentList(),
                    icon = QuackIcon.Close,
                    onClickIcon = { index ->
                        val findTag = tags.find { selectedTags[index].id == it.id }
                        val itemsSelectionIndex = tags.indexOf(findTag)
                        selectedTags.remove(selectedTags[index])
                        itemsSelection[itemsSelectionIndex] = false
                    }
                )
                if (selectedTags.isNotEmpty()) Spacer(modifier = Modifier.height(12.dp))
                QuackHeadLine2(
                    text = stringResource(R.string.adding_tag_self),
                    color = QuackColor.DuckieOrange,
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    rippleEnabled = true,
                )
                categories.forEachIndexed { categoryIndex, category ->

                    /**
                     * TODO : 인기 태그가 무적권 10개라고 단정지어 버림 => 서버 연동후에 수정
                     */

                    Spacer(modifier = Modifier.height(16.dp))
                    CategoryPopularTags(
                        category = category,
                        onClickTag = { index ->
                            val tagIndex = categoryIndex * 10 + index
                            itemsSelection[tagIndex] =
                                !itemsSelection[tagIndex]
                            if (itemsSelection[tagIndex]) {
                                selectedTags.add(tags[tagIndex])
                            } else {
                                selectedTags.remove(tags[tagIndex])
                            }
                        },
                        itemsSelection = itemsSelection.subList(
                            categoryIndex * 10,
                            categoryIndex * 10 + 10
                        ),
                    )
                    if (categoryIndex == categories.size - 1) Spacer(
                        modifier = Modifier.height(
                            WHITE_GRADIENT_HEIGHT
                        )
                    )
                }
            },
            bottomContent = {
                OnboardingButton(
                    title = stringResource(R.string.start_duckie),
                    isVisible = true,
                    onClick = onClickComplete,
                )
            }
        )
    }
}

@Composable
private fun CategoryPopularTags(
    category: land.sungbin.androidprojecttemplate.data.model.auth.Category,
    itemsSelection: List<Boolean>,
    onClickTag: (Int) -> Unit,
) {
    QuackSingleRowTag(
        title = stringResource(R.string.category_popular_tag, category.title),
        items = category.popularTags.map { it.title }.toPersistentList(),
        itemsSelection = itemsSelection,
        onClick = onClickTag,
    )
}