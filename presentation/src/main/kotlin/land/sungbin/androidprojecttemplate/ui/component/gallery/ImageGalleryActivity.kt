package land.sungbin.androidprojecttemplate.ui.component.gallery

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import dagger.hilt.android.AndroidEntryPoint
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.GALLERY_IMAGE_TYPE
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_DATA
import land.sungbin.androidprojecttemplate.constants.ApplicationConstant.IMAGE_SINGLE_TYPE
import land.sungbin.androidprojecttemplate.util.EventObserver
import land.sungbin.androidprojecttemplate.util.ImageUtil
import land.sungbin.androidprojecttemplate.util.PermissionUtil
import land.sungbin.androidprojecttemplate.util.PermissionUtil.CAMERA_REQUEST
import land.sungbin.androidprojecttemplate.util.PermissionUtil.READ_EXTERNAL_STORAGE_REQUEST


@AndroidEntryPoint
class ImageGalleryActivity : ComponentActivity() {

    private val cameraGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val image = it?.data?.extras?.get("data") as Bitmap
                val path = ImageUtil.saveGalleryImage(this, image)
                viewModel.offerImage(path)
            }
        }

    private val viewModel: ImageGalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ImageGalleryScreen(
                activity = this,
                onClickCamera = {
                    PermissionUtil.requestCamera(this) {
                        launchCameraScreen()
                    }
                },
                images = viewModel.images.observeAsState().value?.map { it.contentUri } ?: listOf(),
                viewModel = viewModel,
            )
        }

        init()
        openMediaStore()
        observeViewModel()
    }

    private fun init() {
        viewModel.init(
            selectType = intent.getIntExtra(GALLERY_IMAGE_TYPE, IMAGE_SINGLE_TYPE)
        )
    }

    private fun openMediaStore() {
        PermissionUtil.requestReadExternalStorage(this) {
            viewModel.loadImages()
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            onClickCompleteEvent.observe(this@ImageGalleryActivity, EventObserver {
                setResult(
                    RESULT_OK,
                    intent.putExtra(
                        IMAGE_DATA,
                        ImageGalleryResponse(selectedImages.value ?: listOf())
                    )
                )
                finish()
            })
        }
    }

    private fun launchCameraScreen() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            cameraGalleryLauncher.launch(takePictureIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    viewModel.loadImages()
                } else {
                    finish()
                }
            }

            CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    launchCameraScreen()
                }
            }
        }
    }


}

