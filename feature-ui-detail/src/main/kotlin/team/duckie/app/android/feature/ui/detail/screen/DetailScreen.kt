/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:AllowMagicNumber
@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.app.android.feature.ui.detail.screen

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.detail.R
import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
import team.duckie.app.android.shared.ui.compose.DuckieBottomSheetDialog
import team.duckie.app.android.shared.ui.compose.ErrorScreen
import team.duckie.app.android.shared.ui.compose.LoadingScreen
import team.duckie.app.android.util.android.network.NetworkUtil
import team.duckie.app.android.util.compose.GetHeightRatioW328H240
import team.duckie.app.android.util.compose.activityViewModel
import team.duckie.app.android.util.compose.asLoose
import team.duckie.app.android.util.compose.rememberToast
import team.duckie.app.android.util.kotlin.AllowMagicNumber
import team.duckie.app.android.util.kotlin.fastFirstOrNull
import team.duckie.app.android.util.kotlin.npe
import team.duckie.app.android.util.kotlin.percents
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.shape.SquircleShape
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

private const val DetailScreenTopAppBarLayoutId = "DetailScreenTopAppBar"
private const val DetailScreenContentLayoutId = "DetailScreenContent"
private const val DetailScreenBottomBarLayoutId = "DetailScreenBottomBar"

/** 상세 화면 Screen */
@Composable
internal fun DetailScreen(
    modifier: Modifier,
    viewModel: DetailViewModel = activityViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    var isNetworkAvailable: Boolean by remember { mutableStateOf(false) }
    isNetworkAvailable = !NetworkUtil.isNetworkAvailable(context)
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    return when {
        isNetworkAvailable -> ErrorScreen(
            modifier,
            true,
            onRetryClick = viewModel::refresh,
        )

        state is DetailState.Loading -> LoadingScreen(
            viewModel::initState,
            modifier,
        )

        state is DetailState.Success -> {
            DetailBottomSheetDialog(
                bottomSheetState = bottomSheetState,
                closeSheet = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onReport = viewModel::report,
            ) {
                DetailSuccessScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                    openBottomSheet = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    state = state,
                )
            }
        }

        else -> ErrorScreen(
            modifier,
            false,
            onRetryClick = viewModel::initState,
        )
    }
}

/**
 * [DetailBottomSheetDialog] 에서 표시할 요소들을 정의합니다.
 */
private data class DetailBottomSheetItem(
    @DrawableRes
    val icon: Int,
    @StringRes
    val text: Int,
    val onClick: () -> Unit,
)

/** 더보기 클릭 시 사용되는 BottomSheetDialog */
@Composable
private fun DetailBottomSheetDialog(
    bottomSheetState: ModalBottomSheetState,
    closeSheet: () -> Unit,
    onReport: () -> Unit,
    content: @Composable () -> Unit,
) {
    val rememberBottomSheetItems = remember {
        persistentListOf(
            DetailBottomSheetItem(
                icon = R.drawable.ic_report,
                text = R.string.report,
                onClick = onReport,
            ),
        )
    }

    DuckieBottomSheetDialog(
        useHandle = true,
        sheetState = bottomSheetState,
        sheetContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp),
            ) {
                items(rememberBottomSheetItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .quackClickable(
                                rippleEnabled = false,
                            ) {
                                item.onClick
                                closeSheet()
                            }
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        QuackImage(
                            src = item.icon,
                            size = DpSize(width = 24.dp, height = 24.dp),
                        )
                        QuackSubtitle2(
                            modifier = Modifier.padding(start = 8.dp),
                            text = stringResource(id = item.text),
                        )
                    }
                }
            }
        },
    ) {
        content()
    }
}

