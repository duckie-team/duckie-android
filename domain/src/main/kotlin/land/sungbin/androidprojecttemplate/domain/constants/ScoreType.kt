package land.sungbin.androidprojecttemplate.domain.constants

/**
 * 덕키의 추천 시스템에 사용되는 점수의 종류를 나타냅니다.
 *
 * | 상황 | 점수 |
 * | --- | :---: |
 * | 피드에 떴을 때 | 0 |
 * | 피드를 클릭 안하고 그냥 읽었을 때 | 1 |
 * | 피드를 클릭해서 자세히 읽었을 때 | 2 |
 * | 좋아요 눌렀을 때 | 3 |
 * | 좋아요를 취소했을 때 | -3 |
 * | 댓글 작성 | 5 |
 * | 댓글 삭제 | -5 |
 * | 공유 | 5 |
 * | 관심 없음 | -3 |
 *
 * @property score 추천 시스템에 반영될 점수
 * @property description 해당 점수의 설명.
 * 주석 대응으로 사용됩니다.
 */
enum class ScoreType(
    val score: Int,
    private val description: String,
) {
    OnFeed(
        score = 0,
        description = "피드에 떴을 때",
    ),
    Read(
        score = 1,
        description = "피드를 클릭 안하고 그냥 읽었을 때",
    ),
    ReadDetail(
        score = 2,
        description = "피드를 클릭해서 자세히 읽었을 때",
    ),
    Like(
        score = 3,
        description = "좋아요 눌렀을 때",
    ),
    Dislike(
        score = -3,
        description = "좋아요를 취소했을 때",
    ),
    Comment(
        score = 5,
        description = "댓글 작성. 한 피드당 한 번만 요청되야 합니다.",
    ),
    DeleteComment(
        score = -5,
        description = "댓글 삭제",
    ),
    Share(
        score = 5,
        description = "공유",
    ),
    NotInterested(
        score = -3,
        description = "관심 없음",
    ),
}
