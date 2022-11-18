package land.sungbin.androidprojecttemplate.ui.common.gallery

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import land.sungbin.androidprojecttemplate.base.BaseViewModel
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_MULTI_TYPE
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_SINGLE_TYPE
import land.sungbin.androidprojecttemplate.domain.model.MediaStoreImage
import land.sungbin.androidprojecttemplate.domain.usecase.gallery.LoadImagesUseCase
import land.sungbin.androidprojecttemplate.domain.usecase.gallery.ReleaseObserverUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageGalleryViewModel @Inject constructor(
    private val loadImagesUseCase: LoadImagesUseCase,
    private val releaseObserverUseCase: ReleaseObserverUseCase,
) : BaseViewModel<ImageGalleryState, ImageGallerySideEffect>(ImageGalleryState()) {

    suspend fun loadImages() {
        loadImagesUseCase().onSuccess { loadedImages: List<MediaStoreImage> ->
            updateState {
                copy(images = loadedImages)
            }
        }.onFailure {

        }
    }

    suspend fun init(selectType: Int) {
        updateState {
            copy(selectType = ImageSelectType.getByType(selectType))
        }
        loadImages()
    }


    fun pick(checked: Boolean, image: String) {
        when (currentState.selectType) {
            ImageSelectType.SINGLE -> {
                singleTypePick(image)
            }

            ImageSelectType.MULTI -> {
                multiTypePick(checked, image)
            }
        }
    }

    private fun singleTypePick(image: String) {
        updateState {
            copy(
                selectedImages = listOf(image)
            )
        }

    }

    private fun multiTypePick(checked: Boolean, image: String) {
        updateState {
            copy(
                selectedImages = when (checked) {
                    true -> selectedImages + image
                    false -> selectedImages - image
                }
            )
        }
    }

    fun offerImage(image: String) {
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
        releaseObserverUseCase().onSuccess {
            Log.d("dispose 성공", "")
        }
    }
}

enum class ImageSelectType(
    val type: Int,
) {
    SINGLE(IMAGE_SINGLE_TYPE), MULTI(IMAGE_MULTI_TYPE);

    companion object {
        fun getByType(value: Int): ImageSelectType {
            return values().find { selectType: ImageSelectType ->
                selectType.type == value
            } ?: SINGLE
        }
    }
}

@Parcelize
data class ImageGalleryResponse(
    val images: List<String>,
) : Parcelable