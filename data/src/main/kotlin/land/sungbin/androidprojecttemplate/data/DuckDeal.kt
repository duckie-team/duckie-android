package land.sungbin.androidprojecttemplate.data

/**
 * 덕딜[DuckDeal] data class
 *
 * @param field 필드 값
 * @param writer 글쓴이
 * @param isDeleted 삭제 여부
 * @param isHide 숨기기
 * @param title 제목
 * @param content 본문
 * @param category 카테고리
 * @param tags 태그 목록
 * @param pictures 사진 목록
 * @param videos 영상 목록
 * @param price 가격
 * @param isDirect 직거래 여부
 * @param isCourier 택배거래 여부
 * @param transactionState [TransactionState] 거래 상태
 * @param salesLocation 직거래 판매 위치
 * @param createdAt 글 쓴 시간 (yyyy-MM-dd-hh-mm-ss)
 */
data class DuckDeal(
    val field: String,
    val writer: String,
    val isDeleted: Boolean,
    val isHide: Boolean,
    val title: String,
    val content: String,
    val category: Int,
    val tags: List<String>,
    val pictures: List<String>,
    val videos: List<String>,
    val price: Int,
    val isDirect: Boolean,
    val isCourier: Boolean,
    // TODO (riflockle7) serializer 로 int 와 대응하도록 처리 필요
    val transactionState: TransactionState,
    val salesLocation: String,
    // TODO (riflockle7) serializer 로 시간 관련 변수와 대응하도록 처리 필요
    val createdAt: String,
)

/** 거래 상태 */
enum class TransactionState {
    Trading,    // 거래중
    Reserving,  // 예약중
    Traded,     // 거래 완료
}