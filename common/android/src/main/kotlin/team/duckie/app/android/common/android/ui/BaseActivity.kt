/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import land.sungbin.systemuicontroller.SystemUiController

/**
 * 모든 액티비티의 기본이 되는 Base Activity 를 구현합니다.
 * [BaseActivity] 는 크게 다음과 같은 일을 진행합니다.
 *
 * 1. WindowInsets 의 consume 비활성화
 * 2. SystemBars 의 색상을 transparent 로 변경
 *
 * @param useDarkStatusBarIcons 상태바에 다크모드 아이콘을 사용할지 여부
 * @param useDarkNavigationBarIcons 네비게이션바에 다크모드 아이콘을 사용할지 여부
 */
//  The base class, 'team.duckie.app.android.util.ui.BaseActivity' contains a constructor with default parameters.
//  This is currently not supported by the Gradle plugin.
open class BaseActivity(
    private val useDarkStatusBarIcons: Boolean,
    private val useDarkNavigationBarIcons: Boolean,
) : ComponentActivity() {

    constructor() : this(useDarkStatusBarIcons = true, useDarkNavigationBarIcons = true)

    private val systemUiController by lazy {
        SystemUiController(window)
    }
    private val whiteColor by lazy {
        ContextCompat.getColor(this, android.R.color.white)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        systemUiController.setStatusBarColor(
            color = whiteColor,
            darkIcons = useDarkStatusBarIcons,
        )
        systemUiController.setNavigationBarColor(
            color = whiteColor,
            darkIcons = useDarkNavigationBarIcons,
        )
        super.onCreate(savedInstanceState)
        actionBar?.hide()
    }
}
