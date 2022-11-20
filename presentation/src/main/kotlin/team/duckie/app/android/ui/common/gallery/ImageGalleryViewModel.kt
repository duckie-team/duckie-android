package team.duckie.app.android.ui.common.gallery

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import team.duckie.app.base.BaseViewModel
import team.duckie.app.constants.ApplicationConstant.IMAGE_MULTI_TYPE
import team.duckie.app.constants.ApplicationConstant.IMAGE_SINGLE_TYPE
import team.duckie.app.domain.model.MediaStoreImage
import team.duckie.app.domain.usecase.gallery.LoadImagesUseCase
import team.duckie.app.domain.usecase.gallery.ReleaseObserverUseCase
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


    fun pick(
        checked: Boolean,
        image: String,
    ) {
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

    private fun multiTypePick(
        checked: Boolean,
        image: String,
    ) {
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
