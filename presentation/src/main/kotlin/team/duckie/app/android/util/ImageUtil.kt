package team.duckie.app.android.util

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.net.URI

object ImageUtil {

    fun saveGalleryImage(context: Context, image: Bitmap): URI {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            image,
            "ImageFromDuckie",
            null
        )
        checkNotNull(path)
        return URI.create(path)
    }
}
