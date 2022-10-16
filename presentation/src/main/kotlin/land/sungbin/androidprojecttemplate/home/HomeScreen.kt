package land.sungbin.androidprojecttemplate.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.common.component.DuckieFab
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.common.component.DuckieLoadingIndicator
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.FeedType
import land.sungbin.androidprojecttemplate.home.component.DuckDealHolder
import land.sungbin.androidprojecttemplate.home.component.FeedHeader
import land.sungbin.androidprojecttemplate.home.component.FeedHolder
import land.sungbin.androidprojecttemplate.home.component.HomeDuckDealFeed
import land.sungbin.androidprojecttemplate.home.component.HomeNormalFeed
import land.sungbin.androidprojecttemplate.home.component.dummyTags
import land.sungbin.androidprojecttemplate.home.component.getTradingMethod
import land.sungbin.androidprojecttemplate.home.component.priceToString
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

@Stable
internal val homeFabPadding = PaddingValues(
    bottom = 12.dp,
    end = 16.dp
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


@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val homeState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberQuackDrawerState()
    val homeBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val moreBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    var fabExpanded by remember {
        mutableStateOf(false)
    }
    var selectedUser by remember {
        mutableStateOf("")
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
                when (homeState.status) {
                    is UiStatus.Success -> {
                        HomeComponent(
                            feeds = homeState.feeds,
                            onClickLeadingIcon = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            },
                            onClickTrailingIcon = {
                                coroutineScope.launch {
                                    homeBottomSheetState.show()
                                }
                            },
                            onClickMoreIcon = {
                                coroutineScope.launch {
                                    moreBottomSheetState.show()
                                }
                            }
                        )
                    }
                    UiStatus.Loading -> {
                        Crossfade(targetState = homeState.status) {
                            DuckieLoadingIndicator()
                        }
                    }
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
        paddingValues = homeFabPadding,
    )
}

@Composable
fun HomeComponent(
    feeds: List<Feed>,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    onClickMoreIcon: () -> Unit,
) {
    val selectedTags = remember {
        mutableStateListOf(
            elements = Array(dummyTags.size) { false }
        )
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
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        QuackTopAppBar(
            leadingIcon = QuackIcon.Profile,
            onClickLeadingIcon = onClickLeadingIcon,
            centerContent = {
                QuackImage(
                    src = R.drawable.top_bar_logo,
                    overrideSize = DuckieLogoSize,
                )
            },
            trailingIcon = QuackIcon.Filter,
            onClickTrailingIcon = onClickTrailingIcon,
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
            items(feeds) { feed: Feed -> // //DuckDeal 이므로 null이 아님을 보장
                when (feed.type) {
                    FeedType.Normal -> {
                        HomeNormalFeed(
                            FeedHolder(
                                profile = feed.writerId,
                                nickname = feed.writerId,
                                time = "3일 전", //Date 로직 작성 필요
                                content = feed.content.text,
                                onMoreClick = onClickMoreIcon,
                                commentCount = { commentCount.toString() },
                                onClickComment = {
                                    //navigate
                                },
                                likeCount = { likeCount.toString() },
                                isLike = { isLike },
                                onClickLike = {
                                    when (isLike) {
                                        true -> likeCount--
                                        false -> likeCount++
                                    }
                                    isLike = !isLike
                                },
                                images = feed.content.images.toPersistentList()
                            ),
                        )
                    }

                    FeedType.DuckDeal -> {
                        HomeDuckDealFeed(
                            FeedHolder(
                                profile = feed.writerId,
                                nickname = feed.writerId,
                                time = "3일 전", //Date 로직 작성 필요
                                content = feed.content.text,
                                onMoreClick = onClickMoreIcon,
                                commentCount = { commentCount.toString() },
                                onClickComment = {
                                    //navigate
                                },
                                likeCount = { likeCount.toString() },
                                isLike = { isLike },
                                onClickLike = {
                                    when (isLike) {
                                        true -> likeCount--
                                        false -> likeCount++
                                    }
                                    isLike = !isLike
                                },
                                images = feed.content.images.toPersistentList()
                            ),
                            DuckDealHolder(
                                tradingMethod = getTradingMethod(
                                    feed.isDirectDealing!!,
                                    feed.parcelable!!
                                ),
                                price = feed.price!!.priceToString(),
                                dealState = feed.dealState!!,
                                location = feed.location!!,
                            ),
                        )
                    }
                }
            }
        }
    }
}