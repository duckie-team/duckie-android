package land.sungbin.androidprojecttemplate.ui.component.gallery

import java.net.URI

data class ImageGalleryState(
    val selectType: ImageSelectType = ImageSelectType.SINGLE,
    val images: List<MediaStoreImage> = emptyList(),
    val selectedImages: List<URI> = emptyList(),
)

sealed class ImageGallerySideEffect {
    object LaunchCameraScreen: ImageGallerySideEffect()
    object FinishWithData: ImageGallerySideEffect()
    object Finish: ImageGallerySideEffect()
}
