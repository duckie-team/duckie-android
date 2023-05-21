/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferenceKey {
    // 결국 레거시가 되어 버렸다 😂
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
        val Finish = booleanPreferencesKey(buildPreferenceKey(type = "onboard", token = "finish"))
    }

    object Account {
        val AccessToken = stringPreferencesKey(buildPreferenceKey(type = "account", token = "access_token"))
    }

    object User {
        val Id = stringPreferencesKey(buildPreferenceKey(type = "user", token = "id"))
    }
}

val Context.dataStore by preferencesDataStore(
    name = PreferenceKey.Name,
)
