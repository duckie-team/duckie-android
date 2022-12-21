/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalLifecycleComposeApi::class)

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import team.duckie.app.android.feature.ui.create.problem.R
import team.duckie.app.android.feature.ui.create.problem.common.ExitAppBar
import team.duckie.app.android.feature.ui.create.problem.common.SearchResultText
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun FindExamAreaScreen() {
    val viewModel = LocalViewModel.current as CreateProblemViewModel
    val state = viewModel.state.collectAsStateWithLifecycle().value.examInformation.foundExamArea
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            ExitAppBar(
                leadingText = stringResource(id = R.string.find_exam_area),
                onTrailingIconClick = {
                    viewModel.navigateStep(CreateProblemStep.ExamInformation)
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuackBasicTextField(
                modifier = Modifier.focusRequester(focusRequester),
                leadingIcon = QuackIcon.Search,
                text = state.examArea,
                onTextChanged = viewModel::setExamArea,
                placeholderText = stringResource(id = R.string.find_exam_area),
            )
            QuackAnimatedVisibility(visible = state.examArea.isNotEmpty()) {
                LazyColumn {
                    item {
                        SearchResultText(
                            text = stringResource(
                                id = R.string.add_also,
                                state.examArea,
                            ),
                            onClick = viewModel::onClickSearchListHeader,
                        )
                    }
                    itemsIndexed(
                        items = state.searchResults,
                        key = { _, item -> item },
                    ) { index: Int, item: String ->
                        SearchResultText(
                            text = item,
                            onClick = {
                                viewModel.onClickSearchList(index)
                            }
                        )
                    }
                }
            }
        }
    }
}
