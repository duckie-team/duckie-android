package land.sungbin.androidprojecttemplate.shared.compose.extension

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import land.sungbin.androidprojecttemplate.shared.compose.optin.LocalActivityUsageApi

@LocalActivityUsageApi
val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    noLocalProvidedFor("LocalActivity")
}

@Suppress("SameParameterValue")
private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
