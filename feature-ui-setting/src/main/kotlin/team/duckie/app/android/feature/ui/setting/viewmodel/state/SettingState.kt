/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.setting.viewmodel.state

import team.duckie.app.android.domain.user.model.User

data class SettingState(
    val me: User? = null,
)
