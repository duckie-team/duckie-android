package team.duckie.app.android.ui.navigator

import android.app.Activity
import android.content.Intent
import team.duckie.app.constants.ApplicationConstant
import team.duckie.app.constants.ApplicationConstant.GALLERY_SCREEN_REQUEST
import team.duckie.app.ui.common.gallery.ImageGalleryActivity
import team.duckie.app.ui.component.startActivityForResultWithAnimation
import team.duckie.app.ui.component.startActivityWithAnimation
import team.duckie.app.ui.login.LoginActivity
import team.duckie.app.ui.main.MainActivity
import team.duckie.app.ui.onboard.OnboardActivity
import javax.inject.Inject

class DuckieNavigatorImpl @Inject constructor() : DuckieNavigator {

    override fun navigateGalleryScreen(
        activity: Activity,
        imageSelectType: Int,
        withFinish: Boolean
    ) {
        activity.startActivityForResultWithAnimation(
            requestCode = GALLERY_SCREEN_REQUEST,
            withFinish = withFinish,
            intentBuilder = {
                Intent(activity, ImageGalleryActivity::class.java).apply {
                    putExtra(ApplicationConstant.GALLERY_IMAGE_TYPE, imageSelectType)
                }
            }
        )
    }

    override fun navigateMainScreen(activity: Activity, withFinish: Boolean) {
        activity.startActivityWithAnimation(
            withFinish = withFinish,
        ) {
            Intent(activity, MainActivity::class.java)
        }
    }

    override fun navigateOnboardScreen(activity: Activity, withFinish: Boolean) {
        activity.startActivityWithAnimation(
            withFinish = withFinish,
        ) {
            Intent(activity, OnboardActivity::class.java)
        }
    }

    override fun navigateLoginScreen(activity: Activity, withFinish: Boolean) {
        activity.startActivityWithAnimation(
            withFinish = withFinish,
        ) {
            Intent(activity, LoginActivity::class.java)
        }
    }
}
