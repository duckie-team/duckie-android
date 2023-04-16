/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.presentation.viewmodel.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class IntroState(
    val isUpdateRequire: Boolean = false,
) : Parcelable