/** 데이터 성공적으로 받은[DetailState.Success] 상세 화면 */
@Composable
private fun DetailSuccessScreen(
    viewModel: DetailViewModel,
    modifier: Modifier,
    openBottomSheet: () -> Unit,
    state: DetailState.Success,
) {
    Layout(
        modifier = modifier.navigationBarsPadding(),
        content = {
            // 상단 탭바 Layout
            TopAppCustomBar(
                modifier = Modifier.layoutId(DetailScreenTopAppBarLayoutId),
                state = state,
            )
            // content Layout
            DetailContentLayout(
                state = state,
                tagItemClick = viewModel::goToSearch,
                moreButtonClick = openBottomSheet,
            ) {
                viewModel.followUser()
            }
            // 최하단 Layout
            DetailBottomLayout(
                modifier = Modifier
                    .layoutId(DetailScreenBottomBarLayoutId)
                    .background(color = QuackColor.White.composeColor),
                state,
                onHeartClick = viewModel::heartExam,
                onChallengeClick = viewModel::startExam,
            )
        },
    ) { measurableItems, constraints ->
        // 1. topAppBar, bottomBar 높이값 측정
        val looseConstraints = constraints.asLoose()

        val topAppBarMeasurable = measurableItems.fastFirstOrNull { measureItem ->
            measureItem.layoutId == DetailScreenTopAppBarLayoutId
        }?.measure(looseConstraints) ?: npe()
        val topAppBarHeight = topAppBarMeasurable.height

        val bottomBarMeasurable = measurableItems.fastFirstOrNull { measurable ->
            measurable.layoutId == DetailScreenBottomBarLayoutId
        }?.measure(looseConstraints) ?: npe()
        val bottomBarHeight = bottomBarMeasurable.height

        // 2. content 제약 설정 및 content 높이값 측정
        val contentHeight = constraints.maxHeight - topAppBarHeight - bottomBarHeight
        val contentConstraints = constraints.copy(
            minHeight = contentHeight,
            maxHeight = contentHeight,
        )
        val contentMeasurable = measurableItems.fastFirstOrNull { measurable ->
            measurable.layoutId == DetailScreenContentLayoutId
        }?.measure(contentConstraints) ?: npe()

        // 3. 위에서 추출한 값들을 활용해 레이아웃 위치 처리
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            topAppBarMeasurable.place(
                x = 0,
                y = 0,
            )
            contentMeasurable.place(
                x = 0,
                y = topAppBarHeight,
            )
            bottomBarMeasurable.place(
                x = 0,
                y = topAppBarHeight + contentHeight,
            )
        }
    }
}

/** 상세 화면 컨텐츠 Layout */
@Suppress("MagicNumber")
@Composable
private fun DetailContentLayout(
    state: DetailState.Success,
    tagItemClick: (String) -> Unit,
    moreButtonClick: () -> Unit,
    followButtonClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val detailImageWidthDp = screenWidthDp - 32.dp

    Column(
        modifier = Modifier
            .layoutId(DetailScreenContentLayoutId)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        // 그림
        QuackImage(
            size = DpSize(
                detailImageWidthDp,
                detailImageWidthDp * GetHeightRatioW328H240,
            ),
            padding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            shape = RoundedCornerShape(size = 8.dp),
            contentScale = ContentScale.FillWidth,
            src = state.exam.thumbnailUrl,
        )
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // 제목
            QuackHeadLine2(text = state.exam.title)
            // 더보기 아이콘
            QuackImage(
                src = QuackIcon.More,
                size = DpSize(width = 24.dp, height = 24.dp),
                onClick = moreButtonClick,
            )
        }

        // 공백
        Spacer(modifier = Modifier.height(8.dp))
        // 내용
        state.exam.description?.run {
            QuackBody2(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = this,
            )
        }
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        // 태그 목록
        QuackSingeLazyRowTag(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalSpace = 4.dp,
            items = state.tagNames,
            tagType = QuackTagType.Grayscale(""),
            onClick = { index -> tagItemClick(state.tagNames[index]) },
        )
        // 공백
        Spacer(modifier = Modifier.height(24.dp))
        // 구분선
        QuackDivider()
        // 프로필 Layout
        DetailProfileLayout(state, followButtonClick = followButtonClick)
        // 구분선
        QuackDivider()
        // 공백
        Spacer(modifier = Modifier.height(24.dp))
        // 점수 분포도 Layout
        // TODO(riflockle7): 기획 정해질 시 활성화
        // DetailScoreDistributionLayout(state)
    }
}

/**
 * 상세 화면 프로필 Layout
 * TODO(riflockle7): 추후 공통화하기
 */
