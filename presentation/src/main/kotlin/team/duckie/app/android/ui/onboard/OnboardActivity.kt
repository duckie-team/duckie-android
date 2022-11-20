package team.duckie.app.android.ui.onboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.constants.ApplicationConstant
import team.duckie.app.constants.ApplicationConstant.IMAGE_DATA
import team.duckie.app.ui.DuckieTheme
import team.duckie.app.ui.common.gallery.ImageGalleryResponse
import team.duckie.app.ui.navigator.DuckieNavigator
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
            LaunchedEffect(Unit){
                viewModel.fetchCategories()
            }
            LaunchedEffect(viewModel.effect) {
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
                viewModel.setProfileImage(images[0])
            }
        }
    }
}
