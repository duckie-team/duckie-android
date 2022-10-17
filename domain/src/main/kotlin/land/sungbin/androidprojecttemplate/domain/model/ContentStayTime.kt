package land.sungbin.androidprojecttemplate.domain.model

import androidx.annotation.IntRange
import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category
import land.sungbin.androidprojecttemplate.domain.model.util.requireInput
import land.sungbin.androidprojecttemplate.domain.model.util.requireSize

/**
 * 유저가 특정 컨텐츠를 보고 있는 시간 모델
 *
 * 모든 시간의 기준은 초를 사용합니다.
 *
 * @param userId [유저 아이디][User.nickname]
 * @param categories 각각 카테고리별 머문 시간.
 * [Categories] 타입이 아닌, **각각 카테고리별로 유저가 얼마나
 * 머물렀는지를 각각 인덱스로 받아야 합니다.**
 * 이용 가능한 카테고리는 [Category] 와 동일합니다.
 * @param search 검색 화면에 머문 시간
 * @param dm DM 화면에 머문 시간
 * @param notification 알림 화면에 머문 시간
 */
data class ContentStayTime(
    val userId: String,
    val categories: List<Int>,
    @IntRange(from = 0) val search: Int,
    @IntRange(from = 0) val dm: Int,
    @IntRange(from = 0) val notification: Int,
) {
    init {
        requireInput(
            field = "userId",
            value = userId,
        )
        requireSize(
            min = Category.values().size,
            max = Category.values().size,
            field = "categories",
            value = categories,
        )
        requireSize(
            min = 0,
            field = "search",
            value = search,
        )
        requireSize(
            min = 0,
            field = "dm",
            value = dm,
        )
        requireSize(
            min = 0,
            field = "notification",
            value = notification,
        )
    }
}
