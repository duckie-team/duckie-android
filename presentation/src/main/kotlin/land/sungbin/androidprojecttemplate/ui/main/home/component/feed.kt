package land.sungbin.androidprojecttemplate.ui.main.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import land.sungbin.androidprojecttemplate.R
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.ui.main.home.dto.FeedDTO
import land.sungbin.androidprojecttemplate.util.DateUtil
import land.sungbin.androidprojecttemplate.util.IntUtil.priceToString
import land.sungbin.androidprojecttemplate.util.IntUtil.toUnitString
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
import java.util.Date

@Composable
internal fun FeedHeader(
    tagItems: List<String>,
    onTagClick: (
        index: Int,
    ) -> Unit,
) {
    Box {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            )
        ) {
            QuackImage(
                src = R.drawable.duckie_profile,
                overrideSize = ProfileSize
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
                        text = stringResource(id = R.string.duckie_name),
                    )
                    QuackBody1(
                        text = stringResource(id = R.string.duckie_introduce),
                        singleLine = false,
                    )
                }
                QuackMultiLineTagRow(
                    items = tagItems,
                    icon = QuackIcon.Close,
                    onClickIcon = onTagClick,
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                )
            }

        }
    }
}

@Composable
internal fun NormalFeed(
    normalFeed: FeedDTO.Normal,
    onClickMoreIcon: (selectedUser: String) -> Unit,
    onClickHeartIcon: (
        feedId: String,
        isHearted: Boolean,
    ) -> Unit,
    onClickCommentIcon: () -> Unit,
) = with(normalFeed) {
    BasicFeed(
        feedId = feedId,
        profileUrl = writerId,
        nickname = writerId,
        createdAt = createdAt,
        isHearted = isHearted,
        heartCount = heartCount,
        commentCount = commentCount,
        onClickHeartIcon = onClickHeartIcon,
        onClickCommentIcon = onClickCommentIcon,
        onClickMoreIcon = onClickMoreIcon,
    ) {
        NormalFeedContent(content = content)
    }
}

@Composable
internal fun DuckDealFeed(
    duckDealFeed: FeedDTO.DuckDeal,
    onClickMoreIcon: (selectedUser: String) -> Unit,
    onClickHeartIcon: (
        feedId: String,
        isHearted: Boolean,
    ) -> Unit,
    onClickCommentIcon: () -> Unit,
) = with(duckDealFeed) {
    BasicFeed(
        feedId = feedId,
        profileUrl = writerId,
        nickname = writerId,
        createdAt = createdAt,
        isHearted = isHearted,
        heartCount = heartCount,
        commentCount = commentCount,
        onClickMoreIcon = onClickMoreIcon,
        onClickHeartIcon = onClickHeartIcon,
        onClickCommentIcon = onClickCommentIcon,
    ) {
        DuckDealFeedContent(
            content = content,
            title = title,
            dealState = dealState,
            price = price,
            location = location,
            isDirectDealing = isDirectDealing,
            parcelable = parcelable,
        )
    }
}

@Composable
internal fun NormalFeedContent(
    content: Content,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        if (content.text.isNotEmpty()) {
            QuackBody1(text = content.text)
        }
        QuackCardImageRow(images = content.images.toPersistentList())
    }
}

@Composable
internal fun DuckDealFeedContent(
    content: Content,
    title: String,
    dealState: DealState,
    price: Int,
    location: String,
    isDirectDealing: Boolean,
    parcelable: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        QuackBody1(text = title)
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FeedLabel(
                dealState = dealState,
                description = dealState.description
            )
            QuackTitle2(
                text = price.priceToString()
            )
        }
        if (content.images.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            QuackCardImageRow(images = content.images.toPersistentList())
        }
        Spacer(modifier = Modifier.height(6.dp))
        QuackBody2(
            text = when (isDirectDealing) {
                true -> {
                    stringResource(
                        R.string.center_period_between_text,
                        if (parcelable) {
                            stringResource(id = R.string.both_direct_dealing_parcelable)
                        } else {
                            stringResource(id = R.string.direct_dealing)
                        },
                        location
                    )
                }

                false -> stringResource(id = R.string.parcelable)
            }
        )
    }
}

@Composable
internal fun DuckDealCardContent(
    title: String,
    dealState: DealState,
    price: String,
    dealMethodAndLocation: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        QuackBody1(text = title)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FeedLabel(
                dealState = dealState,
                description = dealState.description
            )
            QuackTitle2(
                text = price
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        QuackBody2(text = dealMethodAndLocation)
    }
}

@Composable
private fun BasicFeed(
    feedId: String,
    profileUrl: String,
    nickname: String,
    createdAt: Date,
    isHearted: Boolean,
    heartCount: Int,
    commentCount: Int,
    onClickHeartIcon: (
        feedId: String,
        isHearted: Boolean,
    ) -> Unit,
    onClickCommentIcon: () -> Unit,
    onClickMoreIcon: (selectedUser: String) -> Unit,
    feedContent: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuackRoundImage(
            src = profileUrl,
            size = ProfileSize,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuackSubtitle2(text = nickname)
                    QuackBody3(text = DateUtil.formatTimeString(createdAt))
                }
                QuackImage(
                    src = QuackIcon.More,
                    overrideSize = MoreIconSize,
                    tint = QuackColor.Gray1,
                    onClick = {
                        onClickMoreIcon(nickname)
                    },
                )
            }
            feedContent()
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                QuackIconTextToggle(
                    checkedIcon = null,
                    uncheckedIcon = QuackIcon.Comment,
                    checked = false,
                    text = commentCount.toUnitString(),
                    onToggle = { onClickCommentIcon() }
                )
                QuackIconTextToggle(
                    checkedIcon = QuackIcon.FilledHeart,
                    uncheckedIcon = QuackIcon.Heart,
                    checked = isHearted,
                    text = heartCount.toUnitString(),
                    onToggle = {
                        onClickHeartIcon(feedId, it)
                    },
                )
            }
        }
    }
}

@Stable
private val ProfileSize = DpSize(
    width = 36.dp,
    height = 36.dp,
)

@Stable
private val MoreIconSize = DpSize(
    width = 16.dp,
    height = 16.dp,
)


@Composable
private fun FeedLabel(
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
