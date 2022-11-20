package team.duckie.app.android.domain.model

import team.duckie.app.domain.model.constraint.Users
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.model.util.PK
import team.duckie.app.domain.model.util.requireInput

/**
 * 팔로우 모델
 *
 * @param userId 팔로우 모델의 계정 아이디
 * @param followings 팔로잉 계정의 아이디 목록
 * @param followers 팔로워 계정의 아이디 목록
 * @param blocks 차단한 계정의 아이디 목록
 */
data class Follow(
    @PK val userId: String,
    @FK val followings: Users,
    @FK val followers: Users,
    @FK val blocks: Users,
) {
    init {
        requireInput(
            field = "accountId",
            value = userId,
        )
    }
}
