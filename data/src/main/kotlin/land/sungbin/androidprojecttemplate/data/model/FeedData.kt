package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.common.ContentData
import land.sungbin.androidprojecttemplate.data.model.constraint.CategoriesData

internal data class FeedData(
    val id: String? = null,
    val writerId: String? = null,
    val type: Int? = null,
    val isDeleted: Boolean? = null,
    val isHidden: Boolean? = null,
    val content: ContentData? = null,
    val categories: CategoriesData? = null,
    val createdAt: String? = null,
    val title: String? = null,
    val price: Int? = null,
    val location: String? = null,
    val dealingMethod: List<Int>? = null,
    val parcelable: Boolean? = null,
    val dealState: Int? = null,
)
