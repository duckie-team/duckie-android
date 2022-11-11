package land.sungbin.androidprojecttemplate.ui.main.notification

import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.ui.main.home.component.DrawerContent
import land.sungbin.androidprojecttemplate.ui.main.home.component.dummyDate
import land.sungbin.androidprojecttemplate.util.DateUtil
import team.duckie.quackquack.ui.component.QuackAnnotatedBody2
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackModalDrawer
import team.duckie.quackquack.ui.component.QuackRoundImage
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.component.rememberQuackDrawerState
import team.duckie.quackquack.ui.icon.QuackIcon
import java.util.Date

val notificationItems = listOf(
    NotificationItemType.NewComment(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
        targetUserId = "targetUserId",
        feedId = "feedId",
    ),
    NotificationItemType.NewHeart(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
        targetUserId = "targetUserId",
        feedId = "feedId",
    ),
    NotificationItemType.NewFollower(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = dummyDate(),
        targetUserId = "덕통사고",
        isFollowed = true,
        followBtnClick = { Log.i("riflockle7", "팔로우 버튼 클릭") },
    ),
    NotificationItemType.RequireWriteReview(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        duckDealTitle = "곰돌이 푸 파우치",
        duckDealUrl = R.drawable.ic_launcher_background,
    ),
    NotificationItemType.RequireChangeDealState(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        duckDealTitle = "어쩌구 제품...",
        duckDealUrl = R.drawable.ic_launcher_background,
    ),
    NotificationItemType.RequireUpToDuckDeal(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        targetUserId = "우주사령관",
        duckDealUrl = R.drawable.ic_launcher_background,
    ),
    NotificationItemType.NewComment(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
        targetUserId = "targetUserId",
        feedId = "feedId",
    ),
    NotificationItemType.NewHeart(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        description = "글 뭐라써야하지 모르겠는걸 아 갑자기 초코우유 먹고 싶다.",
        targetUserId = "targetUserId",
        feedId = "feedId",
    ),
    NotificationItemType.NewFollower(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        targetUserId = "덕통사고",
        isFollowed = true,
        followBtnClick = { Log.i("riflockle7", "팔로우 버튼 클릭") },
    ),
    NotificationItemType.RequireWriteReview(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        duckDealTitle = "곰돌이 푸 파우치",
        duckDealUrl = R.drawable.ic_launcher_background,
    ),
    NotificationItemType.RequireChangeDealState(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        duckDealTitle = "어쩌구 제품...",
        duckDealUrl = R.drawable.ic_launcher_background,
    ),
    NotificationItemType.RequireUpToDuckDeal(
        profileUrl = R.drawable.ic_launcher_background,
        createdAt = Date(),
        targetUserId = "우주사령관",
        duckDealUrl = R.drawable.ic_launcher_background,
    ),
)

