package land.sungbin.androidprojecttemplate.data.model

import land.sungbin.androidprojecttemplate.data.model.common.ContentData

internal data class CommentData(
    val id: String? = null,
    val parentId: String? = null,
    val userId: String? = null,
    val content: ContentData? = null,
    val createdAt: String? = null,
)
