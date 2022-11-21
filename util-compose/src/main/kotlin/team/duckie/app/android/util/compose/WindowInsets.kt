/*
 * Designed and developed by 2022 Ji Sungbin.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/ApiLibrary/blob/trunk/LICENSE
 */

package team.duckie.app.android.util.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable

/**
 * SystemBars 에 해당하는 WindowInsets 을 [PaddingValues] 로 가져옵니다.
 */
val systemBarPaddings
    @Composable get() = WindowInsets.systemBars.asPaddingValues()
