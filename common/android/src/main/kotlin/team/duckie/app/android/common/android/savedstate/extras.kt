/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.savedstate

import androidx.lifecycle.SavedStateHandle
import team.duckie.app.android.common.kotlin.exception.DuckieClientLogicProblemException

fun <T : Any> SavedStateHandle.getOrThrow(
    key: String,
    errorCode: String = FAILED_IMPORT_EXTRAS,
) = get<T>(key) ?: throw DuckieClientLogicProblemException(code = errorCode)

private const val FAILED_IMPORT_EXTRAS = "failed_import_extra"
