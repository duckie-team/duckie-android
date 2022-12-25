/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import team.duckie.app.android.feature.ui.onboard.util.PermissionCompat
import team.duckie.app.android.feature.ui.onboard.viewmodel.PermissionViewModel

internal object PermissionViewModelInstance : PermissionViewModel {
    override val imagePermission = PermissionCompat.getImageStoragePermission()

    override var isCameraPermissionGranted = false

    private val mutableImagePermissionGrantState = MutableStateFlow<Boolean?>(null)
    override val imagePermissionGrantState = mutableImagePermissionGrantState.asStateFlow()

    override val isImagePermissionGranted get() = imagePermissionGrantState.value

    override fun updateImagePermissionGrantState(isGranted: Boolean?) {
        mutableImagePermissionGrantState.value = isGranted
    }
}
