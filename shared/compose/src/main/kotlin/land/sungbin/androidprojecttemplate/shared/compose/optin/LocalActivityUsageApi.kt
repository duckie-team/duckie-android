package land.sungbin.androidprojecttemplate.shared.compose.optin

@RequiresOptIn(message = "This API uses LocalActivity. Check if the Activity is being present.")
@Retention(AnnotationRetention.BINARY)
annotation class LocalActivityUsageApi
