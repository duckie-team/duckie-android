package land.sungbin.androidprojecttemplate.data

/**
 * 판매요청[SaleRequest] data class
 *
 * @param seller 판매자
 * @param buyer 구매자
 * @param field 판매 요청한 글 필드 값
 */
data class SaleRequest(
    val seller: String,
    val buyer: String,
    val field: String,
)