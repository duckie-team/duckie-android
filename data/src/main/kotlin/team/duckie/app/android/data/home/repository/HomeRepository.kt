/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.home.repository

import team.duckie.app.android.data.home.datasource.HomeDataSource
import team.duckie.app.android.domain.home.model.HomeFunding
import team.duckie.app.android.domain.home.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun getFunding(): List<HomeFunding> {
        return homeDataSource.getFunding()
    }
}
