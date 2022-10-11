package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.common.ContentData

internal data class ChatData(
    val id: String? = null,
    val chatRoomId: String? = null,
    val sender: String? = null,
    val type: Int? = null,
    val isDeleted: Boolean? = null,
    val isEdited: Boolean? = null,
    val content: ContentData? = null,
    val sentAt: String? = null,
    val duckFeedData: DuckFeedCoreInformationData? = null,
)

internal data class DuckFeedCoreInformationData(
    val images: List<String>? = null,
    val title: String? = null,
    val price: Int? = null,
)

