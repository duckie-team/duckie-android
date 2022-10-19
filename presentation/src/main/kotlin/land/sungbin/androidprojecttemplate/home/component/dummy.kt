package land.sungbin.androidprojecttemplate.home.component

import land.sungbin.androidprojecttemplate.domain.model.Feed
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category
import land.sungbin.androidprojecttemplate.domain.model.constraint.DealState
import land.sungbin.androidprojecttemplate.domain.model.constraint.FeedType
import java.util.Date

val dummyTags = listOf(
    "디즈니", "픽사", "마블", "DC", "애니메이션", "지브리", "ost", "피규어","카툰네트워크"
)

val dummyFeeds = listOf(
    Feed(
        id = "a",
        writerId = "b",
        type = FeedType.Normal,
        isDeleted = false,
        isHidden = false,
        content = Content(
            text = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                    "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                    "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
            images = emptyList(),
            video = null,
        ),
        categories = listOf(Category.Celebrity),
        createdAt = Date(),
        title = null,
        price = null,
        location = null,
        isDirectDealing = null,
        parcelable = null,
        dealState = null
    ),
    Feed(
        id = "c",
        writerId = "d",
        type = FeedType.DuckDeal,
        isDeleted = false,
        isHidden = false,
        content = Content(
            text = "버즈 라이트이어 개봉 앞둔 기념!\n" +
                    "내 보물들 1일 1자랑 해야지ㅋㅋㅋ 개봉날 무조건\n" +
                    "오픈런 할거임 굿즈 많이 나왔음 좋겠당",
            images = emptyList(),
            video = null,
        ),
        categories = listOf(Category.Celebrity),
        createdAt = Date(),
        title = "덕딜 상품 제목",
        price = 30000,
        location = "마포구 도화동",
        isDirectDealing = true,
        parcelable = true,
        dealState = DealState.Booking
    )
)