@Composable
private fun DetailProfileLayout(
    state: DetailState.Success,
    followButtonClick: () -> Unit,
) {
    val isFollowed = remember(state.isFollowing) { state.isFollowing }
    val toast = rememberToast()
    val detailLoadingMypageToastMessage = stringResource(id = R.string.detail_loading_mypage_toast)

    Row(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 작성자 프로필 이미지
        if (state.profileImageUrl.isNotEmpty()) {
            QuackImage(
                src = state.profileImageUrl,
                shape = SquircleShape,
                size = DpSize(32.dp, 32.dp),
            )
        } else {
            Box(
                Modifier
                    .size(DpSize(32.dp, 32.dp))
                    .clip(SquircleShape)
                    .background(QuackColor.Gray2.composeColor),
            )
        }

        // 공백
        Spacer(modifier = Modifier.width(8.dp))
        // 닉네임, 응시자, 일자 Layout
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 댓글 작성자 닉네임
                QuackBody3(
                    text = state.nickname,
                    onClick = {
                        // TODO(riflockle7): 프로필 화면으로 이동
                        toast(detailLoadingMypageToastMessage)
                    },
                    color = QuackColor.Black,
                )

                // 공백
                Spacer(modifier = Modifier.width(4.dp))
            }

            // 공백
            Spacer(modifier = Modifier.height(2.dp))

            // 덕티어 + 퍼센트, 태그
            QuackBody3(
                text = stringResource(
                    R.string.detail_tier_tag,
                    state.exam.user?.duckPower?.tier ?: "",
                    state.exam.user?.duckPower?.tag?.name ?: "",
                ),
                color = QuackColor.Gray2,
            )
        }
        // 공백
        Spacer(modifier = Modifier.weight(1f))

        // 팔로우 버튼
        QuackBody2(
            padding = PaddingValues(
                top = 8.dp,
                bottom = 8.dp,
            ),
            text = stringResource(
                if (isFollowed) {
                    R.string.detail_following
                } else {
                    R.string.detail_follow
                },
            ),
            color = if (isFollowed) QuackColor.Gray2 else QuackColor.DuckieOrange,
            onClick = followButtonClick,
        )
    }
}

/** 상세 화면 점수 분포도 Layout */
@Composable
@Suppress("unused")
private fun DetailScoreDistributionLayout(state: DetailState.Success) {
    // 제목 Layout
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 제목
        QuackText(text = "점수 분포도", style = QuackTextStyle.Title2)
        // 공백
        Spacer(modifier = Modifier.weight(1f))
        // 정답률 텍스트
        state.exam.answerRate?.percents?.run {
            QuackText(
                text = stringResource(
                    R.string.detail_right_percent,
                    this,
                ),
                style = QuackTextStyle.Body2,
            )
        }
    }
    // 공백
    Spacer(modifier = Modifier.height(8.dp))
    // 분포도 레퍼
    QuackText(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = "분포도 레퍼 필요",
        style = QuackTextStyle.Body2,
    )
}

/** 상세 화면 최하단 Layout */
@Composable
private fun DetailBottomLayout(
    modifier: Modifier,
    state: DetailState.Success,
    onHeartClick: () -> Unit,
    onChallengeClick: () -> Unit,
) {
    Column(modifier = modifier) {
        // 구분선
        QuackDivider()
        // 버튼 모음 Layout
        // TODO(riflockle7): 추후 Layout 을 활용해 처리하기
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // 좋아요 버튼
            QuackImage(
                src = if (state.isHeart) {
                    team.duckie.quackquack.ui.R.drawable.quack_ic_heart_filled_24
                } else {
                    team.duckie.quackquack.ui.R.drawable.quack_ic_heart_24
                },
                size = DpSize(24.dp, 24.dp),
                onClick = onHeartClick,
            )

            // 버튼
            QuackSmallButton(
                text = state.buttonTitle,
                type = QuackSmallButtonType.Fill,
                enabled = true,
                onClick = onChallengeClick,
            )
        }
    }
}

/** 상세 화면에서 사용하는 TopAppBar */
@Composable
private fun TopAppCustomBar(modifier: Modifier, state: DetailState.Success) {
    val activity = LocalContext.current as Activity
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(QuackColor.White.composeColor)
            .padding(
                vertical = 6.dp,
                horizontal = 10.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        QuackImage(
            padding = PaddingValues(6.dp),
            src = QuackIcon.ArrowBack,
            size = DpSize(24.dp, 24.dp),
            rippleEnabled = false,
            onClick = { activity.finish() },
        )

        QuackCircleTag(
            text = state.mainTagNames,
            trailingIcon = QuackIcon.ArrowRight,
            isSelected = false,
        )
    }
}
