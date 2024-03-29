/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(ExperimentalQuackQuackApi::class)

package team.duckie.app.android.feature.exam.result.screen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.duckie.app.android.common.android.image.MediaUtil
import team.duckie.app.android.common.android.image.saveImageInGallery
import team.duckie.app.android.common.android.intent.goToMarket
import team.duckie.app.android.common.android.share.ShareUtil
import team.duckie.app.android.common.android.share.ShareUtil.INSTAGRAM_PACKAGE_NAME
import team.duckie.app.android.common.android.share.isAppInstalled
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.compose.rememberToast
import team.duckie.app.android.common.compose.ui.BackPressedTopAppBar
import team.duckie.app.android.common.compose.ui.QuackDivider
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v2.Download
import team.duckie.app.android.common.compose.ui.icon.v2.DuckieTextLogo
import team.duckie.app.android.common.compose.ui.icon.v2.Instagram
import team.duckie.app.android.common.compose.ui.quack.TempSmallOutlineButton
import team.duckie.app.android.common.compose.util.ComposeToBitmap
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.toHourMinuteSecond
import team.duckie.app.android.feature.exam.result.R
import team.duckie.app.android.feature.exam.result.viewmodel.ExamResultState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.ui.QuackIcon
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.span
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackHeadLine2
import team.duckie.quackquack.ui.sugar.QuackTitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

private val PaleOrange = Color(0xFFFFF8E5)
private const val DelayDeleteFile: Long = 3000L

@AllowMagicNumber("for instagram delete external save delay")
@Composable
internal fun ExamResultShareScreen(
    state: ExamResultState.Success,
    onPrevious: () -> Unit,
) {
    val context = LocalContext.current

    val imageLoader =
        ImageLoader.Builder(context).allowHardware(false) // Disallow hardware bitmaps.
            .build()
    val imagePainter = rememberAsyncImagePainter(
        model = state.thumbnailUrl,
        imageLoader = imageLoader,
    )

    val coroutineScope = rememberCoroutineScope()
    val rememberToast = rememberToast()

    val snapShot = ComposeToBitmap {
        ExamResultImage(
            state = state,
            painter = imagePainter,
            isWidthFixed = true,
        )
    }

    BackHandler {
        onPrevious()
    }

    Scaffold(
        topBar = {
            BackPressedTopAppBar(
                color = PaleOrange,
                onBackPressed = onPrevious,
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .background(Color.White)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp,
                    ),
            ) {
                TempSmallOutlineButton(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(id = R.string.exam_result_save_image),
                    icon = QuackIcon.Download,
                ) {
                    coroutineScope.launch {
                        val bitmap = snapShot.invoke()
                        saveImageInGallery(
                            bitmap = bitmap,
                            context = context,
                            folderName = context.getString(
                                R.string.exam_result_name,
                                state.mainTag,
                            ),
                        )
                    }
                    rememberToast.invoke(context.getString(R.string.exam_success_save_image_message))
                }
                Spacer(space = 8.dp)
                TempSmallOutlineButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.exam_result_instagram_share),
                    icon = QuackIcon.Instagram,
                ) {
                    coroutineScope.launch {
                        val bitmap = snapShot.invoke()
                        val file = MediaUtil.imageExternalSave(bitmap)
                        sharInstagramStory(
                            filePath = file,
                            context = context,
                            onFailed = {
                                rememberToast.invoke(context.getString(R.string.exam_share_failed))
                            },
                        )
                    }
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PaleOrange)
                .padding(padding)
                .padding(horizontal = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(space = 20.dp)
            ExamResultImage(state = state, painter = imagePainter)
        }
    }
}

private suspend fun sharInstagramStory(
    filePath: String,
    context: Context,
    onFailed: () -> Unit,
) {
    runCatching {
        val intent = ShareUtil.intentInstagramStory(filePath)
        context.startActivity(intent)
    }.onSuccess {
        delay(DelayDeleteFile) // for delete external save
        MediaUtil.deleteFile(filePath)
    }.onFailure { _ ->
        if (!context.isAppInstalled(INSTAGRAM_PACKAGE_NAME)) {
            (context as Activity).goToMarket(INSTAGRAM_PACKAGE_NAME)
        } else {
            onFailed()
        }
    }
}

@Composable
private fun ExamResultImage(
    modifier: Modifier = Modifier,
    state: ExamResultState.Success,
    painter: AsyncImagePainter,
    isWidthFixed: Boolean = false,
) {
    Column(
        modifier = modifier
            .composed {
                if (isWidthFixed) width(268.dp) else fillMaxWidth()
            }
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackHeadLine2(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .span(
                    texts = listOf(state.nickname),
                    style = SpanStyle(
                        color = QuackColor.DuckieOrange.value,
                        fontWeight = FontWeight.Bold,
                    ),
                ),
            text = stringResource(
                id = R.string.exam_result_image_title,
                state.nickname,
                state.mainTag,
            ),
        )
        Spacer(space = 16.dp)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(GetHeightRatioW328H240)
                .clip(RoundedCornerShape(size = 8.dp)),
            contentScale = ContentScale.FillBounds,
        )
        Spacer(space = 8.dp)
        QuackDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                TitleAndBody(
                    title = state.time.toHourMinuteSecond(),
                    body = stringResource(id = R.string.exam_result_total_time),
                )
            }
            Box(
                modifier = Modifier
                    .size(1.dp, 12.dp)
                    .background(color = QuackColor.Gray3.value),
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                TitleAndBody(
                    title = stringResource(
                        id = R.string.exam_result_correct_problem_unit,
                        state.correctProblemCount,
                    ),
                    body = stringResource(id = R.string.exam_result_score),
                )
            }
        }
        QuackDivider()
        Spacer(space = 12.dp)
        QuackText(
            text = stringResource(
                id = R.string.exam_result_rank,
                state.ranking,
            ),
            typography = QuackTypography(
                color = QuackColor.DuckieOrange,
                size = 26.sp,
                weight = FontWeight.Bold,
                letterSpacing = 0.sp,
                lineHeight = 36.sp,
            ),
        )
        Spacer(space = 4.dp)
        QuackBody1(
            text = stringResource(
                id = R.string.exam_share_result_percent,
                state.solveRank,
                state.percentileRank,
            ),
        )
        Spacer(space = 24.dp)
        QuackIcon(
            modifier = Modifier.size(48.dp, 16.dp),
            icon = QuackIcon.DuckieTextLogo,
        )
    }
}

@Composable
private fun TitleAndBody(
    title: String,
    body: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuackTitle2(text = title)
        QuackText(
            text = body,
            typography = QuackTypography.Body3.change(
                color = QuackColor.Gray1,
            ),
        )
    }
}
