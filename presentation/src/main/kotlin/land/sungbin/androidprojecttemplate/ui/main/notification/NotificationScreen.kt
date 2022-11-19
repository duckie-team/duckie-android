package land.sungbin.androidprojecttemplate.ui.main.notification

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.domain.model.NotificationItem
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.shared.android.extension.toast
import land.sungbin.androidprojecttemplate.shared.compose.extension.CoroutineScopeContent
import land.sungbin.androidprojecttemplate.ui.main.home.component.DrawerContent
import land.sungbin.androidprojecttemplate.util.DateUtil
import team.duckie.quackquack.ui.component.QuackAnnotatedBody2
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackModalDrawer
import team.duckie.quackquack.ui.component.QuackRoundImage
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.rememberQuackDrawerState
import team.duckie.quackquack.ui.icon.QuackIcon

@Composable
internal fun NotificationScreen(
    notificationViewModel: NotificationViewModel
) = CoroutineScopeContent {
    val drawerState = rememberQuackDrawerState()
    val swipeRefreshState = rememberSwipeRefreshState(false)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        notificationViewModel.fetch()
    }
    LaunchedEffect(notificationViewModel.effect) {
        notificationViewModel.effect.collect { effect ->
            when (effect) {
                is NotificationSideEffect.ClickNewComment -> {
                    toast(context, "${effect.item.type} 항목 클릭")
                }

                is NotificationSideEffect.ClickNewHeart -> {
                    toast(context, "${effect.item.type} 항목 클릭")
                }

                is NotificationSideEffect.ClickNewFollower -> {
                    toast(context, "${effect.item.type} 항목 클릭")
                }

                is NotificationSideEffect.ClickRequireWriteReview -> {
                    toast(context, "${effect.item.type} 항목 클릭")
                }

                is NotificationSideEffect.ClickRequireChangeDealState -> {
                    toast(context, "${effect.item.type} 항목 클릭")
                }

                is NotificationSideEffect.ClickRequireUpToDuckDeal -> {
                    toast(context, "${effect.item.type} 항목 클릭")
                }

                is NotificationSideEffect.ShowToast -> {
                    toast(context, effect.message, Toast.LENGTH_SHORT)
                }
            }
        }
    }

    QuackModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 상단 탭바
            QuackTopAppBar(
                leadingIcon = QuackIcon.Profile,
                headline = "알림",
                onClickLeadingIcon = {
                    coroutineScope.launch { drawerState.open() }
                },
            )

            // 공백
            Spacer(modifier = Modifier.height(4.dp))

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    coroutineScope.launch {
                        notificationViewModel.fetch()
                    }
                },
            ) {
                val notificationItems = notificationViewModel.state.value.notifications
                LazyColumn(
                    content = {
                        items(
                            notificationViewModel.state.value.notifications.size,
                            itemContent = { index ->
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp,
                                        )
                                        .clickable(onClick = {
                                            coroutineScope.launch {
                                                notificationViewModel
                                                    .onClickItem(notificationItems[index])
                                            }
                                        }),
                                    verticalAlignment = getVerticalAlignment(notificationItems[index]),
                                ) {
                                    when (val item = notificationItems[index]) {
                                        is NotificationItem.NewComment -> {
                                            NewCommentItemScreen(
                                                item,
                                                notificationViewModel::onClickItemTargetUser,
                                                notificationViewModel::onClickItemNewComment,
                                            )
                                        }

                                        is NotificationItem.NewHeart -> {
                                            NewHeartItemScreen(
                                                item,
                                                notificationViewModel::onClickItemTargetUser,
                                                notificationViewModel::onClickItemHeart,
                                            )
                                        }

                                        is NotificationItem.NewFollower -> {
                                            NewFollowerItemScreen(
                                                item,
                                                notificationViewModel::onClickItemTargetUser,
                                                notificationViewModel::onClickItemFollower,
                                                notificationViewModel::onClickItemFollowToggle,
                                            )
                                        }

                                        is NotificationItem.RequireWriteReview -> {
                                            RequireWriteReviewItemScreen(
                                                item,
                                                notificationViewModel::onClickItemDuckDeal,
                                                notificationViewModel::onClickItemReview,
                                            )
                                        }

                                        is NotificationItem.RequireChangeDealState -> {
                                            RequireChangeDealStateItemScreen(
                                                item,
                                                notificationViewModel::onClickItemDuckDeal,
                                                notificationViewModel::onClickItemDealFinish,
                                            )
                                        }

                                        is NotificationItem.RequireUpToDuckDeal -> {
                                            RequireUpToDuckDealItemScreen(
                                                item,
                                                notificationViewModel::onClickItemTargetUser,
                                                notificationViewModel::onClickItemSaleRequest,
                                            )
                                        }
                                    }
                                }
                            },
                        )
                    },
                )
            }
        }
    }
}

