package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.Size
import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.constraint.Badges
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.Collections
import land.sungbin.androidprojecttemplate.domain.model.constraint.DuckTier
import land.sungbin.androidprojecttemplate.domain.model.constraint.Tags
import land.sungbin.androidprojecttemplate.domain.model.util.PK
import land.sungbin.androidprojecttemplate.domain.model.util.Unsupported
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireSize

/**
 * 유저 모델
 *
 * @param nickname 닉네임. **덕키에서는 유저 닉네임을 유저의
 * 고유 키로 사용합니다.** 즉, 유저 아이디는 이 값을 나타냅니다.
 * 이 값은 `User.id` 를 통해서도 가져올 수 있습니다.
 * @param accountAvailable 계정 사용 가능 여부
 * @param profileUrl 프로필 사진 주소.
 * 만약 프로필 사진이 설정되지 않았다면 null 을 받습니다.
 * @param tier 유저의 덕티어
 * @param badges 활성화된 배지 목록
 * @param likeCategories 좋아하는 분야 목록.
 * **최소 1개는 있어야 합니다.**
 * @param interestedTags 관심태그 목록
 * @param nonInterestedTags 관심없음 태그 목록
 * @param notificationTags 태그 알림 목록
 * @param tradePreferenceTags 거래 선호 태그 목록
 * @param collections 컬렉션 아이디 목록
 * @param createdAt 계정이 생성된 시간
 * @param deletedAt 계정이 삭제된 시간
 * @param bannedAt 계정이 정지된 시간
 */
data class User(
    @PK val nickname: String,
    val accountAvailable: Boolean,
    val profileUrl: String?,
    @property:Unsupported val tier: DuckTier? = null,
    @property:Unsupported val badges: Badges? = null,
    @Size(min = 1) val likeCategories: Categories,
    val interestedTags: Tags,
    val nonInterestedTags: Tags,
    val notificationTags: Tags,
    val tradePreferenceTags: Tags,
    @property:Unsupported val collections: Collections? = null,
    val createdAt: Date,
    val deletedAt: Date?,
    val bannedAt: Date?,
) {
    init {
        requireInput(
            field = "nickname",
            value = nickname,
        )
        requireSize(
            min = 1,
            field = "likeCategories",
            value = likeCategories,
        )
    }
}

/**
 * [유저][User] 의 아이디를 가져옵니다.
 * **덕키에서는 유저 닉네임을 유저의 고유 키(아이디)로 사용합니다.**
 * 따라서 `User.nickname` 을 델리게이트 합니다.
 * 단지 아이디를 도메인적으로 나타내기 위한 extension 입니다.
 * 이를 [유저][User] 모델에 넣게 되면 직렬화되면서 이 값이 같이 포함되기 때문에
 * 중복되는 필드를 없애기 위해 별도 extension 으로 분리하였습나다.
 *
 * @see User.nickname
 */
val User.id get() = nickname
