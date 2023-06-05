/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.ignore.datasource

interface IgnoreRemoteDataSource {

    suspend fun ignoreUser(targetId: Int)
    suspend fun deleteIgnoreUser(targetId: Int)
}
