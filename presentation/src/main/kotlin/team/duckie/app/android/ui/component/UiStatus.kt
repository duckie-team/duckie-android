package team.duckie.app.android.ui.component

sealed class UiStatus {
    object Loading : UiStatus()
    object Success : UiStatus()
    data class Failed(val message: String = "") : UiStatus()
}
