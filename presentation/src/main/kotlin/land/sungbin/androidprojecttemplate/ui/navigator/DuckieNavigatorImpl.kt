package land.sungbin.androidprojecttemplate.ui.navigator

import android.app.Activity
import android.content.Intent
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.GALLERY_SCREEN_REQUEST
import land.sungbin.androidprojecttemplate.ui.common.gallery.ImageGalleryActivity
import land.sungbin.androidprojecttemplate.ui.component.startActivityForResultWithAnimation
import land.sungbin.androidprojecttemplate.ui.component.startActivityWithAnimation
import land.sungbin.androidprojecttemplate.ui.login.LoginActivity
import land.sungbin.androidprojecttemplate.ui.main.MainActivity
import land.sungbin.androidprojecttemplate.ui.onboard.OnboardActivity
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