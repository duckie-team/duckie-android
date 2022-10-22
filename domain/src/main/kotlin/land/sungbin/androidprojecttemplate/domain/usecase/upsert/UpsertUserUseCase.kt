package land.sungbin.androidprojecttemplate.domain.usecase.upsert

import androidx.annotation.Size
import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.User
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.Tags
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.repository.DuckUpsertRepository
import land.sungbin.androidprojecttemplate.domain.repository.result.DuckApiResult
import land.sungbin.androidprojecttemplate.domain.repository.result.runDuckApiCatching

class UpsertUserUseCase(
    private val repository: DuckUpsertRepository,
) {
    /**
     * [유저][User] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param nickname 닉네임. **덕키에서는 유저 닉네임을 유저의
     * 고유 키로 사용합니다.** 즉, 유저 아이디는 이 값을 나타냅니다.
     * 이 값은 `User.id` 를 통해서도 가져올 수 있습니다.
     * @param accountAvailable 계정 사용 가능 여부
     * @param profileUrl 프로필 사진 주소.
     * 만약 프로필 사진이 설정되지 않았다면 null 을 받습니다.
     * @param likeCategories 좋아하는 분야 목록.
     * **최소 1개는 있어야 합니다.**
     * @param interestedTags 관심태그 목록
     * @param nonInterestedTags 관심없음 태그 목록
     * @param notificationTags 태그 알림 목록
     * @param tradePreferenceTags 거래 선호 태그 목록
     * @param createdAt 계정이 생성된 시간
     * @param deletedAt 계정이 삭제된 시간
     * @param bannedAt 계정이 정지된 시간
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @PK nickname: String,
        accountAvailable: Boolean,
        profileUrl: String?,
        @Size(min = 1) likeCategories: Categories,
        interestedTags: Tags,
        nonInterestedTags: Tags,
        notificationTags: Tags,
        tradePreferenceTags: Tags,
        createdAt: Date,
        deletedAt: Date?,
        bannedAt: Date?,
    ) = runDuckApiCatching {
        repository.upsertUser(
            user = User(
                nickname = nickname,
                accountAvailable = accountAvailable,
                profileUrl = profileUrl,
                likeCategories = likeCategories,
                interestedTags = interestedTags,
                nonInterestedTags = nonInterestedTags,
                notificationTags = notificationTags,
                tradePreferenceTags = tradePreferenceTags,
                createdAt = createdAt,
                deletedAt = deletedAt,
                bannedAt = bannedAt,
            ),
        )
    }
}
