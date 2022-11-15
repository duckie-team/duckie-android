package land.sungbin.androidprojecttemplate.domain.model.constraint

data class LikeCategory(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val popularTags: List<Tag>,
)