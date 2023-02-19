/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.search.screen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import team.duckie.app.android.feature.ui.search.viewmodel.SearchResultViewModel
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchResultSideEffect
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class SearchResultActivity : BaseActivity() {

    private val searchResultViewModel: SearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO(limsaehyun) getExtra 필요
        searchResultViewModel.changeSearchTag("웹툰")

        setContent {
            LaunchedEffect(key1 = searchResultViewModel) {
                searchResultViewModel.container.sideEffectFlow
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            QuackTheme {
                SearchResultScreen(
                    modifier = Modifier
                        .padding(systemBarPaddings)
                        .background(QuackColor.White.composeColor),
                ) {
                    finishWithAnimation()
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: SearchResultSideEffect) {
        when (sideEffect) {
            is SearchResultSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }
        }
    }
}
