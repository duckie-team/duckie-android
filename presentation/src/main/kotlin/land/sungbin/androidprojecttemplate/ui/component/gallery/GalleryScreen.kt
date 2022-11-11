package land.sungbin.androidprojecttemplate.ui.component.gallery

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.constants.UiConstant.GALLERY_COLUMN_COUNT
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeaderGridLayout
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import java.net.URI

@Composable
internal fun ImageGalleryScreen(
    images: List<URI>,
    selectedImages: List<URI>,
    viewModel: ImageGalleryViewModel,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            onClickLeadingIcon = {
                coroutineScope.launch { viewModel.onBackPressed() }
            },
            trailingText = stringResource(R.string.add),
            onClickTrailingText = {
                coroutineScope.launch { viewModel.onClickAddComplete() }
            }
        )
        QuackHeaderGridLayout(
            columns = GALLERY_COLUMN_COUNT,
            items = images.toPersistentList(),
            horizontalSpace = 1.5.dp,
            verticalSpace = 1.5.dp,
            contentPadding = PaddingValues(0.dp),
            itemContent = { index, item ->
                GalleryImageItem(
                    image = item,
                    onClick = { checked, image ->
                        viewModel.pick(checked, image)
                    },
                    isSelected = selectedImages.find { it == item } != null,
                )
            },
            header = {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .aspectRatio(1f)
                        .border(
                            width = 1.dp,
                            color = QuackColor.Gray4.composeColor
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    QuackImage(src = QuackIcon.Camera)
                }
            },
            onClickHeader = {
                coroutineScope.launch { viewModel.onClickCamera() }
            },
        )
    }
}

@Composable
private fun GalleryImageItem(
    isSelected: Boolean,
    image: URI,
    onClick: (Boolean, URI) -> Unit,
) {
    QuackSelectableImage(
        isSelected = isSelected,
        image = image,
        onClick = {
            onClick(!isSelected, image)
        }
    )
}
