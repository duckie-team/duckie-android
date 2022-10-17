package land.sungbin.androidprojecttemplate.domain.model.util

/**
 * Primary Key 를 나타냅니다.
 * 중요한 필드임을 인지시키기 위해 사용됩니다.
 *
 * Also see: [FK]
 */
@Target(
    AnnotationTarget.VALUE_PARAMETER,
)
internal annotation class PK

/**
 * Foreign Key 를 나타냅니다.
 * 중요한 필드임을 인지시키기 위해 사용됩니다.
 *
 * Also see: [PK]
 */
@Target(
    AnnotationTarget.VALUE_PARAMETER,
)
internal annotation class FK

/**
 * API 사용에 편의를 위해 추가된 DB 명세에는 없는 필드임을 나타냅니다.
 * 이 어노테이션이 붙는 필드는 data 계층에서 `NewModel` optin 으로 변경되어,
 * 별도로 관리됩니다. DB 명세와 대조하면서 혼동을 방지하기 위해 사용됩니다.
 */
@Target(
    AnnotationTarget.VALUE_PARAMETER,
)
internal annotation class New
