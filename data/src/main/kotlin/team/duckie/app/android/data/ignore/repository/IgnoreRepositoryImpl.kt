/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.ignore.repository

import team.duckie.app.android.data.ignore.datasource.IgnoreRemoteDataSourceImpl
import team.duckie.app.android.domain.ignore.repository.IgnoreRepository
import javax.inject.Inject

class IgnoreRepositoryImpl @Inject constructor(
    private val ignoreRemoteDataSourceImpl: IgnoreRemoteDataSourceImpl,
) : IgnoreRepository {
    override suspend fun ignoreUser(targetId: Int) {
        ignoreRemoteDataSourceImpl.ignoreUser(targetId = targetId)
    }

    override suspend fun deleteIgnoreUser(targetId: Int) {
        ignoreRemoteDataSourceImpl.deleteIgnoreUser(targetId = targetId)
    }
}
