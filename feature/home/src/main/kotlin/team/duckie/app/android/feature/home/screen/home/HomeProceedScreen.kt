/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalQuackQuackApi::class,
)

@file:Suppress("unused") // 더미 값, 미구현 된 내용
@file:AllowMagicNumber("더미 값, 미구현 된 내용")

package team.duckie.app.android.feature.home.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import team.duckie.app.android.common.compose.GetHeightRatioW129H84
import team.duckie.app.android.common.compose.GetHeightRatioW328H240
import team.duckie.app.android.common.compose.GetHeightRatioW360H90
import team.duckie.app.android.common.compose.GetHeightRatioW85H63
import team.duckie.app.android.common.compose.activityViewModel
import team.duckie.app.android.common.compose.ui.Spacer
import team.duckie.app.android.common.compose.ui.icon.v1.ArrowRightId
import team.duckie.app.android.common.compose.ui.quack.QuackProfileImage
import team.duckie.app.android.common.kotlin.AllowMagicNumber
import team.duckie.app.android.common.kotlin.fastForEach
import team.duckie.app.android.domain.exam.model.ExamFunding
import team.duckie.app.android.domain.home.model.HomeFunding
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.home.R
import team.duckie.app.android.feature.home.component.HomeTopAppBar
import team.duckie.app.android.feature.home.constants.HomeStep
import team.duckie.app.android.feature.home.constants.MainScreenType
import team.duckie.app.android.feature.home.viewmodel.home.HomeState
import team.duckie.app.android.feature.home.viewmodel.home.HomeViewModel
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.icon.QuackIcon
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.ui.QuackImage
import team.duckie.quackquack.ui.QuackTag
import team.duckie.quackquack.ui.QuackTagStyle
import team.duckie.quackquack.ui.QuackText
import team.duckie.quackquack.ui.sugar.QuackBody1
import team.duckie.quackquack.ui.sugar.QuackSubtitle2
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi

@Suppress("unused") // 더미 값
@Composable
internal fun HomeProceedScreen(
    initState: (MainScreenType, () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    state: HomeState,
    homeViewModel: HomeViewModel = activityViewModel(),
    navigateToCreateExam: () -> Unit,
    navigateToCreateExamDetail: (Int) -> Unit,
    navigateToHomeDetail: (Int) -> Unit,
    navigateToSearch: (String) -> Unit,
    openExamBottomSheet: (Int) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isHomeProceedPullRefreshLoading,
        onRefresh = {
            homeViewModel.refreshProceedScreen(forceLoading = true)
        },
    )

    LaunchedEffect(Unit) {
        initState(MainScreenType.HomeProceed) { homeViewModel.initProceedScreen() }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                HomeTopAppBar(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    selectedTabIndex = state.homeSelectedIndex.index,
                    onTabSelected = { step ->
                        homeViewModel.changedHomeScreen(HomeStep.toStep(step))
                    },
                    onClickedCreate = navigateToCreateExam,
                    onClickedNotice = {},
                )
            }

            // 공백
            item {
                Spacer(Modifier.height(12.dp))
            }

            // 제목
            item {
                QuackText(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.home_proceed_title),
                    typography = QuackTypography.HeadLine1,
                )
            }

            // 공백
            item {
                Spacer(Modifier.height(12.dp))
            }

            // 진행중인 덕력고사 목록 뷰
            items(state.homeFundings) { item ->
                ProceedItemView(item, navigateToCreateExamDetail)
            }

            // 공백
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 전체보기 버튼
            item {
                ProceedViewAllButton(onViewAllClick = {})
            }

            // 공백
            item {
                Spacer(Modifier.height(48.dp))
            }

            // 덕력고사 진행중 배너 뷰
            item {
                // TODO(riflockle7): 이렇게 화면 이동하는 게 맞는지 확인 필요
                ProceedBannerView()
            }

            // 공백
            item {
                Spacer(Modifier.height(48.dp))
            }

            // 덕력고사 진행 중 카테고리 영역 뷰
            item {
                ProceedCategorySection(
                    username = state.me?.nickname ?: "사용자",
                    tagItemClick = homeViewModel::clickProceedFundingTag,
                    selectedTag = state.homeFundingSelectedTag,
                    categories = state.homeFundingTags,
                    items = state.examFundings,
                    navigateToCreateExamDetail = navigateToCreateExamDetail,
                )
            }
        }
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isHomeProceedPullRefreshLoading,
            state = pullRefreshState,
        )
    }
}

/** 진행중인 덕력고사 Item 뷰 */
@Composable
fun ProceedItemView(homeFunding: HomeFunding, navigateToCreateExamDetail: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(8.dp),
            )
            .quackClickable(
                onClick = { navigateToCreateExamDetail(homeFunding.id) },
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp,
                    ),
                ),
        ) {
            // 덕퀴즈/덕질고사 썸네일 이미지
            AsyncImage(
                model = homeFunding.thumbnailUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = GetHeightRatioW328H240),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            // 오픈까지 x문제
            QuackText(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 4.dp))
                    .background(
                        if (homeFunding.remainCount == 1) {
                            QuackColor.DuckieOrange.value
                        } else {
                            Color(0xFF222222)
                        },
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = stringResource(
                    id = R.string.home_proceed_item_count_down,
                    homeFunding.remainCount,
                ),
                typography = QuackTypography.Body3.change(color = QuackColor.White),
            )
        }

        // 만들어진 문제 개수 / 최대 문제 개수 비율 막대 그래프
        Row(modifier = Modifier.height(8.dp)) {
            if (homeFunding.problemCount != 0) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(homeFunding.problemCount.toFloat())
                        .background(QuackColor.DuckieOrange.value),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(homeFunding.remainCount.toFloat())
                    .background(QuackColor.Gray3.value),
            )
        }

        // 덕력고사 정보 & 참여율
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            // 덕력고사 이름
            QuackText(text = homeFunding.title, typography = QuackTypography.HeadLine2)

            Spacer(modifier = Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                // 프로필 이미지
                QuackProfileImage(
                    modifier = Modifier.size(DpSize(width = 16.dp, height = 16.dp)),
                    profileUrl = homeFunding.user?.profileImageUrl,
                )

                // 닉네임 + 참여 인원수
                QuackText(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(
                        R.string.home_proceed_item_info,
                        homeFunding.user?.nickname ?: "",
                        stringResource(id = R.string.home_proceed_item_join_count, homeFunding.contributorCount),
                    ),
                    typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
                )

                // 너비
                Spacer(weight = 1f)

                // 만들어진 문제 개수 / 최대 문제 개수
                QuackText(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(
                        R.string.home_proceed_item_problem_count,
                        homeFunding.problemCount,
                        homeFunding.totalProblemCount,
                    ),
                    typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
                )
            }
        }
    }
}

