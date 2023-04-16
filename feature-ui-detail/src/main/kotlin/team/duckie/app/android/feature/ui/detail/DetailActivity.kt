package team.duckie.app.android.feature.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe
import team.duckie.app.android.feature.ui.detail.screen.DetailScreen
import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.start.exam.screen.StartExamActivity
import team.duckie.app.android.util.exception.handling.reporter.reportToCrashlyticsIfNeeded
import team.duckie.app.android.util.exception.handling.reporter.reportToToast
import team.duckie.app.android.util.kotlin.DuckieResponseException
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.startActivityWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

/** 상세 화면 */
@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    private val vm: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                DetailScreen(
                    Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor)
                        .systemBarsPadding(),
                )
            }
        }

        vm.observe(
            lifecycleOwner = this,
            sideEffect = ::handleSideEffect,
        )
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

            is DetailSideEffect.StartExam -> startActivityWithAnimation<StartExamActivity>(
                intentBuilder = {
                    putExtra(Extras.ExamId, sideEffect.examId)
                    putExtra(Extras.CertifyingStatement, sideEffect.certifyingStatement)
                },
            )

            else -> {}
        }
    }
}
