/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.devMode.datasource

import team.duckie.app.android.data._datasource.devModeIsStage
import javax.inject.Inject

class DevModeDataSourceImpl @Inject constructor() : DevModeDataSource {
    override suspend fun setApiEnvironment(isStage: Boolean) {
        devModeIsStage = isStage
    }
}
