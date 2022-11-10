package land.sungbin.androidprojecttemplate.ui.component.gallery

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_MULTI_TYPE
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_SINGLE_TYPE
import land.sungbin.androidprojecttemplate.data.repository.gallery.GalleryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageGalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
) : BaseViewModel<ImageGalleryState, ImageGallerySideEffect>(ImageGalleryState()) {

    private val TAG = ImageGalleryViewModel::class.java.simpleName

    suspend fun loadImages() {
        val loadedImages = galleryRepository.loadImages()
        updateState {
            copy(images = loadedImages)
        }
    }

    suspend fun init(selectType: Int) {
        updateState {
            copy(selectType = ImageSelectType.getByType(selectType))
        }
        loadImages()
    }


    fun pick(checked: Boolean, image: Uri) {
        when (currentState.selectType) {
            ImageSelectType.SINGLE -> {
                singleTypePick(image)
            }

            ImageSelectType.MULTI -> {
                multiTypePick(checked, image)
            }
        }
    }

    private fun singleTypePick(image: Uri) {
        updateState {
            copy(
                selectedImages = listOf(image)
            )
        }

    }

    private fun multiTypePick(checked: Boolean, image: Uri) {
        updateState {
            copy(
                selectedImages = when (checked) {
                    true -> selectedImages + image
                    false -> selectedImages - image
                }
            )
        }
    }

    fun offerImage(image: Uri) {
        updateState {
            copy(
                images = listOf(MediaStoreImage(images.size.toLong(), image)) + images
            )
        }
    }

    suspend fun onClickAddComplete() = postSideEffect {
        ImageGallerySideEffect.FinishWithData
    }

    suspend fun onClickCamera() = postSideEffect {
        ImageGallerySideEffect.LaunchCameraScreen
    }

    suspend fun onBackPressed() = postSideEffect {
        ImageGallerySideEffect.Finish
    }

    fun releaseObserver() {
        galleryRepository.releaseObserver()
    }
}


data class MediaStoreImage(
    val id: Long,
    val contentUri: Uri,
)

enum class ImageSelectType(
    val type: Int,
) {
    SINGLE(IMAGE_SINGLE_TYPE), MULTI(IMAGE_MULTI_TYPE);

    companion object {
        fun getByType(value: Int): ImageSelectType {
            return values().find { it.type == value } ?: SINGLE
        }
    }
}

@Parcelize
data class ImageGalleryResponse(
    val images: List<Uri>,
) : Parcelable