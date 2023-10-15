/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import team.duckie.app.android.common.kotlin.AllowMagicNumber

@AllowMagicNumber("vibration seconds")
private fun vibrateDevice(context: Context) {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val vibrator: VibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrationEffect = VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_TICK)
            vibrator.vibrate(CombinedVibration.createParallel(vibrationEffect))
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationEffect = VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_TICK)
            vibrator.vibrate(vibrationEffect)
        }

        else -> {
            @Suppress("DEPRECATION")
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            @Suppress("DEPRECATION")
            vibrator.vibrate(100)
        }
    }
}

@Composable
fun VibrateOnTap(
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier, () -> Unit) -> Unit,
) {
    val context = LocalContext.current

    content(modifier) {
        vibrateDevice(context)
    }
}
