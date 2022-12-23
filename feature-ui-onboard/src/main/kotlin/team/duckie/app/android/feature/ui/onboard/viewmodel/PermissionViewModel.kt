/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel

import kotlinx.coroutines.flow.StateFlow
import team.duckie.app.android.feature.ui.onboard.util.PermissionCompat

internal interface PermissionViewModel {
    /**
     * SDK 버전에 따라 사용할 이미지 저장소 권한을 나타냅니다.
     *
     * @see PermissionCompat.getImageStoragePermission
     */
    val imagePermission: String

    /**
     * 카메라 권한이 허용되었는지 여부를 나타냅니다.
     */
    var isCameraPermissionGranted: Boolean

    /**
     * [imagePermission] 권한이 허용되었는지 여부를 스트림으로 나타냅니다.
     *
     * 값이 null 일 경우엔 권한이 요청되지 않음을 나타냅니다.
     */
    val imagePermissionGrantState: StateFlow<Boolean?>

    /**
     * [imagePermission] 권한이 허용되었는지 여부를 반환합니다.
     *
     * 값이 null 일 경우엔 권한이 요청되지 않음을 나타냅니다.
     *
     * @see PermissionCompat.getImageStoragePermission
     */
    val isImagePermissionGranted: Boolean?

    /**
     * [imagePermissionGrantState] 값을 업데이트합니다.
     *
     * 값이 null 일 경우엔 권한이 요청되지 않음을 나타냅니다.
     */
    fun updateImagePermissionGrantState(isGranted: Boolean?)
}
