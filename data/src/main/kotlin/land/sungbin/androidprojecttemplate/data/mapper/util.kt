package land.sungbin.androidprojecttemplate.data.mapper

/**
 * 주어진 nullable 한 타입을 non-null 로 변환합니다.
 *
 * @receiver nullable 한 타입
 * @param field nullable 한 타입의 이름. non-null 로 전환할 수
 * 없을 때 에러 메시지로 사용됩니다.
 *
 * @return non-null 한 타입
 */
internal fun <T> T?.unwrap(
    field: String,
) = this ?: throw IllegalStateException(
    """
        |필수 데이터가 누락되었습니다.
        |데이터 베이스를 확인해 주세요.
        |누락된 필드: $field
    """.trimMargin()
)
