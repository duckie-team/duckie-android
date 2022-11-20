package team.duckie.app.android.ui.component

import android.app.Activity
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FadeAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn(),
    exit: ExitTransition = fadeOut(),
    label: String = "AnimatedVisibility",
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible,
        modifier,
        enter,
        exit,
        label,
        content
    )
}

inline fun Activity.startActivityWithAnimation(
    withFinish: Boolean = false,
    intentBuilder: () -> Intent,
) {
    startActivity(intentBuilder())
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (withFinish) {
        finish()
    }
}

inline fun Activity.startActivityForResultWithAnimation(
    withFinish: Boolean = false,
    requestCode: Int,
    intentBuilder: () -> Intent,
) {
    startActivityForResult(intentBuilder(), requestCode)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (withFinish) {
        finish()
    }
}
