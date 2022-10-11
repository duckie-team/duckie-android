package land.sungbin.androidprojecttemplate.domain.model

import land.sungbin.androidprojecttemplate.domain.model.constraint.Categories

/**
 * 유저가 특정 컨텐츠를 보고 있는 시간 모델
 *
 * 모든 시간의 기준은 초를 사용합니다.
 *
 * @param userId [유저 아이디][User.nickname]
 * @param categories 각각 카테고리별 머문 시간.
 * [Categories] 타입이 아닌, **각각 카테고리별로 유저가 얼마나
 * 머물렀는지를 각각 인덱스로 받아야 합니다.**
 * @param search 검색 화면에 머문 시간
 * @param dm DM 화면에 머문 시간
 * @param notification 알림 화면에 머문 시간
 */
data class ContentStayTime(
    val userId: String,
    val categories: List<Int>,
    val search: Int,
    val dm: Int,
    val notification: Int,
)
