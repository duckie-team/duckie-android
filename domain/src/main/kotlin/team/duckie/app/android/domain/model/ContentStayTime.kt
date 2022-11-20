package team.duckie.app.android.domain.model

import androidx.annotation.IntRange
import team.duckie.app.domain.model.constraint.Categories
import team.duckie.app.domain.model.constraint.Category
import team.duckie.app.domain.model.util.Unsupported
import team.duckie.app.domain.model.util.requireInput
import team.duckie.app.domain.model.util.requireRange
import team.duckie.app.domain.model.util.requireSize

/**
 * 유저가 특정 컨텐츠를 보고 있는 시간 모델
 *
 * 모든 시간의 기준은 초를 사용합니다.
 * 각 필드별 업데이트된 값만 새로 받기 위해 모든 필드는
 * nullable 입니다. 단, 적어도 한 필드는 값이 있어야 합니다.
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
@Unsupported
data class ContentStayTime(
    val userId: String,
    val categories: List<Int?>,
    @IntRange(from = 0) val search: Int? = null,
    @IntRange(from = 0) val dm: Int? = null,
    @IntRange(from = 0) val notification: Int? = null,
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
        if (
            categories.all { category ->
                category == null
            } &&
            search == null &&
            dm == null &&
            notification == null
        ) {
            throw IllegalArgumentException("At least one field must be not null.")
        }
        categories.forEachIndexed { index, time ->
            if (time != null) {
                requireRange(
                    min = 0,
                    field = "categories[$index] (${Category.values()[index].name})",
                    value = time,
                )
            }
        }
        if (search != null) {
            requireRange(
                min = 0,
                field = "search",
                value = search,
            )
        }
        if (dm != null) {
            requireRange(
                min = 0,
                field = "dm",
                value = dm,
            )
        }
        if (notification != null) {
            requireRange(
                min = 0,
                field = "notification",
                value = notification,
            )
        }
    }
}
