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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackCardImageRow
import team.duckie.quackquack.ui.component.QuackIconTextToggle
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackMultiLineTagRow
import team.duckie.quackquack.ui.component.QuackRoundImage
import team.duckie.quackquack.ui.component.QuackSimpleLabel
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun FeedHeader(
    @DrawableRes profile: Int,
    title: String,
    content: String,
    tagItems: List<String>,
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
            QuackMultiLineTagRow(
                items = tagItems,
                icon = QuackIcon.Close,
                onClickIcon = onTagClick,
                mainAxisSpacing = 8.dp,
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
                with(duckDealHolder) {
                    FeedLabel(dealState = dealState, description = dealState.description)
                }
                QuackTitle2(
                    text = duckDealHolder.price
                )
            }
            QuackBody2(
                text = with(duckDealHolder) {
                    getTradingMethodAndLocation(isDirectDealing, parcelable, location)
                }

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
                    onClick = {
                        feedHolder.onMoreClick(feedHolder.nickname)
                    },
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

data class FeedHolder(
    val profile: Any?,
    val nickname: String,
    val time: String,
    val content: String,
    val onMoreClick: (
        user: String,
    ) -> Unit,
    val commentCount: () -> String,
    val onClickComment: () -> Unit,
    val isLike: () -> Boolean,
    val likeCount: () -> String,
    val onClickLike: () -> Unit,
    val images: PersistentList<Any>? = null,
)

data class DuckDealHolder(
    val isDirectDealing: Boolean,
    val parcelable: Boolean,
    val price: String,
    val dealState: DealState,
    val location: String,
)

@Stable
private val ProfileSize = DpSize(
    width = 36.dp,
    height = 36.dp,
)

@Composable
internal fun FeedLabel(
    dealState: DealState,
    description: String,
) = when (dealState) {
    DealState.Booking -> {
        QuackSimpleLabel(
            text = description,
            active = true
        )
    }

    DealState.Done -> {
        QuackSimpleLabel(
            text = description,
            active = false
        )
    }

    else -> {}
}

@Composable
internal fun getTradingMethodAndLocation(
    isDirectDealing: Boolean,
    parcelable: Boolean,
    location: String,
) = stringResource(
    id = R.string.center_period_between_text,
    stringResource(id = getTradingMethodResourceId(isDirectDealing, parcelable)),
    location
)



private fun getTradingMethodResourceId(isDirectDealing: Boolean, parcelable: Boolean): Int {
    return if (isDirectDealing && parcelable) {
        R.string.both_direct_dealing_parcelable
    } else if (isDirectDealing) {
        R.string.parcelable
    } else {
        R.string.direct_dealing
    }
}

