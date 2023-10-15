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
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.intent.goToMarket
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.systemBarPaddings
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.presentation.R
import team.duckie.app.android.presentation.viewmodel.IntroViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.sugar.QuackHeadLine1

@Composable
internal fun IntroScreen(
    viewModel: IntroViewModel = activityViewModel(),
) {
    val activity = LocalContext.current as Activity
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.intro_bg_splash),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = QuackColor.White.value)
            .padding(systemBarPaddings)
            .padding(
                top = 110.dp,
                bottom = 139.dp,
            ),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuackImage(src = R.drawable.intro_duckie_text_logo)
            QuackHeadLine1(text = stringResource(R.string.intro_slogan))
        }
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
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
                    activity.goToMarket()
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
