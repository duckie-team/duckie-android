/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.friends

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.duckie.app.android.feature.ui.friends.viewmodel.FriendsViewModel
import team.duckie.app.android.util.kotlin.FriendsType
import team.duckie.app.android.util.ui.BaseActivity
import team.duckie.app.android.util.ui.const.Extras
import team.duckie.app.android.util.ui.finishWithAnimation
import team.duckie.quackquack.ui.theme.QuackTheme

@AndroidEntryPoint
class FriendsActivity : BaseActivity() {

    private val viewModel: FriendsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val friendType = getFriendType(intent)

        setContent {
            QuackTheme {
                FriendScreen(
                    viewModel = viewModel,
                    onPrevious = {
                        finishWithAnimation()
                    },
                    initialFriendsType = friendType,
                )
            }
        }
    }

    private fun getFriendType(intent: Intent): FriendsType {
        val friendTypeIndex = intent.getIntExtra(Extras.FriendType, 0)
        return FriendsType.fromIndex(friendTypeIndex)
    }
}
