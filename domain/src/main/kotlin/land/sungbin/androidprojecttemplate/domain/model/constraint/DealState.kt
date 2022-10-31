@file:Suppress("unused")

package land.sungbin.androidprojecttemplate.domain.model.constraint

/** 거래 상태 */
enum class DealState(
    val index: Int,
    val description: String,
) {
    InProgress(
        index = 0,
        description = "거래중",
    ),
    Booking(
        index = 1,
        description = "예약중",
    ),
    Done(
        index = 2,
        description = "거래완료",
    ),
}
