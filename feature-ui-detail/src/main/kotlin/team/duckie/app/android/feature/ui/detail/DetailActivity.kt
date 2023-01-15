package team.duckie.app.android.feature.ui.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.detail.screen.DetailScreen
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.theme.QuackTheme

/** 상세 화면 */
@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuackTheme {
                DetailScreen(
                    Modifier
                        .fillMaxSize()
                        .background(color = QuackColor.White.composeColor)
                        .systemBarsPadding()
                )
            }
        }
    }
}