@Composable
internal fun NotificationScreen() {
    val drawerState = rememberQuackDrawerState()
    val coroutineScope = rememberCoroutineScope()
    val swipeRefreshState = rememberSwipeRefreshState(false)

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
                onRefresh = { Log.i("riflockle7", "새로고침 swipe refresh") },
            ) {
                LazyColumn(
                    content = {
                        items(
                            notificationItems.size,
                            itemContent = { index ->
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 12.dp,
                                            horizontal = 16.dp
                                        )
                                        .clickable(onClick = notificationItems[index].onClick),
                                    verticalAlignment = getVerticalAlignment(notificationItems[index]),
                                ) {
                                    when (val item = notificationItems[index]) {
                                        is NotificationItemType.NewComment -> NewCommentItemScreen(
                                            item
                                        )

                                        is NotificationItemType.NewHeart -> NewHeartItemScreen(item)
                                        is NotificationItemType.NewFollower -> NewFollowerItemScreen(
                                            item
                                        )

                                        is NotificationItemType.RequireWriteReview -> {
                                            RequireWriteReviewItemScreen(item)
                                        }

                                        is NotificationItemType.RequireChangeDealState -> {
                                            RequireChangeDealStateItemScreen(item)
                                        }

                                        is NotificationItemType.RequireUpToDuckDeal -> {
                                            RequireUpToDuckDealItemScreen(item)
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

/** 각 [알림 목록의 타입][itemType] 에 맞는 [Alignment.Vertical] 값을 return 한다. */
private fun getVerticalAlignment(
    itemType: NotificationItemType
): Alignment.Vertical = when (itemType) {
    is NotificationItemType.NewComment -> Alignment.Top
    is NotificationItemType.NewFollower -> Alignment.CenterVertically
    is NotificationItemType.NewHeart -> Alignment.Top
    is NotificationItemType.RequireChangeDealState -> Alignment.Top
    is NotificationItemType.RequireUpToDuckDeal -> Alignment.Top
    is NotificationItemType.RequireWriteReview -> Alignment.Top
}

@Composable
private fun NewCommentItemScreen(itemType: NotificationItemType.NewComment) {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column {
        QuackAnnotatedBody2(
            text = "${itemType.targetUserId}님이 님이 내 글에 새 댓글을 달았어요.",
            highlightTextPairs = persistentListOf(
                Pair(itemType.targetUserId) {
                    Log.i("riflockle7", "${itemType.targetUserId} 클릭")
                },
                Pair("새 댓글") {
                    Log.i("riflockle7", "새 댓글 클릭")
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(4.dp))

        // 내용
        QuackBody2(text = itemType.description)

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(itemType.createdAt))
    }
}

@Composable
private fun NewHeartItemScreen(itemType: NotificationItemType.NewHeart) {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column {
        QuackAnnotatedBody2(
            text = "${itemType.targetUserId}님이 내 글에 좋아요을 눌렀어요.",
            highlightTextPairs = persistentListOf(
                Pair(itemType.targetUserId) {
                    Log.i("riflockle7", "${itemType.targetUserId} 클릭")
                },
                Pair("좋아요") {
                    Log.i("riflockle7", "좋아요 클릭")
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(4.dp))

        // 내용
        QuackBody2(text = itemType.description)

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(itemType.createdAt))
    }
}

@Composable
private fun RowScope.NewFollowerItemScreen(itemType: NotificationItemType.NewFollower) {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column {
        QuackAnnotatedBody2(
            text = "${itemType.targetUserId}님이 나를 팔로우 했어요.",
            highlightTextPairs = persistentListOf(
                Pair(itemType.targetUserId) {
                    Log.i("riflockle7", "${itemType.targetUserId} 클릭")
                },
                Pair("팔로우") {
                    Log.i("riflockle7", "팔로우 클릭")
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(itemType.createdAt))
    }

    Spacer(Modifier.weight(1f))

    // 팔로우 버튼
    QuackToggleChip(
        text = "팔로우",
        selected = itemType.isFollowed,
        onClick = {
            itemType.followBtnClick(itemType.isFollowed)
        },
    )
}

@Composable
private fun RowScope.RequireWriteReviewItemScreen(
    itemType: NotificationItemType.RequireWriteReview
) {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column(modifier = Modifier.weight(1f)) {
        QuackAnnotatedBody2(
            text = "\"${itemType.duckDealTitle}\" 어떠셨나요? 구매 후기를 남겨보세요.",
            highlightTextPairs = persistentListOf(
                Pair("\"${itemType.duckDealTitle}\"") {
                    Log.i("riflockle7", "\"${itemType.duckDealTitle}\" 클릭")
                },
                Pair("구매 후기") {
                    Log.i("riflockle7", "구매 후기 클릭")
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(itemType.createdAt))
    }

    // 상품 이미지
    QuackRoundImage(
        src = itemType.duckDealUrl, size = DpSize(40.dp, 40.dp)
    )
}

@Composable
private fun RowScope.RequireChangeDealStateItemScreen(
    itemType: NotificationItemType.RequireChangeDealState
) {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column(modifier = Modifier.weight(1f)) {
        QuackAnnotatedBody2(
            text = "\"${itemType.duckDealTitle}\"를 하셨나요? 거래가 끝났다면 거래완료로 변경해주세요.",
            highlightTextPairs = persistentListOf(
                Pair("\"${itemType.duckDealTitle}\"") {
                    Log.i("riflockle7", "\"${itemType.duckDealTitle}\" 클릭")
                },
                Pair("거래완료") {
                    Log.i("riflockle7", "거래완료 클릭")
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(itemType.createdAt))
    }

    // 상품 이미지
    QuackRoundImage(
        src = itemType.duckDealUrl, size = DpSize(40.dp, 40.dp)
    )
}

@Composable
private fun RowScope.RequireUpToDuckDealItemScreen(
    itemType: NotificationItemType.RequireUpToDuckDeal
) {
    // 프로필 이미지
    QuackRoundImage(
        src = R.drawable.ic_launcher_background, size = DpSize(36.dp, 36.dp)
    )

    // 공백
    Spacer(modifier = Modifier.width(8.dp))

    // 우측 영역
    Column(modifier = Modifier.weight(1f)) {
        QuackAnnotatedBody2(
            text = "${itemType.targetUserId}님이 내 피드에 판매요청을 했어요.",
            highlightTextPairs = persistentListOf(
                Pair(itemType.targetUserId) {
                    Log.i("riflockle7", "${itemType.targetUserId} 클릭")
                },
                Pair("판매요청") {
                    Log.i("riflockle7", "판매요청 클릭")
                }
            )
        )

        // 공백
        Spacer(modifier = Modifier.height(8.dp))

        // 생성일
        QuackBody2(text = DateUtil.formatTimeString(itemType.createdAt))
    }

    // 상품 이미지
    QuackRoundImage(
        src = itemType.duckDealUrl, size = DpSize(40.dp, 40.dp)
    )
}


/**
 * 알림 목록의 타입
 * 각 타입의 공통된 데이터를 하나로 묶어 처리함
 *
 * @param profileUrl 프로필 URL
 * @param createdAt 작성일자
 * @param onClick 알림 항목 클릭 이벤트
 */
sealed class NotificationItemType(
    open val profileUrl: Any,
    open val createdAt: Date,
    // TODO(riflockle7) 추후 viewModel 에서 처리 예정
    open val onClick: () -> Unit = {},
) {
    /**
     * 타 유저가, 내 글에 댓글을 달음
     *
     * @param description 내용
     * @param targetUserId 타 유저 id
     * @param feedId 이동할 피드 id
     */
    data class NewComment(
        override val profileUrl: Any,
        override val createdAt: Date,
        val description: String,
        val targetUserId: String,
        val feedId: String,
    ) : NotificationItemType(profileUrl, createdAt)

    /**
     * 타 유저가, 내 글에 좋아요를 누름
     *
     * @param description 내용
     * @param targetUserId 타 유저 id
     * @param feedId 이동할 피드 id
     */
    data class NewHeart(
        override val profileUrl: Any,
        override val createdAt: Date,
        val description: String,
        val targetUserId: String,
        val feedId: String,
    ) : NotificationItemType(profileUrl, createdAt)

    /**
     * 타 유저가, 나를 팔로우함
     *
     * @param targetUserId 타 유저 id
     * @param isFollowed 팔로우 여부
     * @param followBtnClick 팔로우 버튼 클릭 이벤트
     */
    data class NewFollower(
        override val profileUrl: Any,
        override val createdAt: Date,
        val targetUserId: String,
        val isFollowed: Boolean,
        val followBtnClick: (Boolean) -> Unit,
    ) : NotificationItemType(profileUrl, createdAt)

    /**
     * 서비스가, 내가 구매한 물품에 거래 후기를 남기라고 요청함
     *
     * @param duckDealTitle 덕딜 상품 이름
     * @param duckDealUrl 덕딜 상품 URL
     */
    data class RequireWriteReview(
        override val profileUrl: Any,
        override val createdAt: Date,
        val duckDealTitle: String,
        val duckDealUrl: Any,
        // TODO(riflockle7) 어떤 동작을 하는지에 따라 필요한 데이터 추가 필요
    ) : NotificationItemType(profileUrl, createdAt)

    /**
     * 서비스가, 예약중인 상태의 상품을 거래 완료로 바꾸라고 요청함
     *
     * @param duckDealTitle 덕딜 상품 이름
     * @param duckDealUrl 덕딜 상품 URL
     */
    data class RequireChangeDealState(
        override val profileUrl: Any,
        override val createdAt: Date,
        val duckDealTitle: String,
        val duckDealUrl: Any,
        // TODO(riflockle7) 어떤 동작을 하는지에 따라 필요한 데이터 추가 필요
    ) : NotificationItemType(profileUrl, createdAt)

    /**
     * 타 유저가, 내 피드에 판매 요청을 함
     *
     * @param targetUserId 타 유저 id
     * @param duckDealUrl 덕딜 상품 URL
     */
    data class RequireUpToDuckDeal(
        override val profileUrl: Any,
        override val createdAt: Date,
        val targetUserId: String,
        val duckDealUrl: Any,
        // TODO(riflockle7) 어떤 동작을 하는지에 따라 필요한 데이터 추가 필요
    ) : NotificationItemType(profileUrl, createdAt)
}