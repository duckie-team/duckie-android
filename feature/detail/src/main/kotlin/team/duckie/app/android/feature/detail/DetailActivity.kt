/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.common.android.deeplink.DynamicLinkHelper
import team.duckie.app.android.common.android.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.common.android.exception.handling.reporter.reportToToast
import team.duckie.app.android.common.android.ui.BaseActivity
import team.duckie.app.android.common.android.ui.const.Extras
import team.duckie.app.android.common.compose.ToastWrapper
import team.duckie.app.android.common.kotlin.exception.DuckieResponseException
import team.duckie.app.android.feature.detail.screen.DetailScreen
import team.duckie.app.android.feature.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.navigator.feature.examresult.ExamResultNavigator
import team.duckie.app.android.navigator.feature.profile.ProfileNavigator
import team.duckie.app.android.navigator.feature.search.SearchNavigator
import team.duckie.app.android.navigator.feature.startexam.StartExamNavigator
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.theme.QuackTheme
import javax.inject.Inject

/** 상세 화면 */
@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    private val vm: DetailViewModel by viewModels()

    @Inject
    lateinit var searchNavigator: SearchNavigator

    @Inject
    lateinit var profileNavigator: ProfileNavigator

    @Inject
    lateinit var startExamNavigator: StartExamNavigator

    @Inject
    lateinit var examResultNavigator: ExamResultNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuackTheme {
                DetailScreen(
                    Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.value)
                        .systemBarsPadding(),
                )
            }
        }

        vm.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        vm.parseDynamicLink()
    }

    override fun onStart() {
        super.onStart()
        vm.refresh()
    }

    private fun handleSideEffect(sideEffect: DetailSideEffect) {
        when (sideEffect) {
            is DetailSideEffect.ReportError -> {
                sideEffect.exception.printStackTrace()
                sideEffect.exception.reportToCrashlyticsIfNeeded()
                if (sideEffect.exception is DuckieResponseException) {
                    if (sideEffect.exception.code == "CAN_NOT_RETRY_EXAM") {
                        sideEffect.exception.reportToToast(getString(R.string.exam_already))
                    }
                }
            }

            is DetailSideEffect.NavigateToMyPage -> {
                profileNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.UserId, sideEffect.userId)
                    },
                )
            }

            is DetailSideEffect.StartExam -> startExamNavigator.navigateFrom(
                activity = this,
                intentBuilder = {
                    putExtra(Extras.ExamId, sideEffect.examId)
                    putExtra(Extras.CertifyingStatement, sideEffect.certifyingStatement)
                    putExtra(Extras.IsQuiz, sideEffect.isQuiz)
                },
            )

            is DetailSideEffect.NavigateToSearch -> {
                searchNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.SearchTag, sideEffect.searchTag)
                    },
                )
            }

            is DetailSideEffect.SendToast -> ToastWrapper(this).invoke(sideEffect.message)
            is DetailSideEffect.StartQuiz -> startExamNavigator.navigateFrom(
                activity = this,
                intentBuilder = {
                    putExtra(Extras.ExamId, sideEffect.examId)
                    putExtra(Extras.Timer, sideEffect.timer)
                    putExtra(Extras.RequirementQuestion, sideEffect.requirementQuestion)
                    putExtra(Extras.RequirementPlaceholder, sideEffect.requirementPlaceholder)
                    putExtra(Extras.IsQuiz, sideEffect.isQuiz)
                },
            )

            is DetailSideEffect.NavigateToExamResult -> {
                examResultNavigator.navigateFrom(
                    activity = this,
                    intentBuilder = {
                        putExtra(Extras.ExamId, sideEffect.examId)
                        putExtra(Extras.IsQuiz, false)
                        putExtra(Extras.IsPassed, true)
                    },
                )
            }

            is DetailSideEffect.CopyExamIdDynamicLink -> {
                DynamicLinkHelper.createAndShareLink(this, sideEffect.examId)
            }
        }
    }
}
