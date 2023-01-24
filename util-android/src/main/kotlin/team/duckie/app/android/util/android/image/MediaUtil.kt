/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.android.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * Media 처리용 유틸 클래스
 * // TODO(riflockle7): 추후 ImageUtil 로 바꿀지 고민
 */
object MediaUtil {
    private const val maxWidth = 1600
    private const val maxHeight = 1600

    /**
     * 업로드할 이미지를 가져온다.
     *
     * @param uri 사이즈를 조정할 파일의 uri
     * @param maxSizeLimitEnable 1600 제한을 걸어둘 것인지 여부
     *
     * @return 조정된 bitmap 파일
     */
    fun getOptimizedBitmapFile(
        applicationContext: Context,
        uri: Uri,
        maxSizeLimitEnable: Boolean = false,
    ): File {
        try {
            // 임시 파일 경로 및 이름
            val storage = applicationContext.cacheDir
            val fileName = String.format("%s.%s", UUID.randomUUID(), "jpg")

            // 임시 파일 생성
            val tempFile = File(storage, fileName)
            tempFile.createNewFile()

            // 파일 출력 스트림 생성
            val fos = FileOutputStream(tempFile)

            decodeBitmapFromUri(uri, applicationContext, maxSizeLimitEnable)?.apply {
                compress(Bitmap.CompressFormat.JPEG, 100, fos)
                recycle()
            } ?: throw NullPointerException()

            fos.flush()
            fos.close()

            return tempFile
        } catch (throwable: Throwable) {
            println("FileUtil - ${throwable.message}")
            throw throwable
        }
    }

    /** 최적화시킨 bitmap 을 가져온다 */
    private fun decodeBitmapFromUri(
        uri: Uri,
        context: Context,
        maxSizeLimitEnable: Boolean,
    ): Bitmap? {
        // 인자 값으로 넘어온 입력 스트림을 나중에 사용하기 위해 저장하는 BufferedInputStream 사용
        val input = BufferedInputStream(context.contentResolver.openInputStream(uri))
        input.mark(input.available()) // 입력 스트림의 특정 위치를 기억
        var bitmap: Bitmap?

        BitmapFactory.Options().run {
            if (maxSizeLimitEnable) {
                // inJustDecodeBounds를 true 로 설정한 상태에서 디코딩한 다음 옵션을 전달
                inJustDecodeBounds = true
                bitmap = BitmapFactory.decodeStream(input, null, this)

                // 입력 스트림의 마지막 mark 된 위치로 재설정
                input.reset()

                // inSampleSize 값과 false 로 설정한 inJustDecodeBounds 를 사용하여 다시 디코딩
                inSampleSize = calculateInSampleSize(this)
                inJustDecodeBounds = false
            }

            bitmap = BitmapFactory.decodeStream(input, null, this)
                ?.apply { rotateImageIfRequired(context, this, uri) }
        }
        input.close()

        return bitmap
    }

    /** 리샘플링 값 계산: 타겟 너비와 높이를 기준으로 2의 거듭제곱인 샘플 크기 값을 계산한다. */
    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > maxHeight || width > maxWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= maxHeight && halfWidth / inSampleSize >= maxWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    /**
     * 회전된 bitmap 이 있다면 본래의 상태로 돌려준다.
     * (가로로 찍힌 이미지인 경우 높은 확률로 발생)
     */
    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(uri) ?: return null

        val exif = if (Build.VERSION.SDK_INT > 23) {
            ExifInterface(input)
        } else {
            ExifInterface(uri.path!!)
        }

        return when (exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap
        }
    }

    /** 이미지를 회전한다. */
    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
