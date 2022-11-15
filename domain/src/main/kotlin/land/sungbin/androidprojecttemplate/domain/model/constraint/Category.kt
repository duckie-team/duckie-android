@file:Suppress("unused")

package land.sungbin.androidprojecttemplate.domain.model.constraint

/** 덕질 분야 */
data class Category(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val popularTags: List<Tag>,
)
