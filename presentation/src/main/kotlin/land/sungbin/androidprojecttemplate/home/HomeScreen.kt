package land.sungbin.androidprojecttemplate.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.common.DuckieFab
import land.sungbin.androidprojecttemplate.domain.model.DealState
import land.sungbin.androidprojecttemplate.home.component.DuckDealHolder
import land.sungbin.androidprojecttemplate.home.component.FeedHeader
import land.sungbin.androidprojecttemplate.home.component.FeedHolder
import land.sungbin.androidprojecttemplate.home.component.HomeDuckDealFeed
import land.sungbin.androidprojecttemplate.home.component.HomeNormalFeed
import land.sungbin.androidprojecttemplate.home.component.TradingMethod
import land.sungbin.androidprojecttemplate.home.component.dummyTags
import team.duckie.quackquack.ui.component.QuackBottomNavigation
import team.duckie.quackquack.ui.component.QuackBottomSheetItem
import team.duckie.quackquack.ui.component.QuackHeadlineBottomSheet
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMenuFabItem
import team.duckie.quackquack.ui.component.QuackModalDrawer
import team.duckie.quackquack.ui.component.QuackSimpleBottomSheet
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.rememberQuackDrawerState
import team.duckie.quackquack.ui.icon.QuackIcon

@Stable
internal val DuckieLogoSize = DpSize(
    width = 72.dp,
    height = 24.dp,
)

