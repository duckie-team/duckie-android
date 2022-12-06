/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.create.problem

import android.os.Bundle
import androidx.activity.compose.setContent
import team.duckie.app.android.feature.ui.create.problem.screen.CreateProblemScreen
import team.duckie.app.android.feature.ui.create.problem.viewmodel.CreateProblemViewModel
import team.duckie.app.android.util.ui.BaseActivity
import javax.inject.Inject

class CreateProblemActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: CreateProblemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateProblemScreen(viewModel = viewModel)
        }
    }
}
