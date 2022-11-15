package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.Tag

data class LikeCategoryData(
    val id: Int,
    val title: String,
    val imageUrl: String = "https://picsum.photos/id/237/200/300",
    val popularTags: List<Tag> = listOf(
        Tag(id * 10 + 0, "마블"),
        Tag(id * 10 + 1, "피규어"),
        Tag(id * 10 + 2, "픽사"),
        Tag(id * 10 + 3, "아바타"),
        Tag(id * 10 + 4, "탑건"),
        Tag(id * 10 + 5, "해리포터"),
        Tag(id * 10 + 6, "반지의 제왕"),
    ),
)