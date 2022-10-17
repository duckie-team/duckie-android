package land.sungbin.androidprojecttemplate.ui.splash

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import land.sungbin.androidprojecttemplate.R
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackImage

private const val FIRST_SPLASH_PAGE = 0
private const val SECOND_SPLASH_PAGE = 1
private const val SPLASH_DELAY = 1000L

@Composable
fun SplashScreen(
    onCheckSession: () -> Unit,
    viewModel: SplashViewModel = viewModel()
) {

    val currentPage = viewModel.currentPage.observeAsState().value ?: FIRST_SPLASH_PAGE
    LaunchedEffect(Unit) {
        delay(SPLASH_DELAY)
        viewModel.navigatePage(SECOND_SPLASH_PAGE)
        delay(SPLASH_DELAY)
        onCheckSession()
    }

    Crossfade(targetState = currentPage) { page ->
        when (page) {
            FIRST_SPLASH_PAGE -> FirstSplashScreen()
            SECOND_SPLASH_PAGE -> SecondSplashScreen()
        }
    }
}

@Composable
private fun FirstSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(QuackColor.DuckieOrange.composeColor),
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            src = R.drawable.duckie_splash_logo,
        )
    }
}

@Composable
private fun SecondSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 78.dp)
    ) {
        Column {
            QuackImage(
                src = R.drawable.quack_duckie_text_logo,
                overrideSize = DpSize(85.dp, 25.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.height(8.dp))
            QuackHeadLine1(
                text = stringResource(R.string.splash_introduction_message),
                singleLine = false,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = 32.dp,
                )
        ) {
            QuackImage(
                src = R.drawable.duckie_splash,
                overrideSize = DpSize(
                    172.dp,
                    254.dp,
                )
            )
        }
    }
}