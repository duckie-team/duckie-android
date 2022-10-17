package land.sungbin.androidprojecttemplate.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {

    private const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045

    private const val CAMERA = Manifest.permission.CAMERA
    const val CAMERA_REQUEST = 0x1046

    fun requestReadExternalStorage(activity: Activity, onPermissionGranted: () -> Unit) {
        requestPermission(
            activity,
            READ_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE_REQUEST,
            onPermissionGranted
        )
    }

    fun requestCamera(activity: Activity, onPermissionGranted: () -> Unit) {
        requestPermission(activity, CAMERA, CAMERA_REQUEST, onPermissionGranted)
    }

    private fun requestPermission(
        activity: Activity,
        permission: String,
        permissionRequest: Int,
        onPermissionGranted: () -> Unit
    ) {
        if (!havePermission(activity, permission)) {
            val permissions = arrayOf(permission)
            ActivityCompat.requestPermissions(
                activity, permissions,
                permissionRequest
            )
        } else {
            onPermissionGranted()
        }
    }

    fun haveCameraPermission(activity: Activity) {
        havePermission(activity, CAMERA)
    }

    fun haveStoragePermission(activity: Activity) {
        havePermission(activity, READ_EXTERNAL_STORAGE)
    }

    private fun havePermission(activity: Activity, permission: String) =
        ContextCompat.checkSelfPermission(
            activity, permission
        ) == PackageManager.PERMISSION_GRANTED


}

