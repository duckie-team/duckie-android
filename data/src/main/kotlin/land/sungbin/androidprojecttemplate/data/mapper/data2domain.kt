@file:Suppress("ReplaceGetOrSet")

package land.sungbin.androidprojecttemplate.data.mapper

import java.text.SimpleDateFormat
import java.util.Locale
import land.sungbin.androidprojecttemplate.data.model.ChatData
import land.sungbin.androidprojecttemplate.data.model.ChatReadData
import land.sungbin.androidprojecttemplate.data.model.ChatRoomData
import land.sungbin.androidprojecttemplate.data.model.DuckFeedCoreInformationData
import land.sungbin.androidprojecttemplate.data.model.common.ContentData
import land.sungbin.androidprojecttemplate.domain.model.Chat
import land.sungbin.androidprojecttemplate.domain.model.ChatRead
import land.sungbin.androidprojecttemplate.domain.model.ChatRoom
import land.sungbin.androidprojecttemplate.domain.model.ChatRoomType
import land.sungbin.androidprojecttemplate.domain.model.ChatType
import land.sungbin.androidprojecttemplate.domain.model.DuckFeedCoreInformation
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category

private fun ContentData.toDomain() = Content(
    text = text.unwrap(
        field = "text",
    ),
    images = images.unwrap(
        field = "images",
    ),
    video = video,
)

private fun String.toDate() =
    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.KOREA)
        .parse(this)
        .unwrap(
            field = "date",
        )

private fun DuckFeedCoreInformationData.toDomain() = DuckFeedCoreInformation(
    images = images.unwrap(
        field = "images",
    ),
    title = title.unwrap(
        field = "title",
    ),
    price = price.unwrap(
        field = "price",
    ),
)

internal fun ChatData.toDomain() = Chat(
    id = id.unwrap(
        field = "id",
    ),
    chatRoomId = chatRoomId.unwrap(
        field = "chatRoomId",
    ),
    sender = sender.unwrap(
        field = "sender",
    ),
    type = ChatType.values().get(
        type.unwrap(
            field = "type",
        )
    ),
    isDeleted = isDeleted.unwrap(
        field = "isDeleted",
    ),
    isEdited = isEdited.unwrap(
        field = "isEdited",
    ),
    content = content.unwrap(
        field = "content",
    ).toDomain(),
    sentAt = sentAt.unwrap(
        field = "sentAt",
    ).toDate(),
    duckFeedData = duckFeedData.unwrap(
        field = "duckFeedData",
    ).toDomain(),
)

internal fun ChatReadData.toDomain() = ChatRead(
    chatRoomId = chatRoomId.unwrap(
        field = "chatRoomId",
    ),
    userId = userId.unwrap(
        field = "userId",
    ),
    lastestReadChatId = lastestReadChatId.unwrap(
        field = "lastestReadChatId",
    ),
)

internal fun ChatRoomData.toDomain() = ChatRoom(
    id = id.unwrap(
        field = "id",
    ),
    type = ChatRoomType.values().get(
        type.unwrap(
            field = "type",
        )
    ),
    coverImageUrl = coverImageUrl,
    name = name.unwrap(
        field = "name",
    ),
    categories = categories.unwrap(
        field = "categories",
    ).map { categoryIndex ->
        Category.values()[categoryIndex]
    },
    tags = tags,
)
