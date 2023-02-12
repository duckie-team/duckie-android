/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.datastore

import kotlin.properties.Delegates
import team.duckie.app.android.domain.user.model.User

var me by Delegates.notNull<User>()
