package land.sungbin.androidprojecttemplate.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _currentPage: MutableLiveData<Int> = MutableLiveData()
    val currentPage: LiveData<Int> = _currentPage

    fun navigatePage(page: Int) {
        _currentPage.value = page
    }
}