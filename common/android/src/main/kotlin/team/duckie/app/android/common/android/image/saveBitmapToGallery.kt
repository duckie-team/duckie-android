/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import team.duckie.app.android.common.kotlin.exception.duckieClientLogicProblemException

fun saveBitmapToGallery(
    context: Context,
    bitmap: Bitmap,
    imageName: String,
    imageQuality: Int = 100,
) {
    val imageCollection =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val imageUri: Uri? = context.contentResolver.insert(imageCollection, contentValues)

    context.contentResolver.openOutputStream(imageUri!!)?.use { outputStream ->
        if (!bitmap.compress(Bitmap.CompressFormat.PNG, imageQuality, outputStream)) {
            duckieClientLogicProblemException("Failed to save bitmap.")
        }
    }

    contentValues.clear()
    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)

    context.contentResolver.update(imageUri, contentValues, null, null)
}
