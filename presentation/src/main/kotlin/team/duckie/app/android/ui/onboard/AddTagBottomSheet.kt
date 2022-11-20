package team.duckie.app.android.ui.onboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.R
import team.duckie.app.constants.DuckieConst.MIN_TAG_LENGTH
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBottomSheet
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSingleRowTag
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTextField
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun AddTagBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    onClickComplete: () -> Unit,
    content: @Composable () -> Unit,
) {
    QuackBottomSheet(
        bottomSheetState = bottomSheetState,
        sheetContent = {
            AddTagBottomSheetContent(
                onClickComplete = onClickComplete,
            )
        },
        content = content,
    )
}

@Composable
internal fun AddTagBottomSheetContent(
    onClickComplete: () -> Unit,
) {
    var tagText by remember { mutableStateOf("") }
    var isValidTag by remember { mutableStateOf(false) }
    val newTags = remember { mutableStateListOf<String>() }
    val itemsSelection = remember(
        key1 = newTags.size,
    ) {
        List(
            size = newTags.size,
            init = { false },
        )
    }

    LaunchedEffect(tagText.length) {
        isValidTag = tagText.length >= MIN_TAG_LENGTH
    }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            QuackTitle2(text = stringResource(R.string.adding_tag))
            QuackSubtitle(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = stringResource(R.string.complete),
                onClick = {
                    newTags.clear()
                    onClickComplete()
                }
            )
        }
        if (newTags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            QuackSingleRowTag(
                items = newTags.toList(),
                onClick = { index ->
                    newTags.remove(newTags[index])
                },
                itemsSelection = itemsSelection,
            )
        }

        QuackTextField(
            text = tagText,
            onTextChanged = { text ->
                tagText = text
            },
            placeholderText = stringResource(R.string.adding_tag_placeholder),
            trailingContent = {
                QuackImage(
                    src = QuackIcon.ArrowSend,
                    onClick = {
                        if (isValidTag) {
                            newTags.add(tagText)
                            tagText = ""
                        }
                    },
                    tint = when (isValidTag) {
                        true -> QuackColor.DuckieOrange
                        else -> QuackColor.Black
                    }
                )
            }
        )
    }
}
