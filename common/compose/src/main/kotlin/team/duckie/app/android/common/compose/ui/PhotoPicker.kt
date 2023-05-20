/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.compose.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import kotlinx.collections.immutable.ImmutableList
import team.duckie.app.android.common.compose.R
import team.duckie.app.android.common.kotlin.fastAny
import team.duckie.app.android.common.kotlin.runIf
import team.duckie.quackquack.ui.animation.QuackAnimatedVisibility
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackGridLayout
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.DpSize

object PhotoPickerConstants {
    /**
     * [PhotoPicker] 에서 이미지 대신에 카메라 미리보기를 표시하라고 지시합니다.
     */
    const val Camera = "CameraView"

    internal enum class ContentType {
        Camera, Image;
    }
}

/**
 * 주어진 이미지 정보를 가지고 PhotoPicker 를 구현합니다.
 * 이 컴포저블은 새로운 액티비티를 생성하는게 아닌, 단일 컴포저블로 작동됩니다.
 * 따라서 사용하는 컨테이너의 zIndex 정책을 따라야 하며, [PhotoPicker] 가 제일 높은
 * zIndex 를 보유해야 합니다. 가장 쉬운 제어 방법은 [QuackAnimatedVisibility] 를 이용하여
 * visibility 처리를 하는 것입니다. 이를 통해 visibility 의 state 만 관리하는 식으로 구현하면
 * 쉽고 효율적으로 [PhotoPicker] 를 처리할 수 있습니다.
 *
 * @param modifier 이 컴포넌트에 광역으로 적용될 [Modifier]. [Modifier.fillMaxSize] 가 권장됩니다.
 * @param zIndex 이 컴포넌트의 zIndex. 최상위의 zIndex 가 권장되며, 기본값은 1 입니다.
 * @param imageUris 표시할 이미지의 [android.net.Uri] 목록. call-site 에서 android 의존성 포함을 줄이기 위해
 * [Uri] 이 아닌 [String] 으로 받고, [PhotoPicker] 내부에서 [Uri] 로 변환하여 이미지를 표시합니다.
 * 예외적으로 [Uri] 이 아닌 [PhotoPickerConstants.Camera] 를 받을 수 있습니다. [PhotoPickerConstants.Camera] 를
 * 값으로 받으면 이미지를 표시하는게 아닌 카메라 미리보기를 표시합니다.
 * @param imageSelections 이미지 목록의 선택 상태를 나타냅니다.
 * @param onCameraClick [PhotoPickerConstants.Camera] 를 통해 표시된 카메라 미리보기가 클릭됐을 때 호출될 람다
 * @param onImageClick 이미지가 클릭됐을 때 호출될 람다. 클릭된 이미지의 [imageUris] 에 해당하는 인덱스와
 * [Uri] 를 파라미터로 받습니다.
 * @param onCloseClick 왼쪽 상단의 닫기 버튼이 클릭됐을 때 호출될 람다
 * @param onAddClick 오른쪽 상단의 추가 버튼이 클릭됐을 때 호출될 람다
 */
// TODO(sungbin): single/multi selection mode,
//                이미지 편집 바텀바,
//                선택한 이미지 미리보기 (full-screen preview),
//                사진 폴더별로 보기,
//                퍼포먼스 개선 -> RecyclerView 로 마이그레이션 긍정적으로 고려중
//                https://github.com/duckie-team/quack-quack-android/issues/439
@Composable
fun PhotoPicker(
    modifier: Modifier = Modifier,
    zIndex: Float = 2f,
    imageUris: ImmutableList<String>,
    imageSelections: List<Boolean>,
    onCameraClick: () -> Unit,
    onImageClick: (index: Int, uri: String) -> Unit,
    onCloseClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    // 컴포지션 이전에 동일 조건으로 최초 한 번만 assertion 진행
    @Suppress("RememberReturnType")
    remember(imageUris, imageSelections) {
        require(imageUris.size == imageSelections.size) {
            "imageUris.size (${imageUris.size}) != imageSelectionState.size (${imageSelections.size})"
        }
    }

    val cameraState = rememberCameraState()
    val camSelector by rememberCamSelector(CamSelector.Back)
    val sizedModifier = Modifier
        .aspectRatio(1f)
        .fillMaxSize()

    // 매번 리컴포지션마다 Collection#any 를 진행함
    // List 를 element 가 바뀌어도 동일 인스턴스라 remember 가 불가함
    // TODO(sungbin): 퍼포먼스 개선 방법을 찾아야 함
    val isAddable = imageSelections.fastAny { it }

    Column(modifier = modifier.zIndex(zIndex)) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            onLeadingIconClick = onCloseClick,
            centerText = stringResource(R.string.topappbar_filter_full),
            trailingContent = {
                QuackSubtitle(
                    modifier = Modifier
                        .then(Modifier) // prevent Modifier.Companion
                        .runIf(isAddable) {
                            quackClickable(
                                rippleEnabled = false,
                                onClick = onAddClick,
                            )
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 15.dp,
                        ),
                    text = stringResource(R.string.topappbar_add),
                    color = if (isAddable) QuackColor.Black else QuackColor.Gray2,
                    singleLine = true,
                )
            },
        )
        QuackGridLayout(
            modifier = Modifier.fillMaxSize(),
            key = { _, uri -> uri },
            items = imageUris,
            contentType = { _, uri ->
                if (uri == PhotoPickerConstants.Camera) {
                    PhotoPickerConstants.ContentType.Camera
                } else {
                    PhotoPickerConstants.ContentType.Image
                }
            },
        ) { index, uri ->
            if (uri == PhotoPickerConstants.Camera) {
                CameraPreview(
                    modifier = sizedModifier,
                    cameraState = cameraState,
                    camSelector = camSelector,
                ) {
                    Box(
                        modifier = sizedModifier.clickable(onClick = onCameraClick),
                        contentAlignment = Alignment.Center,
                    ) {
                        QuackImage(
                            src = QuackIcon.Camera,
                            size = DpSize(all = 36.dp),
                            tint = QuackColor.Gray2,
                        )
                    }
                }
            } else {
                QuackSelectableImage(
                    modifier = sizedModifier,
                    src = uri.toUri(),
                    isSelected = imageSelections[index],
                    contentScale = ContentScale.Crop,
                    onClick = { onImageClick(index, uri) },
                )
            }
        }
    }
}
