package land.sungbin.androidprojecttemplate.data.model.util

/**
 * API 사용의 편의를 위해 일부 필드는 DB 명세와 다르게 설계됐습니다.
 * 하지만 실제 API Request 를 할 때는 DB 명세에 맞게 수행해야 하므로,
 * 원래의 DB 명세와 일치하게 필드를 수정하여 API Request 를 해야 하는
 * 상태를 나타내기 위한 OptIn 어노테이션 입니다.
 */
@RequiresOptIn(
    message = "이 필드는 사용자의 편의를 위해 변경되었습니다. " +
            "실제 API 연결 작업을 할 때는 원래의 필드를 사용해야 합니다.",
)
@Target(
    AnnotationTarget.PROPERTY,
)
annotation class NewField
