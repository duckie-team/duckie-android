package land.sungbin.androidprojecttemplate.ui.onboard

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import land.sungbin.androidprojecttemplate.data.domain.Category
import land.sungbin.androidprojecttemplate.data.domain.Tag
import land.sungbin.androidprojecttemplate.util.Event
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor() : ViewModel() {

    private val _currentPage: MutableLiveData<Int> = MutableLiveData()
    val currentPage: LiveData<Int> = _currentPage

    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: LiveData<String> = _nickname

    private val _profileImage: MutableLiveData<Uri> = MutableLiveData()
    val profileImage: LiveData<Uri> = _profileImage

    private val _categories: MutableLiveData<PersistentList<Category>> = MutableLiveData()
    val categories: LiveData<PersistentList<Category>> = _categories

    private val _selectedCategories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val selectedCategories: LiveData<MutableList<Category>> = _selectedCategories

    private val _selectedTags: MutableLiveData<List<Tag>> = MutableLiveData()
    val tags: LiveData<List<Tag>> = _selectedTags

    private val _onClickComplete: MutableLiveData<Event<Unit>> = MutableLiveData()
    val onClickComplete: LiveData<Event<Unit>> = _onClickComplete

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        _categories.value = persistentListOf(
            Category(0, "연예인"),
            Category(1, "영화"),
            Category(2, "만화/애니"),
            Category(3, "웹툰"),
            Category(4, "게임"),
            Category(5, "밀리터리"),
            Category(6, "IT"),
            Category(7, "게임"),
            Category(8, "밀리터리"),
            Category(9, "IT"),
        )
    }

    fun complete() {
        _onClickComplete.value = Event(Unit)
    }

    fun navigatePage(page: Int) {
        _currentPage.value = page
    }

    fun setNickName(nickname: String) {
        _nickname.value = nickname
    }

    fun onClickCategory(checked: Boolean, category: Category) {
        when (checked) {
            true -> {
                _selectedCategories.value =
                    ((_selectedCategories.value ?: mutableListOf()) + category).toMutableList()
            }

            else -> {
                _selectedCategories.value =
                    ((_selectedCategories.value ?: mutableListOf()) - category).toMutableList()
            }
        }
    }

    fun setProfileImage(image: Uri?) {
        _profileImage.value = image
    }


}