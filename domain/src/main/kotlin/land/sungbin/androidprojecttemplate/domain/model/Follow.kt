package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.Users
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput

/**
 * 팔로우 모델
 *
 * @param accountId 팔로우 모델의 계정 아이디
 * @param followings 팔로잉 계정의 아이디 목록
 * @param followers 팔로워 계정의 아이디 목록
 * @param blocks 차단한 계정의 아이디 목록
 */
data class Follow(
    val accountId: String,
    val followings: Users,
    val followers: Users,
    val blocks: Users,
) {
    init {
        requireInput(
            field = "accountId",
            value = accountId,
        )
    }
}
