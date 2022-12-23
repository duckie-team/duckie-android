/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import team.duckie.app.android.util.viewmodel.BaseViewModel
import team.duckie.quackquack.ui.theme.QuackTheme

/**
 * 액티비티에 setContent 를 진행하는 보일러플레이트 함수를 정의합니다.
 * 이 함수는 3가지 작업을 진행합니다.
 *
 * 1. [ComponentActivity] 에 [ComponentActivity.setContent]
 * 2. [QuackTheme] 지정
 * 3. [LocalViewModel] 로 [viewmodel] 제공
 *
 * @param viewmodel 현재 액티비티에서 사용할 뷰모델.
 * null 이 입력될 시 [LocalViewModel] 을 제공하지 않습니다.
 * @param content 현재 액티비티의 컴포저블
 */
fun ComponentActivity.setDuckieContent(
    viewmodel: BaseViewModel<*, *>? = null,
    content: @Composable () -> Unit,
) {
    setContent {
        QuackTheme {
            if (viewmodel != null) {
                CompositionLocalProvider(
                    LocalViewModel provides viewmodel,
                    content = content,
                )
            } else {
                content()
            }
        }
    }
}
