/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.home.viewmodel.mapper

import team.duckie.app.android.domain.recommendation.model.RecommendationJumbotronItem
import team.duckie.app.android.feature.ui.home.viewmodel.state.HomeState

internal fun RecommendationJumbotronItem.toUiModel() =
    HomeState.HomeRecommendJumbotron(
        id = id,
        coverUrl = thumbnailUrl,
        title = title,
        content = description,
        buttonContent = buttonTitle,
        type = type,
    )
