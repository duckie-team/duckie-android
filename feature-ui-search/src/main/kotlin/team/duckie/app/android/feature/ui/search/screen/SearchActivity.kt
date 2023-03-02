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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.search.constants.SearchStep
import team.duckie.app.android.feature.ui.search.viewmodel.SearchViewModel
import team.duckie.app.android.feature.ui.search.viewmodel.sideeffect.SearchSideEffect
import team.duckie.app.android.shared.ui.compose.DuckieCircularProgressIndicator
import team.duckie.app.android.util.compose.systemBarPaddings
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.app.android.util.ui.popStringExtra
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private val vm: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.popStringExtra(Extras.SearchTag)?.let { str ->
            vm.updateSearchKeyword(
                keyword = str,
                debounce = false,
            )
        }

        setContent {
            val state = vm.collectAsState().value

            LaunchedEffect(key1 = vm) {
                vm.container.sideEffectFlow
                    .onEach(::handleSideEffect)
                    .launchIn(this)
            }

            QuackTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    QuackAnimatedContent(
                        modifier = Modifier
                            .background(QuackColor.White.composeColor)
                            .padding(systemBarPaddings),
                        targetState = state.searchStep,
                    ) { step ->
                        when (step) {
                            SearchStep.Search -> SearchScreen(vm = vm)
                            SearchStep.SearchResult -> SearchResultScreen {
                                finishWithAnimation()
                            }
                        }
                    }

                    if (state.isSearchLoading) {
                        DuckieCircularProgressIndicator()
                    }
                }
            }
        }
    }

    private fun handleSideEffect(sideEffect: SearchSideEffect) {
        when (sideEffect) {
            is SearchSideEffect.ReportError -> {
                Firebase.crashlytics.recordException(sideEffect.exception)
            }
        }
    }
}
