package team.duckie.app.android.ui.common.gallery

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import team.duckie.app.constants.ApplicationConstant.GALLERY_IMAGE_TYPE
import team.duckie.app.constants.ApplicationConstant.IMAGE_DATA
import team.duckie.app.constants.ApplicationConstant.IMAGE_SINGLE_TYPE
import team.duckie.app.domain.model.MediaStoreImage
import team.duckie.app.util.ImageUtil
import team.duckie.app.util.PermissionUtil
import team.duckie.app.util.PermissionUtil.CAMERA_REQUEST
import team.duckie.app.util.PermissionUtil.READ_EXTERNAL_STORAGE_REQUEST
import javax.inject.Inject

@AndroidEntryPoint
class ImageGalleryActivity : ComponentActivity() {

    private val cameraGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val image = it?.data?.extras?.get("data") as Bitmap
                val path = ImageUtil.saveGalleryImage(this, image).toString()
                viewModel.offerImage(path)
            }
        }

    @Inject
    lateinit var viewModel: ImageGalleryViewModel

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            ImageGalleryScreen(
                images = state.images.map { mediaStoreImage: MediaStoreImage ->
                    mediaStoreImage.contentUri
                },
                selectedImages = state.selectedImages,
                viewModel = viewModel,
            )

            LaunchedEffect(key1 = viewModel.effect) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        ImageGallerySideEffect.FinishWithData -> {
                            setResult(
                                RESULT_OK,
                                intent.putExtra(
                                    IMAGE_DATA,
                                    ImageGalleryResponse(state.selectedImages),
                                )
                            )
                            finish()
                        }

                        ImageGallerySideEffect.LaunchCameraScreen -> {
                            PermissionUtil.requestCamera(this@ImageGalleryActivity) {
                                launchCameraScreen()
                            }
                        }

                        ImageGallerySideEffect.Finish -> {
                            finish()
                        }
                    }
                }
            }
            DisposableEffect(key1 = Unit) {
                onDispose {
                    viewModel.releaseObserver()
                }
            }
        }

        init()
        openMediaStore()
    }

    private fun init() {
        lifecycleScope.launch {
            viewModel.init(
                selectType = intent.getIntExtra(GALLERY_IMAGE_TYPE, IMAGE_SINGLE_TYPE)
            )
        }

    }

    private fun openMediaStore() {
        PermissionUtil.requestReadExternalStorage(this) {
            lifecycleScope.launch {
                viewModel.loadImages()
            }
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
        grantResults: IntArray,
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    lifecycleScope.launch {
                        viewModel.loadImages()
                    }
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

