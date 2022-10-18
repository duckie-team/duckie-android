package land.sungbin.androidprojecttemplate.domain.exception

/**
 * 덕키 API 호출중에 발생한 예외를 담는 Exception 클래스 입니다.
 *
 * @param message 에러 메시지.
 * 어느 API 요청에서 예외가 생겼는지 나타내기 위해 필수로 요구됩니다.
 */
class DuckApiException(
    override val message: String,
) : IllegalStateException()