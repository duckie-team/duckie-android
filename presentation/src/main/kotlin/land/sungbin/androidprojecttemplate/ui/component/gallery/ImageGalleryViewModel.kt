package land.sungbin.androidprojecttemplate.ui.component.gallery

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_MULTI_TYPE
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_SINGLE_TYPE
import land.sungbin.androidprojecttemplate.data.repository.gallery.GalleryRepository
import land.sungbin.androidprojecttemplate.util.Event
import javax.inject.Inject

@HiltViewModel
class ImageGalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val TAG = ImageGalleryViewModel::class.java.simpleName

    lateinit var selectType: ImageSelectType

    private val _onClickCompleteEvent = MutableLiveData<Event<Unit>>()
    val onClickCompleteEvent: LiveData<Event<Unit>> get() = _onClickCompleteEvent

    private val _images = MutableLiveData<List<MediaStoreImage>>()
    val images: LiveData<List<MediaStoreImage>> get() = _images

    private val _selectedImages = MutableLiveData<List<Uri>>()
    val selectedImages: LiveData<List<Uri>> get() = _selectedImages

    fun loadImages() {
        viewModelScope.launch {
            _images.value = galleryRepository.loadImages(
                onRegisterObserver = {
                    loadImages()
                }
            )
        }
    }

    fun init(selectType: Int) {
        this.selectType = ImageSelectType.getByType(selectType)
        loadImages()
    }


    fun pick(checked: Boolean, image: Uri) {
        when (selectType) {
            ImageSelectType.SINGLE -> {
                singleTypePick(checked, image)
            }

            ImageSelectType.MULTI -> {
                multiTypePick(checked, image)
            }
        }
    }

    private fun singleTypePick(checked: Boolean, image: Uri) {
        when (checked) {
            true -> _selectedImages.value = listOf(image)
            false -> _selectedImages.value = null
        }
    }

    private fun multiTypePick(checked: Boolean, image: Uri) {
        when (checked) {
            true -> _selectedImages.value = (_selectedImages.value ?: listOf()) + image
            else -> _selectedImages.value = (_selectedImages.value ?: listOf()) - image
        }
    }

    fun offerImage(image: Uri) {
        _images.value =
            listOf(MediaStoreImage(_images.value?.size?.toLong() ?: 0L, image)) + (_images.value
                ?: listOf())
    }

    fun onClickComplete() {
        _onClickCompleteEvent.value = Event(Unit)
    }

    override fun onCleared() {
        galleryRepository.releaseObserver()
    }
}


data class MediaStoreImage(
    val id: Long,
    val contentUri: Uri
)

enum class ImageSelectType(
    val type: Int
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
    val images: List<Uri>
) : Parcelable