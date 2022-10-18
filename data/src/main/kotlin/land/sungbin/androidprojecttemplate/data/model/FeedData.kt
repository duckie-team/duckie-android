package land.sungbin.androidprojecttemplate.data.model

import java.util.Date
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
    val pushCount: Int? = null,
    val latestPushAt: Date? = null,
    val location: String? = null,
    val isDirectDealing: Boolean? = null,
    val parcelable: Boolean? = null,
    val dealState: Int? = null,
)
