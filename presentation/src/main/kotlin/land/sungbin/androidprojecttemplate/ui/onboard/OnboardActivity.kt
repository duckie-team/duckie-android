package land.sungbin.androidprojecttemplate.ui.onboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_DATA
import land.sungbin.androidprojecttemplate.ui.DuckieTheme
import land.sungbin.androidprojecttemplate.ui.common.gallery.ImageGalleryResponse
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import javax.inject.Inject

@AndroidEntryPoint
class OnboardActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: DuckieNavigator

    @Inject
    lateinit var viewModel: OnboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContent {
            DuckieTheme {
                OnboardScreen(
                    viewModel = viewModel,
                )
            }
            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        OnboardSideEffect.NavigateToLogin -> {
                            navigator.navigateLoginScreen(this@OnboardActivity, true)
                        }

                        OnboardSideEffect.NavigateToGalley -> {
                            navigator.navigateGalleryScreen(
                                this@OnboardActivity,
                                ApplicationConstant.IMAGE_SINGLE_TYPE
                            )
                        }

                        OnboardSideEffect.NavigateToMain -> {
                            navigator.navigateMainScreen(this@OnboardActivity, true)
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val images =
                data?.getParcelableExtra<ImageGalleryResponse>(IMAGE_DATA)?.images ?: listOf()
            if (images.isNotEmpty()) {
                viewModel.setProfileImage(images[0].toString())
            }
        }
    }
}