/** 배너 뷰 */
@Composable
fun ProceedBannerView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = GetHeightRatioW360H90)
            .background(Color(0xFFFFF8CF)),
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // 직접 덕력고사를 열고 싶다면
            QuackText(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = R.string.home_proceed_banner_title),
                typography = QuackTypography.HeadLine2,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 출제 제안하기 버튼
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(QuackColor.White.value)
                    .padding(vertical = 6.dp, horizontal = 12.dp),
            ) {
                // 텍스트
                QuackSubtitle2(
                    text = stringResource(id = R.string.home_proceed_banner_submit_button_title),
                )

                // 공백
                Spacer(space = 4.dp)

                // 우측 방향 화살표
                QuackImage(
                    modifier = Modifier.size(DpSize(width = 16.dp, height = 16.dp)),
                    src = QuackIcon.ArrowRightId,
                )
            }
        }

        // 배너 우측 이미지
        QuackImage(
            modifier = Modifier
                .padding(top = 6.dp)
                .aspectRatio(GetHeightRatioW129H84)
                .align(Alignment.CenterEnd),
            src = R.drawable.home_proceed_banner_right,
        )
    }
}

/** 진행중인 덕력고사 카테고리 섹션 */
@Composable
fun ProceedCategorySection(
    username: String,
    tagItemClick: (Tag) -> Unit,
    selectedTag: Tag,
    categories: List<Tag>,
    items: List<ExamFunding>,
    navigateToCreateExamDetail: (Int) -> Unit,
) {
    // 제목
    QuackText(
        modifier = Modifier.padding(start = 16.dp),
        text = stringResource(
            id = R.string.home_proceed_category_title,
            username,
        ),
        typography = QuackTypography.HeadLine1,
    )

    // 공백
    Spacer(modifier = Modifier.height(14.dp))

    // 카테고리 목록
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        itemsIndexed(items = categories) { index, tag ->
            QuackTag(
                text = tag.name,
                style = QuackTagStyle.Outlined,
                onClick = {
                    tagItemClick(tag)
                },
                selected = selectedTag.id == tag.id,
            )
        }
    }

    // 공백
    Spacer(modifier = Modifier.height(14.dp))

    // 카테고리에 해당하는 덕력고사 목록
    Column {
        items.fastForEach { item ->
            ProceedCategoryItemView(
                categoryItem = item,
                navigateToCreateExamDetail = navigateToCreateExamDetail,
            )
        }
    }

    // 전체보기 버튼
    ProceedViewAllButton(onViewAllClick = {})

    // 공백
    Spacer(modifier = Modifier.height(20.dp))
}

/** 카테고리별 뷰[ProceedCategorySection]에 보이는 Item 뷰 */
@Composable
fun ProceedCategoryItemView(categoryItem: ExamFunding, navigateToCreateExamDetail: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .quackClickable(onClick = { navigateToCreateExamDetail(categoryItem.id) }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 덕퀴즈/덕질고사 썸네일 이미지
        AsyncImage(
            model = categoryItem.thumbnailUrl,
            modifier = Modifier
                .width(85.dp)
                .clip(RoundedCornerShape(8.dp))
                .aspectRatio(ratio = GetHeightRatioW85H63),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )

        // 덕력고사 정보 & 참여율
        Column(modifier = Modifier.padding(start = 8.dp)) {
            // 덕력고사 이름
            QuackText(text = categoryItem.title, typography = QuackTypography.HeadLine2)

            // 공백
            Spacer(modifier = Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                // 프로필 이미지
                QuackProfileImage(
                    modifier = Modifier.size(DpSize(width = 16.dp, height = 16.dp)),
                    profileUrl = categoryItem.user.profileImageUrl,
                )

                // 닉네임 + 참여 인원수
                QuackText(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(
                        R.string.home_proceed_item_info,
                        categoryItem.user.nickname,
                        stringResource(id = R.string.home_proceed_item_join_count, categoryItem.contributorCount),
                    ),
                    typography = QuackTypography.Body2.change(color = QuackColor.Gray1),
                )
            }
        }
    }

    // 공백
    Spacer(modifier = Modifier.height(16.dp))
}

/** 전체 보기 버튼 */
@Composable
private fun ProceedViewAllButton(onViewAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .quackClickable(onClick = onViewAllClick)
            .border(
                width = 1.dp,
                color = QuackColor.Gray3.value,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackBody1(
            text = stringResource(id = R.string.home_proceed_view_all_button_title),
        )

        Spacer(space = 4.dp)

        QuackImage(
            modifier = Modifier.size(DpSize(width = 24.dp, height = 24.dp)),
            src = QuackIcon.ArrowRightId,
        )
    }
}
