package land.sungbin.androidprojecttemplate.data.util

/**
 * API 사용의 편의를 위해 일부 모델은 DB 명세와 다르게 설계됐습니다.
 * 하지만 실제 API Request 를 할 때는 DB 명세에 맞게 수행해야 하므로,
 * 원래의 DB 명세와 일치하게 모델을 수정하여 API Request 를 해야 하는
 * 모델을 나타내기 위한 OptIn 어노테이션 입니다.
 *
 * @param changedFieldNames DB 명세와 다르게 설계된 필드명들을 나타냅니다.
 */
@RequiresOptIn(
    message = "이 모델은 사용자의 편의를 위해 모델이 변경되었습니다. " +
            "실제 API 연결 작업을 할 때는 원래의 모델을 사용해야 합니다.",
)
annotation class NewField(
    vararg val changedFieldNames: String,
)
