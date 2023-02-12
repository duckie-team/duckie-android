/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalLifecycleComposeApi::class,
)

package team.duckie.app.android.feature.ui.create.problem

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.create.problem.screen.AdditionalInformationScreen
import team.duckie.app.android.feature.ui.create.problem.screen.CreateProblemScreen
import team.duckie.app.android.feature.ui.create.problem.screen.ErrorScreen
import team.duckie.app.android.feature.ui.create.problem.screen.ExamInformationScreen
import team.duckie.app.android.feature.ui.create.problem.screen.LoadingScreen
import team.duckie.app.android.feature.ui.create.problem.screen.SearchTagScreen
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.ui.create.problem.viewmodel.sideeffect.CreateProblemSideEffect
import team.duckie.app.android.feature.ui.create.problem.viewmodel.state.CreateProblemStep
import team.duckie.app.android.util.exception.handling.reporter.reportToToast
import team.duckie.app.android.util.kotlin.DuckieResponseException
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class CreateProblemActivity : BaseActivity() {

    private val viewModel: CreateProblemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val createProblemStep = viewModel.collectAsState().value.createProblemStep

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
                QuackAnimatedContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor),
                    targetState = createProblemStep,
                ) { step: CreateProblemStep ->
                    when (step) {
                        CreateProblemStep.Loading -> LoadingScreen(
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
                        )
                    }
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: CreateProblemSideEffect) {
        when (sideEffect) {
            CreateProblemSideEffect.FinishActivity -> {
                finishWithAnimation()
            }

            is CreateProblemSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)

                // API 오류 발생 시 토스트 메시지 발생
                // TODO(riflockle7): 아마 각 statusCode 마다 발생시킬 토스트도 다를 수 있음
                //  해당 내용 추후 맞춰야 함
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
