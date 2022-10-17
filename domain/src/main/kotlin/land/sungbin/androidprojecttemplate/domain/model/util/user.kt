package land.sungbin.androidprojecttemplate.domain.model.util

import land.sungbin.androidprojecttemplate.domain.model.User

/**
 * [유저][User] 의 아이디를 가져옵니다.
 * **덕키에서는 유저 닉네임을 유저의 고유 키(아이디)로 사용합니다.**
 * 따라서 `User.nickname` 을 델리게이트 합니다.
 * 단지 아이디를 도메인적으로 나타내기 위한 extension 입니다.
 * [User] 모델에 넣게 되면 직렬화되면서 이 값이 같이 포함되기 때문에
 * 중복되는 필드를 없애기 위해 별도 extension 으로 분리하였습나다.
 *
 * @see User.nickname
 */
val User.id get() = nickname
