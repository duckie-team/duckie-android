package team.duckie.app.android.data.model

import team.duckie.app.domain.model.constraint.Tag

data class CategoryData(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val popularTags: List<Tag>,
)
