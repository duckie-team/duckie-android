package land.sungbin.androidprojecttemplate.ui.component.gallery

import android.net.Uri

data class ImageGalleryState(
    val selectType: ImageSelectType = ImageSelectType.SINGLE,
    val images: List<MediaStoreImage> = emptyList(),
    val selectedImages: List<Uri> = emptyList(),
)

sealed class ImageGallerySideEffect {
    object LaunchCameraScreen: ImageGallerySideEffect()
    object FinishWithData: ImageGallerySideEffect()
    object Finish: ImageGallerySideEffect()
}
