/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalDesignToken::class, ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.create.exam.common.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.domain.exam.model.Question
import team.duckie.app.android.feature.create.exam.R
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackSwitch
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

/** 문제 항목 Layout 내 공통 제목 Layout */
@Composable
internal fun TitleView(
    questionIndex: Int,
    question: Question?,
    titleChanged: (String) -> Unit,
    onImageClick: () -> Unit,
    onImageClear: () -> Unit,
) {
    var addPhotoEnabled: Boolean by remember { mutableStateOf(false) }

    QuackNoUnderlineTextField(
        modifier = Modifier,
        text = question?.text ?: "",
        textTypography = QuackTypography.HeadLine2,
        onTextChanged = titleChanged,
        placeholderText = stringResource(
            id = R.string.create_problem_question_placeholder,
            "${questionIndex + 1}",
        ),
    )

    // 공백
    Spacer(modifier = Modifier.height(24.dp))

    // 사진 첨부
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackBody1(text = "사진 첨부")

        QuackSwitch(enabled = addPhotoEnabled) {
            addPhotoEnabled = !addPhotoEnabled
            if (!addPhotoEnabled) {
                onImageClear()
            }
        }
    }

    // 썸네일 사진 (비활성화시 보여주지 않음)
    if (addPhotoEnabled) {
        QuackImage(
            modifier = Modifier
                .padding(top = 8.dp)
                .quackClickable(onClick = onImageClick)
                .fillMaxWidth(),
            src = (question as? Question.Image)?.imageUrl
                ?: R.drawable.img_create_exam_title_thumbnail_empty,
        )
    }

    // 공백
    Spacer(modifier = Modifier.height(28.dp))
}
