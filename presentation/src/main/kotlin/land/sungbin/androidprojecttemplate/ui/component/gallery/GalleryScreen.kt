package land.sungbin.androidprojecttemplate.ui.component.gallery

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.constants.UiConstant.GALLERY_COLUMN_COUNT
import team.duckie.quackquack.ui.component.QuackHeaderGridLayout
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun ImageGalleryScreen(
    activity: Activity,
    onClickCamera: () -> Unit,
    images: List<Uri>,
    viewModel: ImageGalleryViewModel,
) {
    val selectedImages = viewModel.selectedImages.observeAsState().value ?: listOf()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            onClickLeadingIcon = {
                activity.finish()
            },
            trailingText = stringResource(R.string.add),
            onClickTrailingText = {
                viewModel.onClickComplete()
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
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    QuackImage(src = QuackIcon.Camera)
                }
            },
            onClickHeader = onClickCamera,
        )
    }
}

@Composable
private fun GalleryImageItem(
    isSelected: Boolean,
    image: Uri,
    onClick: (Boolean, Uri) -> Unit,
) {
    QuackSelectableImage(
        isSelected = isSelected,
        image = image,
        onClick = {
            onClick(!isSelected, image)
        }
    )
}