package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.Tag

data class CategoryData(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val popularTags: List<Tag>,
)