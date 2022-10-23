package land.sungbin.androidprojecttemplate.domain.usecase.update

import land.sungbin.androidprojecttemplate.domain.model.Comment
import land.sungbin.androidprojecttemplate.domain.repository.UpdateRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class DeleteCommentUseCase(
    private val repository: UpdateRepository,
) {
    /**
     * 특정 댓글을 조건에 관계 없이 무조건 삭제합니다.
     *
     * @param commentId 삭제할 [댓글의 아이디][Comment.id]
     * @return 삭제 결과.
     * 삭제 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다
     */
    suspend operator fun invoke(
        commentId: String,
    ) = runDuckApiCatching {
        repository.deleteComment(
            commentId = commentId,
        )
    }
}
