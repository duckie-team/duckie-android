package land.sungbin.androidprojecttemplate.data.mapper

internal fun <T> T?.unwrap(
    field: String,
) = this ?: throw IllegalStateException(
    """
        |필수 데이터가 누락되었습니다.
        |데이터 베이스를 확인해 주세요.
        |누락된 필드: $field
    """.trimMargin()
)
