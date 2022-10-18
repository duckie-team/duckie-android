package land.sungbin.androidprojecttemplate.domain.model

import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.common.Content
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput

/**
 * 댓글 모델
 *
 * @param id 댓글의 아이디
 * @param parentId 상위 [댓글의 아이디][Comment.id].
 * 대댓글일 경우에만 유효하며, 대댓글이 없을 경우 null 을 받습니다.
 * @param ownerId 댓글을 작성한 [유저의 아이디][User.nickname]
 * @param content 댓글의 내용
 * @param createdAt 댓글이 작성된 시간
 */
data class Comment(
    @PK val id: String,
    @Unsupported @FK val parentId: String? = null,
    @FK val ownerId: String,
    val content: Content,
    val createdAt: Date,
) {
    init {
        requireInput(
            field = "id",
            value = id,
        )
        requireInput(
            field = "userId",
            value = ownerId,
        )
    }
}
