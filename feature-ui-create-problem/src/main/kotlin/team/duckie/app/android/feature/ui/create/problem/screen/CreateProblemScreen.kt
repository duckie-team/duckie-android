/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.create.problem.common.CreateProblemTopAppBar
import team.duckie.app.android.feature.ui.create.problem.common.TitleAndComponent
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.compose.component.DuckieGridLayout
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasicTextArea
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackMediumToggleButton
import team.duckie.quackquack.ui.component.QuackReviewTextArea
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun CreateProblemScreen() {
    val viewModel = LocalViewModel.current as CreateProblemViewModel
    val state = viewModel.state.collectAsStateWithLifecycle().value.examInformation

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .background(color = QuackColor.White.composeColor),
        topBar = {
            CreateProblemTopAppBar(
                onLeadingIconClick = {},
                onTrailingTextClick = {}
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 48.dp),
        ) {
            TitleAndComponent(title = "카테고리") {
                DuckieGridLayout(items = state.categories) { index, item ->
                    //TODO [EvergreenTree97] 컴포넌트 필요
                    QuackMediumToggleButton(
                        modifier = Modifier.size(
                            width = 102.dp,
                            height = 40.dp,
                        ),
                        text = item,
                        selected = state.categoriesSelection[index],
                        onClick = {
                            viewModel.onClickCategory(index)
                        },
                    )
                }
            }
            TitleAndComponent(title = "시험 영역") {
                QuackBasicTextField(
                    leadingIcon = QuackIcon.Search,
                    text = state.examArea,
                    onTextChanged = viewModel::setExamArea,
                    placeholderText = "시험 영역 찾기",
                )
            }

            TitleAndComponent(title = "시험 제목") {
                QuackBasicTextField(
                    text = state.examTitle,
                    onTextChanged = viewModel::setExamTitle,
                    placeholderText = "시험 제목을 입력해 주세요",
                )
            }

            TitleAndComponent(title = "설명") {
                QuackReviewTextArea(
                    modifier = Modifier.heightIn(140.dp),
                    text = state.examDescription,
                    onTextChanged = viewModel::setExamDescription,
                    placeholderText = "시험에 대한 설명을 입력해 주세요",
                )
            }
            TitleAndComponent(title = "필적 확인 문구") {
                //TODO [EvergreenTree97] 컴포넌트 필요
                QuackBasicTextArea(
                    text = state.certifyingStatement,
                    onTextChanged = viewModel::setCertifyingStatement,
                    placeholderText = "필적확인 문구를 입력해 주세요"
                )
            }
        }
    }
}
