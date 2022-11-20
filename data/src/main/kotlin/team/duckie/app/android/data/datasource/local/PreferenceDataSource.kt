package team.duckie.app.android.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(
    @ApplicationContext val context: Context,
) {
    private fun getSharedPreferences(
        fileName: String?,
        mode: Int = Context.MODE_PRIVATE,
    ): SharedPreferences {
        return context.getSharedPreferences(fileName, mode)
    }

    fun setString(fileName: String?, key: String?, value: String?): Boolean {
        val pref = getSharedPreferences(fileName)
        val editor = pref.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getString(fileName: String?, key: String?): String? {
        val pref = getSharedPreferences(fileName)
        return pref.getString(key, null)
    }

    companion object Session {
        const val FILE_NAME = "PrefUtil\$Session"
        const val KEY_USER = "user"
    }
}
