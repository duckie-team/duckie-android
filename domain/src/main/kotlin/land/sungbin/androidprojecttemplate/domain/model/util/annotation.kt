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
 * 이 어노테이션이 붙는 필드는 data 계층에서 `NewField` optin 으로 변경되어,
 * 별도로 관리됩니다. DB 명세와 대조하면서 혼동을 방지하기 위해 사용됩니다.
 *
 * `Int` 에서 `enum class` 로 변경된 경우를 제외하고, 새로운 타입으로
 * 설정된 경우도 이 어노테이션으로 표가됩니다.
 */
@Target(
    AnnotationTarget.VALUE_PARAMETER,
)
internal annotation class New

/**
 * 덕키가 MVP 작업으로 진행되면서 사용되지 않는 필드를 나타냅니다.
 * 모델들은 추후 정식 개발을 위해 MVP 에서 사용되지 않는 필드들까지 다
 * 추가하였기 때문에, 이 어노테이션이 붙은 필드는 nullable 혹은
 * 정해진 값을 가져가며 기본값이 null 혹은 정해진 값으로 설정됩니다.
 * 설정된 기본값의 의미를 나타내기 위해 사용됩니다.
 * 만약 이 어노테이션이 생성자에 붙었다면 해당 클래스는 presentation 계층에서
 * 사용되면 안됨을 나타냅니다. 이러한 경우 실제 API request 에도 사용되지
 * 않습니다.
 */
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
)
internal annotation class Unsupported
