/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.notification.viewmodel

import kotlinx.collections.immutable.toImmutableList
import team.duckie.app.android.domain.notification.model.Notification
import team.duckie.app.android.util.kotlin.randomString

val skeletonNotifications = (1..10).map {
    Notification.empty(id = it).copy(
        body = randomString(24),
    )
}.toImmutableList()
