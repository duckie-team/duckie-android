package land.sungbin.androidprojecttemplate.data.domain

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class Category(
    val id: Int,
    val title: String,
    val imageUrl: String = "https://picsum.photos/id/237/200/300",
    val popularTags: PersistentList<Tag> = persistentListOf(
        Tag(id * 10 + 0, "마블"),
        Tag(id * 10 + 1, "피규어"),
        Tag(id * 10 + 2, "픽사"),
        Tag(id * 10 + 3, "아바타"),
        Tag(id * 10 + 4, "탑건"),
        Tag(id * 10 + 5, "해리포터"),
        Tag(id * 10 + 6, "반지의 제왕"),
        Tag(id * 10 + 7, "반지의 제왕"),
        Tag(id * 10 + 8, "반지의 제왕"),
        Tag(id * 10 + 9, "반지의 제왕")
    )
)