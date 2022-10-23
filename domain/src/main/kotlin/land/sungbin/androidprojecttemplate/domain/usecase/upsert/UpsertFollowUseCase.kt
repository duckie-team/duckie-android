package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import land.sungbin.androidprojecttemplate.domain.model.Follow
import land.sungbin.androidprojecttemplate.domain.model.constraint.Users
import land.sungbin.androidprojecttemplate.domain.model.util.FK
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.UpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching


class UpsertFollowUseCase(
    private val repository: UpsertRepository,
) {
    /**
     * [팔로우][Follow] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param userId 팔로우 모델의 계정 아이디
     * @param followings 팔로잉 계정의 아이디 목록
     * @param followers 팔로워 계정의 아이디 목록
     * @param blocks 차단한 계정의 아이디 목록
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @PK userId: String,
        @FK followings: Users,
        @FK followers: Users,
        @FK blocks: Users,
    ) = runDuckApiCatching {
        repository.upsertFollow(
            follow = Follow(
                userId = userId,
                followings = followings,
                followers = followers,
                blocks = blocks,
            )
        )
    }
}
