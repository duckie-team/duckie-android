package land.sungbin.androidprojecttemplate.data.repository.auth

import land.sungbin.androidprojecttemplate.data.domain.User

interface AuthRepository {
    fun kakaoLogin() : User
}