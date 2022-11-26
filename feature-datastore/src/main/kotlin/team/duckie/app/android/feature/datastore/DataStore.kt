/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferenceKey {
    private const val PackageName = "team.duckie.app.android.feature.datastore"
    internal val Name = buildPreferenceKey(token = "root")

    @Suppress("NOTHING_TO_INLINE")
    private inline fun buildPreferenceKey(
        type: String? = null,
        token: String,
    ): String {
        return "$PackageName.${type?.plus(".").orEmpty()}$token"
    }

    object Onboard {
        val Finish = booleanPreferencesKey(
            name = buildPreferenceKey(type = "onboard", token = "finish"),
        )
    }

    object User {
        val Nickname = stringPreferencesKey(
            name = buildPreferenceKey(type = "user", token = "nickname"),
        )
        val ProfilePhoto = stringPreferencesKey(
            name = buildPreferenceKey(type = "user", token = "profile-photo"),
        )
        val Email = stringPreferencesKey(
            name = buildPreferenceKey(type = "user", token = "email"),
        )
    }
}

val Context.dataStore by preferencesDataStore(
    name = PreferenceKey.Name,
)
