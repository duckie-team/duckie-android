package land.sungbin.androidprojecttemplate.data.repository.gallery

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.provider.MediaStore
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import land.sungbin.androidprojecttemplate.ui.component.gallery.MediaStoreImage
import java.net.URI
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
) : GalleryRepository {

    private var contentObserver: ContentObserver? = null

    override suspend fun loadImages(): List<MediaStoreImage> = queryImages()

    private suspend fun queryImages(): List<MediaStoreImage> {
        val images = mutableListOf<MediaStoreImage>()
        withContext(Dispatchers.IO) {

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED
            )

            val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
            val selectionArgs =
                arrayOf(dateToTimestamp(day = 22, month = 10, year = 2008).toString())
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            Contexts.getApplication(context).contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val uri = URI(contentUri.toString())
                    val image = MediaStoreImage(id, uri)
                    images += image

                    /**
                     * TODO Paging 처리 하기전에는 최근 100개의 사진만 불러옴
                     */
                    if (images.size > 100) break
                }
            }
        }
        return images
    }

    override fun releaseObserver() {
        contentObserver?.let {
            context.contentResolver.unregisterContentObserver(it)
        }
    }

    @Suppress("SameParameterValue")
    @SuppressLint("SimpleDateFormat")
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }

}