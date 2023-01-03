/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.util.viewmodel.activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import team.duckie.app.android.util.viewmodel.BaseViewModel

class SavedStateTestViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<Int, Nothing>(
    savedStateHandle = savedStateHandle,
    initialState = BaseViewModelSavedStateTestActivity.ViewModelDefaultState,
)

class BaseViewModelSavedStateTestActivity : ComponentActivity() {

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var vm: SavedStateTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultViewModelProviderFactory
        vm = SavedStateTestViewModel(
            savedStateHandle = defaultViewModelCreationExtras.createSavedStateHandle(),
        )

        setContentView(
            Button(this).apply {
                id = IncrementButtonViewId
                text = IncrementButtonViewText
                setOnClickListener {
                    vm.updateState { ViewModelFinalState }
                }
            }
        )
    }

    companion object {
        const val ViewModelDefaultState = 0
        const val ViewModelFinalState = 1

        const val IncrementButtonViewId = 1000
        private const val IncrementButtonViewText = "Increment State"
    }
}
