package team.duckie.app.android.domain.model.constraint

data class LikeCategory(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val popularTags: List<Tag>,
)
