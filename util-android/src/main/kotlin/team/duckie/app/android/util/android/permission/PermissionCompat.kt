/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.android.permission

import android.Manifest
import android.os.Build

object PermissionCompat {
    /**
     * 버전 분기에 맞게 Image Storage 권한을 반환합니다.
     *
     * - SDK 32 이상: [Manifest.permission.READ_MEDIA_IMAGES]
     * - SDK 32 미만: [Manifest.permission.READ_EXTERNAL_STORAGE]
     */
    fun getImageStoragePermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }
}
