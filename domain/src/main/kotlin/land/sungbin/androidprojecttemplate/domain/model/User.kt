package land.sungbin.androidprojecttemplate.domain.model

import java.util.Date
import land.sungbin.androidprojecttemplate.domain.model.constraint.Badges
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.Collections
import land.sungbin.androidprojecttemplate.domain.model.constraint.Tags

/**
 * 유저 모델
 *
 * @param nickname 닉네임. **덕키에서는 유저 닉네임을
 * 유저의 고유 키로 사용합니다.** 즉, 유저 아이디는
 * 이 값을 나타냅니다.
 * @param accountAvailable 계정 사용 가능 여부
 * @param profileUrl 프로필 사진 주소
 * @param badges 활성화된 배지 목록
 * @param likeCategories 좋아하는 분야 목록
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
    val nickname: String,
    val accountAvailable: Boolean,
    val profileUrl: String,
    val badges: Badges,
    val likeCategories: Categories,
    val interestedTags: Tags,
    val nonInterestedTags: Tags,
    val notificationTags: Tags,
    val tradePreferenceTags: Tags,
    val collections: Collections,
    val createdAt: Date,
    val deletedAt: Date?,
    val bannedAt: Date?,
)
