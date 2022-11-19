package land.sungbin.androidprojecttemplate.ui.common.gallery

import land.sungbin.androidprojecttemplate.domain.model.MediaStoreImage

data class ImageGalleryState(
    val selectType: ImageSelectType = ImageSelectType.SINGLE,
    val images: List<MediaStoreImage> = emptyList(),
    val selectedImages: List<String> = emptyList(),
)

sealed class ImageGallerySideEffect {
    object LaunchCameraScreen : ImageGallerySideEffect()
    object FinishWithData : ImageGallerySideEffect()
    object Finish : ImageGallerySideEffect()
}
