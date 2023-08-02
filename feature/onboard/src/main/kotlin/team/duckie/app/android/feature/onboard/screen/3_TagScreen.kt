/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalQuackQuackApi::class,
)
@file:Suppress(
    "ConstPropertyName",
    "PrivatePropertyName",
    "SpacingBetweenDeclarationsWithAnnotations",
)

package team.duckie.app.android.feature.onboard.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.asLoose
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.domain.DuckieTagAddBottomSheet
import team.duckie.app.android.common.compose.ui.icon.v1.CloseId
import team.duckie.app.android.common.compose.ui.quack.todo.QuackCircleTag
import team.duckie.app.android.common.compose.ui.quack.todo.QuackSingeLazyRowTag
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.fastAny
import team.duckie.app.android.common.kotlin.fastFirstOrNull
import team.duckie.app.android.common.kotlin.fastFlatten
import team.duckie.app.android.common.kotlin.fastForEachIndexed
import team.duckie.app.android.common.kotlin.fastMap
import team.duckie.app.android.common.kotlin.npe
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.onboard.R
import team.duckie.app.android.feature.onboard.common.OnboardTopAppBar
import team.duckie.app.android.feature.onboard.common.TitleAndDescription
import team.duckie.app.android.feature.onboard.constant.OnboardStep
import team.duckie.app.android.feature.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.onboard.viewmodel.state.OnboardState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackButton
import team.duckie.quackquack.ui.QuackButtonStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private val currentStep = OnboardStep.Tag

private const val TagScreenTopAppBarLayoutId = "TagScreenTopAppBar"
private const val TagScreenTagSelectionLayoutId = "TagScreenTagSelection"
private const val TagScreenQuackLargeButtonLayoutId = "TagScreenQuackLargeButton"

private val TagScreenMeasurePolicy = MeasurePolicy { measurables, constraints ->
    val looseConstraints = constraints.asLoose()

    val topAppBarPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == TagScreenTopAppBarLayoutId
    }?.measure(looseConstraints) ?: npe()

    val quackLargeButtonPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == TagScreenQuackLargeButtonLayoutId
    }?.measure(looseConstraints) ?: npe()

    // TagSelection content 에는 vertical scroll 이 있음 (최대 높이 지정 필요)
    val tagSelectionConstraints = looseConstraints.copy(
        maxHeight = constraints.maxHeight - topAppBarPlaceable.height - quackLargeButtonPlaceable.height,
    )
    val tagSelectionPlaceable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == TagScreenTagSelectionLayoutId
    }?.measure(tagSelectionConstraints) ?: npe()

    layout(
        width = constraints.maxWidth,
        height = constraints.maxHeight,
    ) {
        topAppBarPlaceable.place(
            x = 0,
            y = 0,
        )
        tagSelectionPlaceable.place(
            x = 0,
            y = topAppBarPlaceable.height,
        )
        quackLargeButtonPlaceable.place(
            x = 0,
            y = constraints.maxHeight - quackLargeButtonPlaceable.height,
        )
    }
}

@Composable
internal fun TagScreen(vm: OnboardViewModel = activityViewModel()) {
    val keyboard = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()

    var isLoadingToFinish by remember { mutableStateOf(false) }
    val addedTags = remember { mutableStateListOf<Tag>() }

    val onboardState by vm.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true,
        )

    LaunchedEffect(sheetState) {
        val sheetStateFlow = snapshotFlow { sheetState.currentValue }
        sheetStateFlow.collect { state ->
            if (state == ModalBottomSheetValue.Hidden) {
                keyboard?.hide()
            }
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    DuckieTagAddBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { newAddedTags, clearAction ->
            coroutineScope.launch {
                addedTags.addAll(newAddedTags)
                clearAction()
                sheetState.hide()
            }
        },
        requestAddTag = vm::createTag,
        content = {
            Layout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = systemBarPaddings.calculateBottomPadding() + 16.dp),
                content = {
                    OnboardTopAppBar(
                        modifier = Modifier.layoutId(TagScreenTopAppBarLayoutId),
                        currentStep = currentStep,
                    )
                    TagSelection(
                        modifier = Modifier
                            .layoutId(TagScreenTagSelectionLayoutId)
                            .padding(vertical = 12.dp),
                        sheetState = sheetState,
                        addedTags = addedTags,
                        requestRemoveAddedTag = { index ->
                            addedTags.remove(addedTags[index])
                        },
                    )

                    // TODO(riflockle7): 문제 있으므로 꽥꽥 이슈 해결할 때까지 주석 제거하지 않음
                    // type = QuackLargeButtonType.Fill,
                    // isLoading = isLoadingToFinish,
                    QuackButton(
                        modifier = Modifier.layoutId(TagScreenQuackLargeButtonLayoutId),
                        style = QuackButtonStyle.PrimaryLarge,
                        text = stringResource(id = R.string.button_start_duckie),
                        enabled = true,
                    ) {
                        updateUserAndFinishOnboard(
                            coroutineScope = coroutineScope,
                            vm = vm,
                            onboardState = onboardState,
                            addedTags = addedTags,
                            updateIsLoadingToFinishState = { isLoading ->
                                isLoadingToFinish = isLoading
                            },
                        )
                    }
                },
                measurePolicy = TagScreenMeasurePolicy,
            )
        },
    )
}

