/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.problem

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.common.android.exception.handling.reporter.reportToToast
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.finishWithAnimation
import team.duckie.app.android.common.compose.ui.ErrorScreen
import team.duckie.app.android.common.compose.ui.LoadingScreen
import team.duckie.app.android.common.kotlin.exception.DuckieResponseException
import team.duckie.app.android.feature.create.problem.screen.AdditionalInformationScreen
import team.duckie.app.android.feature.create.problem.screen.CreateProblemScreen
import team.duckie.app.android.feature.create.problem.screen.ExamInformationScreen
import team.duckie.app.android.feature.create.problem.screen.SearchTagScreen
import team.duckie.app.android.feature.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackTitle1
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class CreateProblemActivity : BaseActivity() {

    private val viewModel: CreateProblemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val rootState = viewModel.collectAsState().value
            val createProblemStep = rootState.createProblemStep
            val isMakeExamUploading = remember(rootState.isMakeExamUploading) {
                rootState.isMakeExamUploading
            }

            BackHandler {
                when (createProblemStep) {
                    CreateProblemStep.Loading, CreateProblemStep.Error -> finishWithAnimation()
                    else -> {}
                }
            }

            LaunchedEffect(viewModel) {
                viewModel.container.sideEffectFlow
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            QuackTheme {
                AnimatedContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor),
                    targetState = createProblemStep,
                    label = "AnimatedContent"
                ) { step: CreateProblemStep ->
                    when (step) {
                        CreateProblemStep.Loading -> LoadingScreen(
                            initState = viewModel::initState,
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding(),
                        )

                        CreateProblemStep.ExamInformation -> ExamInformationScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding(),
                        )

                        CreateProblemStep.Search -> SearchTagScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding(),
                        )

                        CreateProblemStep.CreateProblem -> CreateProblemScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding(),
                        )

                        CreateProblemStep.AdditionalInformation -> AdditionalInformationScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding(),
                        )

                        CreateProblemStep.Error -> ErrorScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding(),
                            isNetworkError = rootState.isNetworkError,
                            onRetryClick = viewModel::refresh,
                        )
                    }
                }
            }

            if (isMakeExamUploading) {
                Column(
                    modifier = Modifier
                        .quackClickable(rippleEnabled = false) {}
                        .fillMaxSize()
                        .background(color = QuackColor.Dimmed.composeColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    // 로딩 바
                    CircularProgressIndicator(
                        color = QuackColor.DuckieOrange.composeColor,
                    )

                    // 제목
                    QuackTitle1(
                        text = stringResource(id = R.string.make_exam_loading),
                        color = QuackColor.White,
                        padding = PaddingValues(top = 8.dp),
                    )
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: CreateProblemSideEffect) {
        when (sideEffect) {
            CreateProblemSideEffect.FinishActivity -> {
                finishWithAnimation()
            }

            is CreateProblemSideEffect.TagAlreadyExist -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
                val message = getString(R.string.post_tag_already_exist_error, sideEffect.tagName)
                sideEffect.exception.reportToToast(message)
            }

            is CreateProblemSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)

                // API 오류 발생 시 토스트 메시지 발생
                if (sideEffect.exception is DuckieResponseException) {
                    sideEffect.exception.reportToToast(getString(R.string.network_error))
                }
            }

            is CreateProblemSideEffect.UpdateGalleryImages -> {
                viewModel.addGalleryImages(sideEffect.images)
            }
        }
    }
}
