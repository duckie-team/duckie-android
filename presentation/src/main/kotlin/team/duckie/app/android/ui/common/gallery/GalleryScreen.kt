package team.duckie.app.android.ui.common.gallery

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import team.duckie.app.R
import team.duckie.app.constants.UiConstant.GALLERY_COLUMN_COUNT
import team.duckie.app.shared.compose.extension.CoroutineScopeContent
import team.duckie.app.shared.compose.extension.launch
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackHeaderGridLayout
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun ImageGalleryScreen(
    images: List<String>,
    selectedImages: List<String>,
    viewModel: ImageGalleryViewModel,
)  = CoroutineScopeContent {

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            onClickLeadingIcon = {
                launch { viewModel.onBackPressed() }
            },
            trailingText = stringResource(R.string.add),
            onClickTrailingText = {
                launch { viewModel.onClickAddComplete() }
            }
        )
        QuackHeaderGridLayout(
            columns = GALLERY_COLUMN_COUNT,
            items = images.toPersistentList(),
            horizontalSpace = 1.5.dp,
            verticalSpace = 1.5.dp,
            contentPadding = PaddingValues(0.dp),
            itemContent = { _, item ->
                GalleryImageItem(
                    image = item,
                    onClick = { checked, image ->
                        viewModel.pick(checked, image)
                    },
                    isSelected = selectedImages.find { image: String ->
                        image == item
                    } != null,
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
                launch { viewModel.onClickCamera() }
            },
        )
    }
}

@Composable
private fun GalleryImageItem(
    isSelected: Boolean,
    image: String,
    onClick: (Boolean, String) -> Unit,
) {
    QuackSelectableImage(
        isSelected = isSelected,
        image = Uri.parse(image),
        onClick = {
            onClick(!isSelected, image)
        }
    )
}
