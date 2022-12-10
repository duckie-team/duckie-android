package team.duckie.app.android.feature.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.util.ui.BaseActivity

/** 상세 화면 */
@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DetailScreen()
        }
    }
}