internal val homeFabMenuItems = persistentListOf(
    QuackMenuFabItem(
        icon = QuackIcon.Feed,
        text = "피드",
    ),
    QuackMenuFabItem(
        icon = QuackIcon.Buy,
        text = "덕딜",
    )
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberQuackDrawerState()
    val homeBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val moreBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val selectedTags = remember {
        mutableStateListOf(
            elements = Array(dummyTags.size) { false }
        )
    }
    var fabExpanded by remember {
        mutableStateOf(false)
    }
    var commentCount by remember {
        mutableStateOf(0)
    }
    var isLike by remember {
        mutableStateOf(false)
    }
    var likeCount by remember {
        mutableStateOf(0)
    }
    var selectedUser by remember {
        mutableStateOf("")
    }
    var selectedNavigationIndex by remember {
        mutableStateOf(0)
    }
    QuackModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            //ModalDrawerContent()
        }
    ) {
        QuackHeadlineBottomSheet(
            bottomSheetState = homeBottomSheetState,
            headline = stringResource(id = R.string.feed_filtering_title),
            items = persistentListOf(
                QuackBottomSheetItem(
                    title = stringResource(id = R.string.feed_filtering_both_feed_duck_deal),
                    isImportant = false,
                ),
                QuackBottomSheetItem(
                    title = stringResource(id = R.string.feed_filtering_feed),
                    isImportant = true,
                ),
                QuackBottomSheetItem(
                    title = stringResource(id = R.string.feed_filtering_duck_deal),
                    isImportant = false,
                )

            ),
            onClick = { quackBottomSheetItem ->

            }
        ) {
            QuackSimpleBottomSheet(
                bottomSheetState = moreBottomSheetState,
                items = persistentListOf(
                    QuackBottomSheetItem(
                        title = "${selectedUser}님 팔로우",
                        isImportant = false,
                    ),
                    QuackBottomSheetItem(
                        title = "${selectedUser}님 피드 차단",
                        isImportant = false,
                    ),
                    QuackBottomSheetItem(
                        title = "피드 신고",
                        isImportant = true,
                    )
                ),
                onClick = {

                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    QuackTopAppBar(
                        leadingIcon = QuackIcon.Profile,
                        onClickLeadingIcon = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                        centerContent = {
                            QuackImage(
                                src = R.drawable.top_bar_logo,
                                overrideSize = DuckieLogoSize,
                            )
                        },
                        trailingIcon = QuackIcon.Filter,
                        onClickTrailingIcon = {
                            coroutineScope.launch {
                                homeBottomSheetState.show()
                            }
                        }
                    )
                    LazyColumn(
                        modifier = Modifier.weight(
                            weight = 1f,
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 24.dp,
                        )
                    ) {
                        item {
                            FeedHeader(
                                profile = R.drawable.duckie_profile,
                                title = "더키",
                                content = "덕키즈 무지개양말, 만나서 반갑덕!\n관심 태그를 추가하면 피드를 추천해주겠덕",
                                tagItems = dummyTags,
                                tagItemsSelection = selectedTags,
                                onTagClick = { index ->
                                    selectedTags[index] = !selectedTags[index]
                                }
                            )
                        }
                        item {
                            HomeNormalFeed(
                                FeedHolder(
                                    profile = R.drawable.duckie_profile,
                                    nickname = "우주사령관",
                                    time = "3일 전",
                                    content = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                                            "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                                            "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
                                    onMoreClick = {
                                        coroutineScope.launch {
                                            moreBottomSheetState.show()
                                        }
                                    },
                                    commentCount = {
                                        commentCount.toString()
                                    },
                                    onClickComment = {

                                    },
                                    likeCount = {
                                        likeCount.toString()
                                    },
                                    isLike = { isLike },
                                    onClickLike = {
                                        when (isLike) {
                                            true -> likeCount--
                                            false -> likeCount++
                                        }
                                        isLike = !isLike
                                    },
                                )
                            )
                        }
                        item {
                            HomeNormalFeed(
                                FeedHolder(
                                    profile = R.drawable.duckie_profile,
                                    nickname = "우주사령관",
                                    time = "3일 전",
                                    content = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                                            "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                                            "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
                                    onMoreClick = {
                                        coroutineScope.launch {
                                            moreBottomSheetState.show()
                                        }
                                    },
                                    commentCount = {
                                        commentCount.toString()
                                    },
                                    onClickComment = {

                                    },
                                    likeCount = {
                                        likeCount.toString()
                                    },
                                    isLike = { isLike },
                                    onClickLike = {
                                        when (isLike) {
                                            true -> likeCount--
                                            false -> likeCount++
                                        }
                                        isLike = !isLike
                                    },
                                    images = persistentListOf(
                                        R.drawable.duckie_profile
                                    )
                                )
                            )
                        }
                        item {
                            HomeNormalFeed(
                                FeedHolder(
                                    profile = R.drawable.duckie_profile,
                                    nickname = "우주사령관",
                                    time = "3일 전",
                                    content = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                                            "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                                            "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
                                    onMoreClick = {
                                        coroutineScope.launch {
                                            moreBottomSheetState.show()
                                        }
                                    },
                                    commentCount = {
                                        commentCount.toString()
                                    },
                                    onClickComment = {

                                    },
                                    likeCount = {
                                        likeCount.toString()
                                    },
                                    isLike = { isLike },
                                    onClickLike = {
                                        when (isLike) {
                                            true -> likeCount--
                                            false -> likeCount++
                                        }
                                        isLike = !isLike
                                    },
                                    images = persistentListOf(
                                        R.drawable.duckie_profile,
                                        R.drawable.duckie_profile
                                    )
                                )
                            )
                        }
                        item {
                            HomeDuckDealFeed(
                                FeedHolder(
                                    profile = R.drawable.duckie_profile,
                                    nickname = "우주사령관",
                                    time = "3일 전",
                                    content = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                                            "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                                            "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
                                    onMoreClick = {
                                        coroutineScope.launch {
                                            moreBottomSheetState.show()
                                        }
                                    },
                                    commentCount = {
                                        commentCount.toString()
                                    },
                                    onClickComment = {

                                    },
                                    likeCount = {
                                        likeCount.toString()
                                    },
                                    isLike = { isLike },
                                    onClickLike = {
                                        when (isLike) {
                                            true -> likeCount--
                                            false -> likeCount++
                                        }
                                        isLike = !isLike
                                    },
                                    images = persistentListOf(
                                        R.drawable.duckie_profile,
                                        R.drawable.duckie_profile
                                    )
                                ),
                                DuckDealHolder(
                                    tradingMethod = TradingMethod.Direct,
                                    price = "30,000 원",
                                    dealState = DealState.Booking,
                                    location = "마포구 도화동",
                                ),
                            )
                        }
                        item {
                            HomeDuckDealFeed(
                                FeedHolder(
                                    profile = R.drawable.duckie_profile,
                                    nickname = "우주사령관",
                                    time = "3일 전",
                                    content = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                                            "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                                            "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
                                    onMoreClick = {
                                        coroutineScope.launch {
                                            moreBottomSheetState.show()
                                        }
                                    },
                                    commentCount = {
                                        commentCount.toString()
                                    },
                                    onClickComment = {

                                    },
                                    likeCount = {
                                        likeCount.toString()
                                    },
                                    isLike = { isLike },
                                    onClickLike = {
                                        when (isLike) {
                                            true -> likeCount--
                                            false -> likeCount++
                                        }
                                        isLike = !isLike
                                    },
                                    images = persistentListOf(
                                        R.drawable.duckie_profile,
                                    )
                                ),
                                DuckDealHolder(
                                    tradingMethod = TradingMethod.Both,
                                    price = "30,000 원",
                                    dealState = DealState.Done,
                                    location = "마포구 도화동",
                                ),
                            )
                        }
                    }
                    QuackBottomNavigation(
                        selectedIndex = selectedNavigationIndex,
                        onClick = { index ->
                            selectedNavigationIndex = index
                        },
                    )
                }
            }
        }
    }
    DuckieFab(
        items = homeFabMenuItems,
        expanded = fabExpanded,
        onFabClick = {
            fabExpanded = !fabExpanded
        },
        onItemClick = { index, item ->

        },
        paddingProvider = {
            PaddingValues(
                bottom = 64.dp,
                end = 16.dp
            )
        }
    )
}