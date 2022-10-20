package land.sungbin.androidprojecttemplate.home.component

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
internal fun FeedHeader(
    tagItems: List<String>,
    onTagClick: (
        index: Int,
    ) -> Unit,
) {
    Box(
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 24.dp,
            start = 16.dp,
            end = 16.dp,
        )
    ) {
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

/*
@Composable
internal fun HomeNormalFeed(
    feedHolder: FeedHolder, //TODO 태그 생기면 태그 추가
) {
    NormalFeed(

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
*/
@Composable
internal fun NormalFeed(
    profileUrl: String,
    nickname: String,
    createdAt: String,
    content: Content = Content(
        "버즈 라이트",
        images = listOf()
    ),
) = BasicFeed(
    profileUrl = profileUrl,
    nickname = nickname,
    createdAt = createdAt,
) {
    NormalFeedContent(content = content)
}

@Composable
internal fun DuckDealFeed(
    profileUrl: String,
    nickname: String,
    createdAt: String,
    content: Content = Content(
        "버즈 라이트",
        images = listOf("https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80","https://tedblob.com/wp-content/uploads/2021/09/android.png",)
    ),
    title: String = "제목",
    dealState: DealState = DealState.Booking,
    price: String = "30,000 원",
    dealMethodAndLocation: String = "택배, 직거래 - 마포구 도화동"
) = BasicFeed(
    profileUrl = profileUrl,
    nickname = nickname,
    createdAt = createdAt,
) {
    DuckDealFeedContent(
        content = content,
        title = title,
        dealState = dealState,
        price = price,
        dealMethodAndLocation = dealMethodAndLocation
    )
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
    price: String,
    dealMethodAndLocation: String,
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
                text = price
            )
        }
        if(content.images.isNotEmpty()){
            Spacer(modifier = Modifier.height(6.dp))
            QuackCardImageRow(images = content.images.toPersistentList())
        }
        Spacer(modifier = Modifier.height(6.dp))
        QuackBody2(text = dealMethodAndLocation)
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
internal fun CommentAndHeart(
    commentCount: String,
    heartCount: String,
    heartChecked: Boolean,
    heartOnToggle: (Boolean) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        QuackIconTextToggle(
            checkedIcon = null,
            uncheckedIcon = QuackIcon.Comment,
            checked = false,
            text = commentCount,
            onToggle = {}
        )
        QuackIconTextToggle(
            checkedIcon = QuackIcon.FilledHeart,
            uncheckedIcon = QuackIcon.Heart,
            checked = heartChecked,
            text = heartCount,
            onToggle = heartOnToggle
        )
    }
}

@Composable
private fun BasicFeed(
    profileUrl: String,
    nickname: String,
    createdAt: String,
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
                    QuackBody3(text = createdAt)
                }
                QuackImage(
                    src = QuackIcon.More,
                    overrideSize = MoreIconSize,
                    tint = QuackColor.Gray1,
                    onClick = {

                    },
                )
            }
            feedContent()
            CommentAndHeart(
                commentCount = "100",
                heartCount = "100",
                heartChecked = true,
                heartOnToggle = {

                }
            )
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
