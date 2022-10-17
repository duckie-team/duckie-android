package land.sungbin.androidprojecttemplate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import land.sungbin.androidprojecttemplate.data.repository.auth.AuthRepository
import land.sungbin.androidprojecttemplate.util.Event
import land.sungbin.androidprojecttemplate.util.UserHolder
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userHolder: UserHolder,
) : ViewModel() {

    private val _kakaoLoginFailed: MutableLiveData<Throwable> = MutableLiveData()
    val kakaoLoginFailed: LiveData<Throwable> = _kakaoLoginFailed

    private val _loginSuccess: MutableLiveData<Event<Unit>> = MutableLiveData()
    val loginSuccess: LiveData<Event<Unit>> = _loginSuccess

    private val _loginFailed: MutableLiveData<Throwable> = MutableLiveData()
    val loginFailed: LiveData<Throwable> = _loginFailed

    private val _signUpSuccess: MutableLiveData<Event<Unit>> = MutableLiveData()
    val signUpSuccess: LiveData<Event<Unit>> = _signUpSuccess

    fun kakaoLogin() {
        viewModelScope.launch {
            runCatching {
                authRepository.kakaoLogin()
            }.onSuccess { response ->
                login()
            }.onFailure {

            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            runCatching {
                authRepository.login()
            }.onSuccess { response ->
                userHolder.setUser(response.user)
                _loginSuccess.value = Event(Unit)
            }.onFailure {

            }
        }
    }

    private fun signUp() {

        viewModelScope.launch {
            runCatching {

            }.onSuccess {

            }.onFailure {

            }
        }
    }

}