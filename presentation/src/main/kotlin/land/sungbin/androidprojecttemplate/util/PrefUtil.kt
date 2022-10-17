package land.sungbin.androidprojecttemplate.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import land.sungbin.androidprojecttemplate.application.DuckieApplication

object PrefUtil {

    private fun getSharedPreferences(
        @ApplicationContext
        fileName: String?,
        mode: Int = Context.MODE_PRIVATE
    ): SharedPreferences {
        return DuckieApplication.ApplicationContext().getSharedPreferences(fileName, mode)
    }

    @JvmStatic
    fun setString(fileName: String?, key: String?, value: String?): Boolean {
        val pref = getSharedPreferences(fileName)
        val editor = pref.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    @JvmStatic
    fun getString(fileName: String?, key: String?): String? {
        val pref = getSharedPreferences(fileName)
        return pref.getString(key, null)
    }
}