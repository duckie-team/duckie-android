/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.dev.mode.viewmodel.state

open class DevModeState {
    data class InputPassword(
        val inputted: String = "",
    ) : DevModeState()


    data class Success(
        val duckieApi: DuckieApi = DuckieApi.Real,
    ) : DevModeState()
}

enum class DuckieApi {
    Stage, Real;

    val isStage
        get() = this == Stage
}

fun Boolean?.toDuckieApi() = when (this) {
    true -> DuckieApi.Stage
    false -> DuckieApi.Real
    else -> null
}
