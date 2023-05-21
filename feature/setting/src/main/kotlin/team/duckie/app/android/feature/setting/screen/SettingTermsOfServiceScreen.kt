/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.setting.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import team.duckie.app.android.common.compose.ui.DuckieCircularProgressIndicator

private const val TermsOfServiceLink = "https://rickkyok.tistory.com/9"

@Composable
fun SettingTermsOfServiceScreen() {
    val rememberWebViewState =
        rememberWebViewState(
            url = TermsOfServiceLink,
        )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        WebView(
            state = rememberWebViewState,
        )
        val loadingState = rememberWebViewState.loadingState
        if (loadingState is LoadingState.Loading) {
            DuckieCircularProgressIndicator()
        }
    }
}
