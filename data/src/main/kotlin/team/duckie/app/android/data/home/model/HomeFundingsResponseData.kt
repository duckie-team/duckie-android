/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import com.fasterxml.jackson.annotation.JsonProperty
import team.duckie.app.android.data.home.model.HomeFundingData

internal data class HomeFundingsResponseData(
    @field:JsonProperty("upcomingExams")
    val upcomingExams: List<HomeFundingData>? = null,
)
