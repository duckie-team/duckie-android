package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.common.ContentData

internal typealias DuckFeedCoreInformationData = List<String?>

internal data class ChatData(
    val id: String? = null,
    val chatRoomId: String? = null,
    val sender: String? = null,
    val type: Int? = null,
    val isDeleted: Boolean? = null,
    val isEdited: Boolean? = null,
    val content: ContentData? = null,
    val sentAt: String? = null,
    val duckFeedDatas: DuckFeedCoreInformationData? = null,
)
