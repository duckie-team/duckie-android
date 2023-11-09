/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalFoundationApi::class, ExperimentalDesignToken::class)

package team.duckie.app.android.feature.solve.problem.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.icon.v2.Clock
import team.duckie.app.android.common.compose.util.addFocusCleaner
import team.duckie.app.android.feature.solve.problem.R
import team.duckie.app.android.feature.solve.problem.common.CloseAndPageTopBar
import team.duckie.app.android.feature.solve.problem.common.MusicDoubleButtonBottomBar
import team.duckie.app.android.feature.solve.problem.question.audio.AudioPlayer
import team.duckie.app.android.feature.solve.problem.viewmodel.state.MusicExamState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.ui.QuackFilledTextField
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackTextFieldStyle
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi


private const val MusicTopAppBarLayoutId = "MusicTopAppBar"
private const val MusicContentLayoutId = "MusicContent"
private const val MusicBottomBarLayoutId = "MusicBottomBar"

@Composable
internal fun MusicScreen(
    state: MusicExamState,
    remainTime: () -> Float,
    startTimer: (Float) -> Unit,
    stopExam: () -> Unit,
    giveUpExam: () -> Unit,
) {
    var examExitDialogVisible by remember { mutableStateOf(false) }

    val timeOver by remember {
        derivedStateOf { remainTime() < 0f }
    }

    LaunchedEffect(timeOver) {
        if (timeOver) {

        }
    }

    BackHandler {
        examExitDialogVisible = true
    }

    DuckieDialog(
        title = stringResource(id = R.string.quit_music_exam),
        message = stringResource(id = R.string.available_continue),
        leftButtonText = stringResource(id = R.string.cancel),
        leftButtonOnClick = { examExitDialogVisible = false },
        rightButtonText = stringResource(id = R.string.quit_music),
        rightButtonOnClick = stopExam,
        visible = examExitDialogVisible,
        onDismissRequest = { examExitDialogVisible = false },
    )

    Layout(
        content = {
            CloseAndPageTopBar(
                modifier = Modifier
                    .layoutId(MusicTopAppBarLayoutId)
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .padding(end = 16.dp),
                onCloseClick = {
                    examExitDialogVisible = true
                },
                currentPage = 1,
                totalPage = state.totalPage,
            )
            ContentSection(
                modifier = Modifier
                    .layoutId(MusicContentLayoutId)
                    .padding(top = 8.dp),
                state = state,
                remainTime = remainTime,
            )
            MusicDoubleButtonBottomBar(
                modifier = Modifier
                    .layoutId(MusicBottomBarLayoutId)
                    .fillMaxWidth(),
                remainCount = 1,
                onLeftButtonClick = giveUpExam,
                onRightButtonClick = {},
            )
        },
        measurePolicy = screenMeasurePolicy(
            topLayoutId = MusicTopAppBarLayoutId,
            contentLayoutId = MusicContentLayoutId,
            bottomLayoutId = MusicBottomBarLayoutId,
        ),
    )
}

@OptIn(ExperimentalQuackQuackApi::class)
@Composable
private fun ContentSection(
    modifier: Modifier = Modifier,
    state: MusicExamState,
    remainTime: () -> Float,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(),
    ) {
        TimerClock(remainTime = remainTime)
        QuackHeadLine2(
            text = state.question.text,
        )
        AudioPlayer(url = state.question.mediaUri)
        if(state.hintUsage.useWordCountAndSpacing){
            QuackFilledTextField(
                style = QuackTextFieldStyle.FilledLarge,
                value = state.inputAnswer,
                onValueChange = ,
            )
        }else{

        }
    }
}

@Composable
private fun TimerClock(remainTime: () -> Float) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(QuackColor.Gray4.value),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            QuackIcon(
                icon = QuackIcon.Clock,
                size = 16.dp,
            )
            QuackTitle2(text = stringResource(id = R.string.count_down, remainTime().toString()))
        }
    }
}
