package team.duckie.app.android.data.model

import team.duckie.app.data.model.constraint.CategoriesData
import team.duckie.app.data.model.constraint.TagsData

internal data class ChatRoomData(
    val id: String? = null,
    val type: Int? = null,
    val coverImageUrl: String? = null,
    val name: String? = null,
    val ownerId: String? = null,
    val categories: CategoriesData? = null,
    val tags: TagsData? = null,
)
