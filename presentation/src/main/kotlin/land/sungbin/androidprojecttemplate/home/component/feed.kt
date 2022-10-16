package land.sungbin.androidprojecttemplate.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.domain.model.DealState
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackCardImageRow
import team.duckie.quackquack.ui.component.QuackIconTextToggle
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackRoundImage
import team.duckie.quackquack.ui.component.QuackRowTag
import team.duckie.quackquack.ui.component.QuackSimpleLabel
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import java.text.NumberFormat
import java.util.Locale


data class FeedHolder(
    val profile: Any?,
    val nickname: String,
    val time: String,
    val content: String,
    val onMoreClick: () -> Unit,
    val commentCount: () -> String,
    val onClickComment: () -> Unit,
    val isLike: () -> Boolean,
    val likeCount: () -> String,
    val onClickLike: () -> Unit,
    val images: PersistentList<Any>? = null,
)

data class DuckDealHolder(
    val tradingMethod: String,
    val price: String,
    val dealState: DealState,
    val location: String,
)

@Stable
private val ProfileSize = DpSize(
    width = 36.dp,
    height = 36.dp,
)


val dummyTags = persistentListOf(
    "디즈니", "픽사", "마블", "DC", "애니메이션", "지브리", "ost", "피규어"
)

@Composable
internal fun FeedHeader(
    @DrawableRes profile: Int,
    title: String,
    content: String,
    tagItems: PersistentList<String>,
    tagItemsSelection: List<Boolean>,
    onTagClick: (
        index: Int,
    ) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
        )
    ) {
        QuackImage(
            src = profile,
            overrideSize = DpSize(
                width = 36.dp,
                height = 36.dp
            )
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                )
            ) {
                QuackSubtitle2(
                    text = title,
                )
                QuackBody1(
                    text = content,
                    singleLine = false,
                )
            }
            QuackRowTag(
                items = tagItems,
                itemsSelection = tagItemsSelection,
                onClick = onTagClick,
            )
        }

    }
}

@Composable
internal fun HomeNormalFeed(
    feedHolder: FeedHolder, //TODO 태그 생기면 태그 추가
) {
    BaseHomeFeed(
        feedHolder = feedHolder
    )
}

@Composable
internal fun HomeDuckDealFeed(
    feedHolder: FeedHolder, //TODO 태그 생기면 태그 추가
    duckDealHolder: DuckDealHolder,
) {
    BaseHomeFeed(
        feedHolder = feedHolder
    ) {
        Column(
            modifier = Modifier.padding(
                top = 2.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 4.dp
                )
            ) {
                when (duckDealHolder.dealState) {
                    DealState.Booking -> {
                        QuackSimpleLabel(
                            text = duckDealHolder.dealState.description,
                            active = true
                        )
                    }

                    DealState.Done -> {
                        QuackSimpleLabel(
                            text = duckDealHolder.dealState.description,
                            active = false
                        )
                    }

                    else -> {}
                }
                QuackTitle2(
                    text = duckDealHolder.price
                )
            }
            QuackBody2(
                text = "${duckDealHolder.tradingMethod} · ${duckDealHolder.location}"
            )
        }
    }
}

@Composable
private fun BaseHomeFeed(
    feedHolder: FeedHolder,
    component: (@Composable () -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
        )
    ) {
        QuackRoundImage(
            src = feedHolder.profile,
            size = ProfileSize,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 4.dp,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 4.dp,
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuackSubtitle2(
                        text = feedHolder.nickname,
                    )
                    QuackBody3(
                        text = feedHolder.time,
                    )
                }
                QuackImage(
                    src = QuackIcon.More,
                    overrideSize = DpSize(
                        width = 16.dp,
                        height = 16.dp,
                    ),
                    tint = QuackColor.Gray1,
                    onClick = feedHolder.onMoreClick,
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                )
            ) {
                QuackBody1(
                    text = feedHolder.content,
                    singleLine = false,
                )
                if (component != null) {
                    component()
                }
                feedHolder.images?.let { list ->
                    if (list.isNotEmpty()) {
                        QuackCardImageRow(
                            images = list
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 24.dp,
                    )
                ) {
                    QuackIconTextToggle(
                        checkedIcon = null,
                        uncheckedIcon = QuackIcon.Comment,
                        checked = false,
                        text = feedHolder.commentCount(),
                        onToggle = feedHolder.onClickComment,
                    )
                    QuackIconTextToggle(
                        checkedIcon = QuackIcon.FilledHeart,
                        uncheckedIcon = QuackIcon.Heart,
                        checked = feedHolder.isLike(),
                        text = feedHolder.likeCount(),
                        onToggle = feedHolder.onClickLike,
                    )
                }
            }
        }
    }
}

fun getTradingMethod(isDirectDealing: Boolean, parcelable: Boolean): String {
    return if (isDirectDealing && parcelable) {
        "택배, 직거래"
    } else if (isDirectDealing) {
        "택배"
    } else {
        "직거래"
    }
}

fun Int.priceToString(): String = NumberFormat.getInstance(Locale.getDefault()).format(this)
