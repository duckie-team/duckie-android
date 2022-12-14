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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.create.problem.R
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
            TitleAndComponent(stringResource = R.string.create_problem_category_title) {
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
            TitleAndComponent(stringResource = R.string.create_problem_exam_area) {
                QuackBasicTextField(
                    leadingIcon = QuackIcon.Search,
                    text = state.examArea,
                    onTextChanged = viewModel::setExamArea,
                    placeholderText = stringResource(id = R.string.create_problem_find_exam_area),
                )
            }

            TitleAndComponent(stringResource = R.string.create_problem_exam_title) {
                QuackBasicTextField(
                    text = state.examTitle,
                    onTextChanged = viewModel::setExamTitle,
                    placeholderText = stringResource(id = R.string.create_problem_input_exam_title),
                )
            }

            TitleAndComponent(stringResource = R.string.create_problem_exam_description) {
                QuackReviewTextArea(
                    modifier = Modifier.heightIn(140.dp),
                    text = state.examDescription,
                    onTextChanged = viewModel::setExamDescription,
                    placeholderText = stringResource(id = R.string.create_problem_input_exam_description),
                )
            }
            TitleAndComponent(stringResource = R.string.create_problem_certifying_statement) {
                //TODO [EvergreenTree97] 컴포넌트 필요
                QuackBasicTextArea(
                    text = state.certifyingStatement,
                    onTextChanged = viewModel::setCertifyingStatement,
                    placeholderText = stringResource(id = R.string.create_problem_input_certifying_statement),
                )
            }
        }
    }
}
