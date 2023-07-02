/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.dev.mode

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.constant.sharedComposeRString
import team.duckie.app.android.common.compose.ui.dialog.DuckieDialog
import team.duckie.app.android.common.compose.ui.quack.QuackNoUnderlineTextField
import team.duckie.app.android.core.datastore.PreferenceKey
import team.duckie.app.android.core.datastore.dataStore
import team.duckie.app.android.feature.dev.mode.viewmodel.DevModeViewModel
import team.duckie.app.android.feature.dev.mode.viewmodel.state.DevModeState
import team.duckie.app.android.feature.dev.mode.viewmodel.state.DuckieApi
import team.duckie.app.android.feature.dev.mode.viewmodel.state.toDuckieApi
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@Composable
fun DevModeDialog(
    viewModel: DevModeViewModel = activityViewModel(),
    visible: Boolean = false,
    onDismiss: () -> Unit,
) {
    val applicationContext = LocalContext.current.applicationContext
    val state = viewModel.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    DuckieDialog(
        container = {
            when (state) {
                is DevModeState.InputPassword -> InputPasswordScreen(
                    inputted = state.inputted,
                    editInputted = viewModel::editInputted,
                    gotoDevMode = viewModel::gotoDevMode,
                )

                is DevModeState.Success -> DevModeScreen(
                    state,
                    viewModel::changeApi,
                )
            }
        },
        leftButtonText = stringResource(id = sharedComposeRString.cancel),
        leftButtonOnClick = {
            viewModel.closeDevMode()
            onDismiss()
        },
        rightButtonText = stringResource(id = sharedComposeRString.check),
        rightButtonOnClick = {
            when (state) {
                is DevModeState.InputPassword -> {
                    coroutineScope.launch {
                        checkIsStage(applicationContext, viewModel::gotoDevMode)
                    }
                }

                is DevModeState.Success -> {
                    coroutineScope.launch {
                        applicationContext.dataStore.edit { preference ->
                            preference[PreferenceKey.DevMode.IsStage] = state.duckieApi.isStage
                        }
                        restartApp(applicationContext)
                    }
                }
            }
        },
        visible = visible,
        onDismissRequest = {},
    )
}

/** 앱 내에서 stage 변환된 값이 있는지 확인한다. 없다면 기본 flavor 값에 근거한다. */
// TODO(riflockle7): 추후 해당 로직 [DevModeViewModel] 로 이동 필요
private suspend fun checkIsStage(
    applicationContext: Context,
    gotoDevMode: (duckieApi: DuckieApi?) -> Unit,
) {
    applicationContext.dataStore.data.firstOrNull()?.let { preference ->
        gotoDevMode(preference[PreferenceKey.DevMode.IsStage].toDuckieApi())
    }
}

/** 앱을 재시작한다. */
private fun restartApp(context: Context) {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(context.packageName)
    val componentName = intent!!.component
    val mainIntent = Intent.makeRestartActivityTask(componentName)
    context.startActivity(mainIntent)
    Runtime.getRuntime().exit(0)
}

@Composable
fun InputPasswordScreen(
    inputted: String,
    editInputted: (String) -> Unit,
    gotoDevMode: (duckieApi: DuckieApi?) -> Unit,
) {
    val applicationContext = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()

    QuackHeadLine2(
        modifier = Modifier.padding(all = 28.dp),
        text = stringResource(id = R.string.dev_mode_input_password_title),
    )

    QuackNoUnderlineTextField(
        modifier = Modifier.padding(
            start = 28.dp,
            end = 28.dp,
            bottom = 28.dp,
        ),
        text = inputted,
        placeholderText = stringResource(id = R.string.dev_mode_input_password_title),
        onTextChanged = editInputted,
        keyboardActions = KeyboardActions(
            onDone = {
                coroutineScope.launch {
                    checkIsStage(applicationContext, gotoDevMode)
                }
            },
        ),
    )
}

@Composable
fun DevModeScreen(
    state: DevModeState.Success,
    changeApi: (DuckieApi) -> Unit,
) {
    QuackHeadLine2(
        modifier = Modifier.padding(all = 28.dp),
        text = stringResource(id = R.string.dev_mode_success_title),
    )

    Column {
        QuackHeadLine2(
            modifier = Modifier.padding(start = 28.dp),
            text = stringResource(id = R.string.dev_mode_success_api_env_title),
        )

        Row(
            modifier = Modifier.padding(start = 28.dp, top = 14.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            QuackTag(
                text = stringResource(id = R.string.dev_mode_success_api_env_item_stage),
                style = if (state.duckieApi.isStage) {
                    QuackTagStyle.Filled
                } else {
                    QuackTagStyle.Outlined
                },
                onClick = {
                    changeApi(DuckieApi.Stage)
                },
            )

            Spacer(modifier = Modifier.width(14.dp))

            QuackTag(
                text = stringResource(id = R.string.dev_mode_success_api_env_item_real),
                style = if (!state.duckieApi.isStage) {
                    QuackTagStyle.Filled
                } else {
                    QuackTagStyle.Outlined
                },
                onClick = {
                    changeApi(DuckieApi.Real)
                },
            )
        }
    }

    Spacer(modifier = Modifier.height(14.dp))
}
