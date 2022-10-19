package land.sungbin.androidprojecttemplate.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.common.component.DuckieFab
import land.sungbin.androidprojecttemplate.common.UiStatus
import land.sungbin.androidprojecttemplate.common.component.DuckieLoadingIndicator
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import land.sungbin.androidprojecttemplate.home.component.DrawerContent
import land.sungbin.androidprojecttemplate.home.component.DuckDealHolder
import land.sungbin.androidprojecttemplate.home.component.FeedHeader
import land.sungbin.androidprojecttemplate.home.component.FeedHolder
import land.sungbin.androidprojecttemplate.home.component.HomeDuckDealFeed
import land.sungbin.androidprojecttemplate.home.component.HomeNormalFeed
import land.sungbin.androidprojecttemplate.home.component.dummyTags
import land.sungbin.androidprojecttemplate.home.component.priceToString
import land.sungbin.androidprojecttemplate.home.component.toUnitString
import team.duckie.quackquack.ui.component.QuackBottomSheetItem
import team.duckie.quackquack.ui.component.QuackHeadlineBottomSheet
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMenuFabItem
import team.duckie.quackquack.ui.component.QuackModalDrawer
import team.duckie.quackquack.ui.component.QuackSimpleBottomSheet
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.rememberQuackDrawerState
import team.duckie.quackquack.ui.icon.QuackIcon

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalLifecycleComposeApi::class,
)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeState by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberQuackDrawerState()
    val homeBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val moreBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedUser by remember { mutableStateOf("") }

    val filterQuackBottomSheetItems = homeFilterBottomSheetItems()
    val moreQuackBottomSheetItems = MoreBottomSheetItems(selectedUser = selectedUser)

    var filterBottomSheetItems by remember { mutableStateOf(filterQuackBottomSheetItems) }
    var moreBottomSheetItems by remember { mutableStateOf(filterQuackBottomSheetItems) }

    QuackModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {
        QuackHeadlineBottomSheet(
            bottomSheetState = homeBottomSheetState,
            headline = stringResource(id = R.string.feed_filtering_title),
            items = filterBottomSheetItems,
            onClick = { headLineBottomSheetItem: QuackBottomSheetItem ->
                filterBottomSheetItems = filterBottomSheetItems.map { item: QuackBottomSheetItem ->
                    if (headLineBottomSheetItem == item) {
                        QuackBottomSheetItem(item.title, true)
                    } else {
                        QuackBottomSheetItem(item.title, false)
                    }
                }.toPersistentList()
            }
        ) {
            QuackSimpleBottomSheet(
                bottomSheetState = moreBottomSheetState,
                items = moreQuackBottomSheetItems,
                onClick = { simpleBottomSheetItem: QuackBottomSheetItem ->
                    //index에 따라 팔로우, 피드차단, 신고하기
                }
            ) {
                when (homeState.status) {
                    is UiStatus.Success -> {
                        HomeComponent(
                            feeds = when {
                                filterBottomSheetItems[FeedIndex].isImportant -> homeState.feeds.filter { feed: Feed ->
                                    feed.type == FeedType.Normal
                                }

                                filterBottomSheetItems[DuckDealIndex].isImportant -> homeState.feeds.filter { feed: Feed ->
                                    feed.type == FeedType.DuckDeal
                                }

                                else -> homeState.feeds
                            },
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
                            onClickMoreIcon = { user ->
                                coroutineScope.launch {
                                    moreBottomSheetState.show()
                                }
                                selectedUser = user
                            },
                        )
                    }

                    UiStatus.Loading -> {
                        Crossfade(targetState = homeState.status) {
                            DuckieLoadingIndicator()
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun HomeComponent(
    feeds: List<Feed>,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    onClickMoreIcon: (
        user: String,
    ) -> Unit,
) {
    val selectedTags = remember { mutableStateOf(dummyTags) }
    val commentCount by remember { mutableStateOf(0) }
    val feedState = remember { mutableStateOf(feeds) }
    var isLike by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(12000) }
    var fabExpanded by remember { mutableStateOf(false) }
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
        Box(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp,
                start = 16.dp,
                end = 16.dp,
            )
        ) {
            FeedHeader(
                profile = R.drawable.duckie_profile,
                title = stringResource(id = R.string.duckie_name),
                content = stringResource(id = R.string.duckie_introduce),
                tagItems = selectedTags.value,
                onTagClick = { index: Int ->
                    selectedTags.value = selectedTags.value - selectedTags.value[index]
                }
            )
        }
        LazyColumn(
            modifier = Modifier.weight(weight = 1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 24.dp)
        ) {
            items(
                items = feeds,
                key = { feed: Feed ->
                    feed.id
                }
            ) { feed: Feed -> // //DuckDeal 이므로 null이 아님을 보장
                when (feed.type) {
                    FeedType.Normal -> {
                        HomeNormalFeed(
                            FeedHolder(
                                profile = R.drawable.duckie_profile,
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
                                profile = R.drawable.duckie_profile,
                                nickname = feed.writerId,
                                time = "3일 전", //Date 로직 작성 필요
                                content = feed.content.text,
                                onMoreClick = onClickMoreIcon,
                                commentCount = { commentCount.toString() },
                                onClickComment = {
                                    //navigate
                                },
                                likeCount = { likeCount.toUnitString() },
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
                                isDirectDealing = feed.isDirectDealing!!,
                                parcelable = feed.parcelable!!,
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
    DuckieFab(
        items = homeFabMenuItems(),
        expanded = fabExpanded,
        onFabClick = {
            fabExpanded = !fabExpanded
        },
        onItemClick = { index, item ->

        },
        paddingValues = homeFabPadding,
    )
}

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

private const val FeedIndex = 1
private const val DuckDealIndex = 2

@Stable
@Composable
internal fun homeFabMenuItems(): PersistentList<QuackMenuFabItem> {
    return persistentListOf(
        QuackMenuFabItem(
            icon = QuackIcon.Feed,
            text = stringResource(id = R.string.feed),
        ),
        QuackMenuFabItem(
            icon = QuackIcon.Buy,
            text = stringResource(id = R.string.duck_deal),
        )
    )
}

@Stable
@Composable
internal fun homeFilterBottomSheetItems(): PersistentList<QuackBottomSheetItem> {
    return persistentListOf(
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
    )
}

@Stable
@Composable
internal fun MoreBottomSheetItems(selectedUser: String): PersistentList<QuackBottomSheetItem> {
    return persistentListOf(
        QuackBottomSheetItem(
            title = stringResource(R.string.follow_other, selectedUser),
            isImportant = false,
        ),
        QuackBottomSheetItem(
            title = stringResource(R.string.blocking_other_feed, selectedUser),
            isImportant = false,
        ),
        QuackBottomSheetItem(
            title = stringResource(R.string.report),
            isImportant = false,
        )
    )
}