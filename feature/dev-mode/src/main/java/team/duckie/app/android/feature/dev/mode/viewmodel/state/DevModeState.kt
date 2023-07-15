/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.dev.mode.viewmodel.state

open class DevModeState(open val isStage: Boolean) {
    data class InputPassword(
        val inputted: String = "",
        override val isStage: Boolean,
    ) : DevModeState(isStage)

    data class Success(
        val duckieApi: DuckieApi = DuckieApi.Real,
        override val isStage: Boolean,
    ) : DevModeState(isStage)
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