private fun updateUserAndFinishOnboard(
    coroutineScope: CoroutineScope,
    vm: OnboardViewModel,
    onboardState: OnboardState,
    addedTags: List<Tag>,
    updateIsLoadingToFinishState: (isLoading: Boolean) -> Unit,
) {
    updateIsLoadingToFinishState(true)
    coroutineScope.launch {
        val profileImageUrlDeferred = onboardState.temporaryProfileImageFile?.let {
            vm.uploadProfileImage(file = it)
        }

        val profileImageUrl = profileImageUrlDeferred ?: ""

        vm.updateUser(
            id = vm.me.id,
            nickname = onboardState.temporaryNickname,
            profileImageUrl = profileImageUrl.takeIf { profileImageUrlDeferred != null },
            favoriteCategories = onboardState.selectedCategories,
            favoriteTags = addedTags,
        )

        updateIsLoadingToFinishState(false)
    }
}

@Composable
private fun TagSelection(
    modifier: Modifier,
    sheetState: ModalBottomSheetState,
    addedTags: SnapshotStateList<Tag>,
    requestRemoveAddedTag: (index: Int) -> Unit,
    vm: OnboardViewModel = activityViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val onboardState by vm.collectAsState()

    val hottestTags = remember(onboardState.selectedCategories) {
        List(
            size = onboardState.selectedCategories.size,
            init = { index ->
                onboardState.selectedCategories[index].popularTags?.fastMap(Tag::name).orEmpty()
            },
        )
    }
    val hottestTagSelections = remember(onboardState.selectedCategories) {
        List(
            size = onboardState.selectedCategories.size,
            init = { index ->
                mutableStateListOf(
                    elements = Array(
                        size = hottestTags[index].size,
                        init = { false },
                    ),
                )
            },
        )
    }

    LaunchedEffect(hottestTagSelections, addedTags) {
        // https://stackoverflow.com/a/70429284/14299073
        val hottestTagSelectionsFlow = snapshotFlow { hottestTagSelections.fastFlatten() }
        val addedTagsFlow = snapshotFlow { addedTags.toList() }

        // hottest tag 에 최소 1개가 선택됐거나, 사용자가 최소 1개의 태그를 추가했을 때
        hottestTagSelectionsFlow.combine(addedTagsFlow) { hottestTagSelections, addedTags ->
            hottestTagSelections.fastAny { it } || addedTags.isNotEmpty()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TitleAndDescription(
            modifier = Modifier.padding(horizontal = 20.dp),
            titleRes = R.string.tag_title,
            descriptionRes = R.string.tag_description,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
        ) {
            QuackTitle2(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(R.string.tag_added_tag),
            )
            if (addedTags.isNotEmpty()) {
                // TODO(sungbin): 반주자 제거 (foundation 으로 합체됨)
                FlowRow(
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 20.dp,
                    ),
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                ) {
                    addedTags.fastForEachIndexed { index, tag ->
                        QuackCircleTag(
                            text = tag.name,
                            isSelected = false,
                        ) {
                            requestRemoveAddedTag(index)
                        }
                    }
                }
            }
            QuackText(
                modifier = Modifier
                    .quackClickable(
                        onClick = {
                            coroutineScope.launch {
                                sheetState.show()
                            }
                        },
                    )
                    .padding(
                        top = if (addedTags.isNotEmpty()) 0.dp else 8.dp,
                        start = 20.dp,
                        end = 10.dp,
                        bottom = 8.dp,
                    ),
                text = stringResource(R.string.tag_add_manual),
                typography = QuackTypography.HeadLine2.change(color = QuackColor.DuckieOrange),
            )
        }
        @AllowMagicNumber(because = "(34 - 8).dp")
        Column(
            modifier = Modifier
                .padding(top = (34 - 8).dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            onboardState.selectedCategories.fastForEachIndexed { categoryIndex, category ->
                QuackSingeLazyRowTag(
                    title = stringResource(R.string.tag_hottest_tag, category.name),
                    items = hottestTags[categoryIndex],
                    itemSelections = hottestTagSelections[categoryIndex],
                    trailingIconResId = null,
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    onClick = { tagIndex ->
                        hottestTagSelections[categoryIndex][tagIndex] =
                            !hottestTagSelections[categoryIndex][tagIndex]
                    },
                )
            }
        }
    }
}
