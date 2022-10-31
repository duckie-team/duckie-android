package land.sungbin.androidprojecttemplate.ui.onboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_DATA
import land.sungbin.androidprojecttemplate.ui.DuckieTheme
import land.sungbin.androidprojecttemplate.ui.component.gallery.ImageGalleryResponse
import land.sungbin.androidprojecttemplate.ui.navigator.DuckieNavigator
import land.sungbin.androidprojecttemplate.util.EventObserver
import javax.inject.Inject

@AndroidEntryPoint
class OnboardActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: DuckieNavigator

    private val viewModel: OnboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeViewModel()
    }

    private fun initView() {
        setContent {
            DuckieTheme {
                OnboardScreen(
                    activity = this,
                    viewModel = viewModel,
                    navigator = navigator,
                )
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            onClickComplete.observe(this@OnboardActivity, EventObserver {
                navigator.navigateMainScreen(this@OnboardActivity, true)
            })
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