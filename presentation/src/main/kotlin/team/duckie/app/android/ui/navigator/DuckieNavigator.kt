package team.duckie.app.android.ui.navigator

import android.app.Activity

interface DuckieNavigator {

    fun navigateGalleryScreen(activity: Activity, imageSelectType: Int, withFinish: Boolean = false)

    fun navigateMainScreen(activity: Activity, withFinish: Boolean = false)

    fun navigateOnboardScreen(activity: Activity, withFinish: Boolean = false)

    fun navigateLoginScreen(activity: Activity, withFinish: Boolean = false)
}
