/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import team.duckie.app.android.presentation.R
import team.duckie.app.android.presentation.viewmodel.IntroViewModel
import team.duckie.app.android.shared.ui.compose.constant.getAppPackageName
import team.duckie.app.android.common.android.intent.goToMarket
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackImage
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.shared.ui.compose.dialog.DuckieDialog

@Composable
internal fun IntroScreen(
    viewModel: IntroViewModel = activityViewModel(),
) {
    val activity = LocalContext.current as Activity
    val appPackageName = getAppPackageName()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.composeColor)
            .padding(systemBarPaddings)
            .padding(
                top = 78.dp,
                bottom = 34.dp,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuackImage(
                src = team.duckie.quackquack.ui.R.drawable.quack_duckie_text_logo,
                size = DpSize(
                    width = 110.dp,
                    height = 32.dp,
                ),
            )
            QuackHeadLine1(text = stringResource(R.string.intro_slogan))
        }
        QuackImage(
            modifier = Modifier.offset(x = 125.dp),
            src = R.drawable.img_duckie_intro,
            size = DpSize(
                width = 276.dp,
                height = 255.dp,
            ),
        )
    }

    // 덕키 업데이트 팝업
    viewModel.collectAsState().value.introDialogType?.let { introDialogType ->
        DuckieDialog(
            title = activity.getString(introDialogType.titleRes),
            message = activity.getString(introDialogType.messageRes),
            visible = true,
            leftButtonText = activity.getString(introDialogType.leftBtnTitleRes),
            leftButtonOnClick = { activity.finish() },
            rightButtonText = activity.getString(introDialogType.rightBtnTitleRes),
            rightButtonOnClick = {
                if (introDialogType != IntroDialogType.Error) {
                    activity.goToMarket(appPackageName)
                    activity.finish()
                } else {
                    viewModel.checkUpdateRequire()
                }
            },
            onDismissRequest = {},
        )
    }
}

/** IntroActivity 에서 띄워지는 Dialog 타입 */
enum class IntroDialogType(
    val titleRes: Int,
    val messageRes: Int,
    val leftBtnTitleRes: Int,
    val rightBtnTitleRes: Int,
) {
    UpdateRequire(
        R.string.update_require_dialog_title,
        R.string.update_require_dialog_description,
        R.string.update_require_dialog_left_button_title,
        R.string.update_require_dialog_right_button_title,
    ),
    Error(
        R.string.error_dialog_title,
        R.string.error_dialog_description,
        R.string.error_dialog_left_button_title,
        R.string.error_dialog_right_button_title,
    ),
}
