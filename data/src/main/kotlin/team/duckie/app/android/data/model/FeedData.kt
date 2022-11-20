package team.duckie.app.android.data.model

import java.util.Date
import team.duckie.app.data.model.common.ContentData
import team.duckie.app.data.model.constraint.CategoriesData

internal data class FeedData(
    val id: String? = null,
    val writer_id: String? = null,
    val type: Int? = null,
    val is_delete: Boolean? = null,
    val is_hidden: Boolean? = null,
    val content: ContentData? = null,
    val categories: CategoriesData? = null,
    val create_at: String? = null,
    val title: String? = null,
    val price: Int? = null,
    val push_count: Int? = null,
    val lastest_push_at: Date? = null,
    val location: String? = null,
    val is_direct_dealing: Boolean? = null,
    val parcelable: Boolean? = null,
    val deal_state: Int? = null,
)
