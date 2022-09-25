package land.sungbin.androidprojecttemplate.data

/**
 * 사용자[User] data class
 *
 * @param accountEnabled 계정 사용가능 여부
 * @param nickname 닉네임
 * @param profileURL 프로필 사진
 * @param badges 유저 뱃지 번호 목록
 * @param likeCategory [LikeCategory] 좋아하는 분야
 * @param interestedTags 관심태그 목록
 * @param nonInterestedTags 관심없음 태그 목록
 * @param notificationTags 태그 알림 목록
 * @param tradePreferenceTags 거래 선호 태그 목록
 * @param collections 컬렉션
 * @param createdTime 계정 만든 시간 (yyyy-MM-dd-hh-mm-ss)
 * @param deletedTime 계정 삭제한 시간 (yyyy-MM-dd-hh-mm-ss)
 */
data class User(
    val accountEnabled: Boolean,
    val nickname: String,
    val profileURL: String,
    val badges: List<Int>,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val likeCategory: LikeCategory,
    val interestedTags: List<String>,
    val nonInterestedTags: List<String>,
    val notificationTags: List<String>,
    val tradePreferenceTags: List<String>,
    val collections: List<String>,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val createdTime: String,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val deletedTime: String,
)

/** 좋아하는 분야 */
enum class LikeCategory {
    Celebrities,    // 연예인
    Movie,          // 영화
    Animation,      // 만화/애니
    WebToon,        // 웹툰
    Game,           // 게임
    Military,       // 밀리터리
    IT,             // IT
}