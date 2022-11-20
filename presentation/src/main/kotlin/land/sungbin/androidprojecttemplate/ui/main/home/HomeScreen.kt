package land.sungbin.androidprojecttemplate.ui.main.home

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.component.DuckieFab
import land.sungbin.androidprojecttemplate.ui.component.UiStatus
import land.sungbin.androidprojecttemplate.ui.component.DuckieLoadingIndicator
import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import land.sungbin.androidprojecttemplate.ui.home.HomeViewModel
import land.sungbin.androidprojecttemplate.ui.main.home.component.DrawerContent
import land.sungbin.androidprojecttemplate.ui.main.home.component.DuckDealFeed
import land.sungbin.androidprojecttemplate.ui.main.home.component.FeedHeader
import land.sungbin.androidprojecttemplate.ui.main.home.component.NormalFeed
import land.sungbin.androidprojecttemplate.ui.main.home.dto.toDuckDealFeed
import land.sungbin.androidprojecttemplate.ui.main.home.dto.toNormalFeed
import land.sungbin.androidprojecttemplate.ui.main.navigation.MainScreens
import org.orbitmvi.orbit.viewmodel.observe
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
)
@Composable
internal fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    var homeState by remember { mutableStateOf(HomeState()) }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberQuackDrawerState()
    val homeBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val moreBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.observe(
            lifecycleOwner = lifecycleOwner,
            state = { state ->
                homeState = state
            },
            sideEffect = { sideEffect ->
                when (sideEffect) {
                    HomeSideEffect.NavigateToWriteFeed -> {

                    }

                    HomeSideEffect.NavigateToWriteDuckDeal -> {

                    }
                }
            }
        )
    }

    QuackModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onClickSetting = {
                    navController.navigate(
                        route = MainScreens.Setting.route,
                    )
                }
            )
        }
    ) {
        QuackHeadlineBottomSheet(
            bottomSheetState = homeBottomSheetState,
            headline = stringResource(id = R.string.feed_filtering_title),
            items = homeState.filterBottomSheetItems.toPersistentList(),
            onClick = { bottomSheetItem: QuackBottomSheetItem ->
                viewModel.selectFilterBottomSheet(bottomSheetItem)
            }
        ) {
            QuackSimpleBottomSheet(
                bottomSheetState = moreBottomSheetState,
                items = homeState.moreBottomSheetItems.toPersistentList(),
                onClick = { bottomSheetItem: QuackBottomSheetItem ->
                    viewModel.selectMoreBottomSheet(bottomSheetItem)
                }
            ) {
                HomeContent(
                    feeds = homeState.filteredFeeds,
                    itemStatus = homeState.itemStatus,
                    interestedTags = homeState.interestedTags,
                    fabExpanded = homeState.fabExpanded,
                    onClickLeadingIcon = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    onClickTrailingIcon = {
                        coroutineScope.launch { homeBottomSheetState.show() }
                    },
                    onClickMoreIcon = { selectedUser: String ->
                        viewModel.changeSelectedUser(selectedUser)
                        coroutineScope.launch { moreBottomSheetState.show() }
                    },
                    onClickHeartIcon = { _, _ -> }, //viewModel::onHeartClick,
                    onClickCommentIcon = {}, //viewModel::onCommentClick,
                    onClickTag = viewModel::deleteTag,
                    onFabMenuClick = viewModel::onFabMenuClick,
                    onRefresh = viewModel::refresh,
                    onFabClick = viewModel::onFabClick,
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    itemStatus: UiStatus,
    feeds: List<Feed>,
    interestedTags: List<String>,
    fabExpanded: Boolean,
    onClickLeadingIcon: () -> Unit,
    onClickTrailingIcon: () -> Unit,
    onClickHeartIcon: (
        feedId: String,
        isHearted: Boolean,
    ) -> Unit,
    onClickCommentIcon: () -> Unit,
    onClickMoreIcon: (user: String) -> Unit,
    onClickTag: (index: Int) -> Unit,
    onFabClick: (expanded: Boolean) -> Unit,
    onFabMenuClick: (index: Int) -> Unit,
    onRefresh: () -> Unit,
) {
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
        when (itemStatus) {
            UiStatus.Success -> {
                LazyFeedColumn(
                    feeds = feeds,
                    header = {
                        FeedHeader(
                            tagItems = interestedTags,
                            onTagClick = onClickTag
                        )
                    },
                    onRefresh = onRefresh,
                    onClickMoreIcon = onClickMoreIcon,
                    onClickHeartIcon = onClickHeartIcon,
                    onClickCommentIcon = onClickCommentIcon,
                )
            }

            UiStatus.Loading -> {
                Crossfade(targetState = itemStatus) {
                    DuckieLoadingIndicator()
                }
            }

            is UiStatus.Failed -> {

            }
        }
    }
    DuckieFab(
        items = homeFabMenuItems.toPersistentList(),
        expanded = fabExpanded,
        onFabClick = { onFabClick(fabExpanded) },
        onItemClick = { index, _ ->
            onFabMenuClick(index)
        },
        paddingValues = homeFabPadding,
    )
}

@Composable
internal fun LazyFeedColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        vertical = 8.dp,
        horizontal = 16.dp
    ),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(space = 28.dp),
    header: (@Composable () -> Unit)? = null,
    feeds: List<Feed>,
    onRefresh: () -> Unit,
    onClickMoreIcon: (selectedUser: String) -> Unit,
    onClickHeartIcon: (
        feedId: String,
        isHearted: Boolean,
    ) -> Unit,
    onClickCommentIcon: () -> Unit,
) {
    val swipeRefreshState = rememberSwipeRefreshState(false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement
        ) {
            if (header != null) {
                item { header() }
            }
            items(
                items = feeds,
                key = { feed -> feed.id },
                contentType = { feed -> feed.type }
            ) { feed: Feed ->
                when (feed.type) {
                    FeedType.Normal -> {
                        NormalFeed(
                            normalFeed = feed.toNormalFeed(),
                            onClickMoreIcon = onClickMoreIcon,
                            onClickHeartIcon = onClickHeartIcon,
                            onClickCommentIcon = onClickCommentIcon,
                        )
                    }

                    FeedType.DuckDeal -> {
                        DuckDealFeed(
                            duckDealFeed = feed.toDuckDealFeed(),
                            onClickMoreIcon = onClickMoreIcon,
                            onClickHeartIcon = onClickHeartIcon,
                            onClickCommentIcon = onClickCommentIcon,
                        )
                    }
                }
            }
        }
    }
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

internal val homeFabMenuItems = listOf(
    QuackMenuFabItem(
        icon = QuackIcon.Feed,
        text = "피드",
    ),
    QuackMenuFabItem(
        icon = QuackIcon.Buy,
        text = "덕딜",
    )
)

internal val filterBottomSheetItems = listOf(
    QuackBottomSheetItem(
        title = "피드, 덕딜 함께 보기",
        isImportant = true,
    ),
    QuackBottomSheetItem(
        title = "피드만 보기",
        isImportant = false,
    ),
    QuackBottomSheetItem(
        title = "덕딜만 보기",
        isImportant = false,
    )
)

internal val moreBottomSheetItems = listOf(
    QuackBottomSheetItem(
        title = "",
        isImportant = false,
    ),
    QuackBottomSheetItem(
        title = "",
        isImportant = false,
    ),
    QuackBottomSheetItem(
        title = "신고 하기",
        isImportant = false,
    )
)