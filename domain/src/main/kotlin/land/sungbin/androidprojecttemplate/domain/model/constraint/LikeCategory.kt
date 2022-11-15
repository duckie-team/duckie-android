package land.sungbin.androidprojecttemplate.domain.model.constraint

data class LikeCategory(
    val id: Int,
    val title: String,
    val imageUrl: String = "https://picsum.photos/id/237/200/300",
    val popularTags: List<Tag>,
)