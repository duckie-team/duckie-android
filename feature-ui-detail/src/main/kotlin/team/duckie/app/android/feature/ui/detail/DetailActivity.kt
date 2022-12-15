package team.duckie.app.android.feature.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.util.compose.LocalViewModel
import team.duckie.app.android.util.ui.BaseActivity
import javax.inject.Inject

/** 상세 화면 */
@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalViewModel provides viewModel) {
                DetailScreen()
            }
        }
    }
}
