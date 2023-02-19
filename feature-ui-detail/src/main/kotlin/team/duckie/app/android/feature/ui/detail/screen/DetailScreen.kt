/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:AllowMagicNumber

package team.duckie.app.android.feature.ui.detail.screen

import android.app.Activity
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.orbitmvi.orbit.compose.collectAsState
import team.duckie.app.android.feature.ui.detail.R
import team.duckie.app.android.feature.ui.detail.viewmodel.DetailViewModel
import team.duckie.app.android.feature.ui.detail.viewmodel.sideeffect.DetailSideEffect
import team.duckie.app.android.feature.ui.detail.viewmodel.state.DetailState
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
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.component.QuackTitle1
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
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
) = when (val state = viewModel.collectAsState().value) {
    is DetailState.Loading -> DetailLoadingScreen(
        viewModel,
        modifier,
    )

    is DetailState.Success -> DetailSuccessScreen(
        viewModel,
        modifier,
        state,
    )

    is DetailState.Error -> DetailErrorScreen(
        viewModel,
        modifier,
    )
}

@Composable
private fun DetailLoadingScreen(viewModel: DetailViewModel, modifier: Modifier) {
    LaunchedEffect(Unit) {
        viewModel.initState()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        CircularProgressIndicator(
            color = QuackColor.DuckieOrange.composeColor,
        )
    }
}

/** 데이터 성공적으로 받은[DetailState.Success] 상세 화면 */
@Composable
private fun DetailSuccessScreen(
    viewModel: DetailViewModel,
    modifier: Modifier,
    state: DetailState.Success,
) {
    val toast = rememberToast()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is DetailSideEffect.SendToast -> toast(effect.message)
                else -> Unit
            }
        }
    }

    Layout(
        modifier = modifier.navigationBarsPadding(),
        content = {
            // 상단 탭바 Layout
            TopAppCustomBar(
                modifier = Modifier.layoutId(DetailScreenTopAppBarLayoutId),
                state = state,
            )
            // content Layout
            DetailContentLayout(state) {
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
            contentScale = ContentScale.FillWidth,
            src = state.exam.thumbnailUrl,
        )
        // 공백
        Spacer(modifier = Modifier.height(12.dp))
        // 제목
        QuackHeadLine2(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = state.exam.title,
        )
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
            onClick = {
                // TODO(riflockle7): 태그 검색 화면으로 이동
            },
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
        DetailScoreDistributionLayout(state)
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

            // 응시자, 일자
            QuackBody3(
                text = stringResource(
                    R.string.detail_num_date,
                    "${state.exam.solvedCount}",
                    "1일 전",
                ),
                color = QuackColor.Gray2,
            )
        }
        // 공백
        Spacer(modifier = Modifier.weight(1f))
        // TODO(riflockle7): 추후 팔로우 완료 시에 대한 처리 필요
        // 팔로우 버튼
        QuackBody2(
            padding = PaddingValues(
                top = 8.dp,
                bottom = 8.dp,
            ),
            text = stringResource(
                if (isFollowed) {
                    R.string.detail_follow_cancel
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

/** 에러 발생한[DetailState.Error] 상세 화면 */
@Composable
private fun DetailErrorScreen(viewModel: DetailViewModel, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TODO(riflockle7): 추후 DuckieCircularProgressIndicator.kt 와 합치거나 꽥꽥 컴포넌트로 필요
        QuackTitle1(
            text = "에러입니다\nTODO$viewModel\n추후 데이터 다시 가져오기 로직 넣어야 합니다.",
        )
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
