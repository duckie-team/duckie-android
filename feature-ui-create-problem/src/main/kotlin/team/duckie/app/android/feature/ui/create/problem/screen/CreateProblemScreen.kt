/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.app.android.feature.ui.create.problem.common.CreateProblemTopAppBar
import team.duckie.app.android.feature.ui.create.problem.common.TitleAndComponent
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.util.compose.component.DuckieGridLayout
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.component.QuackBasicTextArea
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackMediumToggleButton
import team.duckie.quackquack.ui.icon.QuackIcon

private val categories = persistentListOf("연예인", "영화", "만화/애니", "웹툰", "게임", "밀리터리")

@Stable
private val CategoryButtonSize = DpSize(
    width = 102.dp,
    height = 40.dp,
)

@Composable
internal fun CreateProblemScreen(
    viewModel: CreateProblemViewModel,
) {
    val categoriesSelectedIndex = remember {
        mutableStateListOf(
            elements = Array(
                size = categories.size,
                init = { false },
            )
        )
    }
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
                DuckieGridLayout(items = categories) { index, item ->
                    QuackMediumToggleButton(
                        modifier = Modifier.size(
                            width = 102.dp,
                            height = 40.dp,
                        ),
                        text = item,
                        selected = categoriesSelectedIndex[index],
                        onClick = {
                            categoriesSelectedIndex[index] = !categoriesSelectedIndex[index]
                        },
                    )
                }
            }
            TitleAndComponent(title = "시험 영역") {
                QuackBasicTextField(
                    leadingIcon = QuackIcon.Search,
                    text = "",
                    onTextChanged = { text -> text },
                    placeholderText = "시험 영역 찾기",
                )
            }

            TitleAndComponent(title = "시험 제목") {
                QuackBasicTextField(
                    text = "",
                    onTextChanged = { text -> text },
                    placeholderText = "시험 제목을 입력해 주세요",
                )
            }

            TitleAndComponent(title = "설명") {
                QuackBasicTextArea(
                    text = "",
                    onTextChanged = {},
                    placeholderText = "시험에 대한 설명을 입력해 주세요",
                )
            }
            TitleAndComponent(title = "필적 확인 문구") {
                QuackBasic2TextField(
                    text = "",
                    onTextChanged = {},
                )
            }
        }
    }
}