/** 각 [알림 항목][item] 에 맞는 [Alignment.Vertical] 값을 return 한다. */
private fun getVerticalAlignment(item: NotificationItem): Alignment.Vertical = when (item) {
    is NotificationItem.NewComment -> Alignment.Top
    is NotificationItem.NewFollower -> Alignment.CenterVertically
    is NotificationItem.NewHeart -> Alignment.Top
    is NotificationItem.RequireChangeDealState -> Alignment.Top
    is NotificationItem.RequireUpToDuckDeal -> Alignment.Top
    is NotificationItem.RequireWriteReview -> Alignment.Top
}

@Composable
private fun NewCommentItemScreen(
    item: NotificationItem.NewComment,
    onUserClick: suspend (NotificationItem) -> Unit,
    onNewCommentClick: suspend (NotificationItem.NewComment) -> Unit,
) = CoroutineScopeContent {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column {
        QuackAnnotatedBody2(
            text = item.title(values = arrayOf(item.targetUserId)),
            highlightTextPairs = persistentListOf(
                Pair(item.targetUserId) {
                    coroutineScope.launch { onUserClick(item) }
                },
                Pair(stringResource(id = R.string.notification_new_comment)) {
                    coroutineScope.launch { onNewCommentClick(item) }
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(4.dp))

        // 내용
        QuackBody2(text = item.description)

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(item.createdAt))
    }
}

@Composable
private fun NewHeartItemScreen(
    item: NotificationItem.NewHeart,
    onUserClick: suspend (NotificationItem) -> Unit,
    onHeartClick: suspend (NotificationItem.NewHeart) -> Unit,
) = CoroutineScopeContent {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column {
        QuackAnnotatedBody2(
            text = item.title(values = arrayOf(item.targetUserId)),
            highlightTextPairs = persistentListOf(
                Pair(item.targetUserId) {
                    coroutineScope.launch { onUserClick(item) }
                },
                Pair(stringResource(id = R.string.notification_heart)) {
                    coroutineScope.launch { onHeartClick(item) }
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(4.dp))

        // 내용
        QuackBody2(text = item.description)

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(item.createdAt))
    }
}

@Composable
private fun RowScope.NewFollowerItemScreen(
    item: NotificationItem.NewFollower,
    onUserClick: suspend (NotificationItem) -> Unit,
    onFollowClick: suspend (NotificationItem.NewFollower) -> Unit,
    onFollowToggleClick: suspend (NotificationItem.NewFollower) -> Unit,
) = CoroutineScopeContent {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column {
        QuackAnnotatedBody2(
            text = item.title(values = arrayOf(item.targetUserId)),
            highlightTextPairs = persistentListOf(
                Pair(item.targetUserId) {
                    coroutineScope.launch { onUserClick(item) }
                },

                Pair(stringResource(id = R.string.notification_follower)) {
                    coroutineScope.launch { onFollowClick(item) }
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(item.createdAt))
    }

    Spacer(Modifier.weight(1f))

    // 팔로우 버튼
    QuackToggleChip(
        text = stringResource(id = R.string.notification_follower),
        // TODO(riflockle7) 토글 동작이 안되는 듯함 (true 로 강제 값 주입해도 비활성화 색상이 나옴)
        selected = item.isFollowed,
        onClick = {
            coroutineScope.launch { onFollowToggleClick(item) }
        },
    )
}

@Composable
private fun RowScope.RequireWriteReviewItemScreen(
    item: NotificationItem.RequireWriteReview,
    onDuckDealClick: suspend (NotificationItem) -> Unit,
    onReviewClick: suspend (NotificationItem.RequireWriteReview) -> Unit,
) = CoroutineScopeContent {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column(modifier = Modifier.weight(1f)) {
        QuackAnnotatedBody2(
            text = item.title(values = arrayOf(item.duckDealTitle)),
            highlightTextPairs = persistentListOf(
                Pair("\"${item.duckDealTitle}\"") {
                    coroutineScope.launch { onDuckDealClick(item) }
                },
                Pair(stringResource(id = R.string.notification_review)) {
                    coroutineScope.launch { onReviewClick(item) }
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(item.createdAt))
    }

    // 상품 이미지
    QuackRoundImage(
        src = item.duckDealUrl, size = DpSize(40.dp, 40.dp)
    )
}

@Composable
private fun RowScope.RequireChangeDealStateItemScreen(
    item: NotificationItem.RequireChangeDealState,
    onDuckDealClick: suspend (NotificationItem) -> Unit,
    onDealFinishClick: suspend (NotificationItem.RequireChangeDealState) -> Unit,
) = CoroutineScopeContent {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column(modifier = Modifier.weight(1f)) {
        QuackAnnotatedBody2(
            text = item.title(values = arrayOf(item.duckDealTitle)),
            highlightTextPairs = persistentListOf(
                Pair("\"${item.duckDealTitle}\"") {
                    coroutineScope.launch { onDuckDealClick(item) }
                },
                Pair(stringResource(id = R.string.notification_deal_finish)) {
                    coroutineScope.launch { onDealFinishClick(item) }
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(item.createdAt))
    }

    // 상품 이미지
    QuackRoundImage(
        src = item.duckDealUrl, size = DpSize(40.dp, 40.dp)
    )
}

@Composable
private fun RowScope.RequireUpToDuckDealItemScreen(
    item: NotificationItem.RequireUpToDuckDeal,
    onUserClick: suspend (NotificationItem) -> Unit,
    onSaleRequestClick: suspend (NotificationItem.RequireUpToDuckDeal) -> Unit,
) = CoroutineScopeContent {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column(modifier = Modifier.weight(1f)) {
        QuackAnnotatedBody2(
            text = item.title(values = arrayOf(item.targetUserId)),
            highlightTextPairs = persistentListOf(
                Pair(item.targetUserId) {
                    coroutineScope.launch { onUserClick(item) }
                },
                Pair(stringResource(id = R.string.notification_sale_request)) {
                    coroutineScope.launch { onSaleRequestClick(item) }
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(item.createdAt))
    }

    // 상품 이미지
    QuackRoundImage(
        src = item.duckDealUrl, size = DpSize(40.dp, 40.dp)
    )
}

/**
 * [알림 목록 항목][NotificationItem] 화면에 보여줄 제목 문자열 값을 가져옵니다.
 *
 * @param values 추가로 들어갈 값 (ex. %s, %d 등) 목록
 *
 * @return 제목 문자열 값
 */
@Composable
private fun NotificationItem.title(vararg values: String): String = when (this) {
    is NotificationItem.NewComment -> stringResource(
        id = R.string.notification_title_new_comment,
        values.firstOrNull() ?: ""
    )

    is NotificationItem.NewHeart -> stringResource(
        id = R.string.notification_title_new_heart,
        values.firstOrNull() ?: ""
    )

    is NotificationItem.NewFollower -> stringResource(
        id = R.string.notification_title_new_follower,
        values.firstOrNull() ?: "",
    )

    is NotificationItem.RequireWriteReview -> stringResource(
        id = R.string.notification_title_require_write_review,
        values.firstOrNull() ?: "",
    )

    is NotificationItem.RequireChangeDealState -> stringResource(
        id = R.string.notification_title_require_change_deal_state,
        values.firstOrNull() ?: "",
    )

    is NotificationItem.RequireUpToDuckDeal -> stringResource(
        id = R.string.notification_title_require_up_to_duck_deal,
        values.firstOrNull() ?: "",
    )
}
