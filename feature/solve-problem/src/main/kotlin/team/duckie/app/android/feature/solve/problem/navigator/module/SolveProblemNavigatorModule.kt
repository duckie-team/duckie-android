/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.solve.problem.navigator.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.feature.solve.problem.navigator.impl.SolveProblemNavigatorImpl
import team.duckie.app.android.navigator.feature.solveproblem.SolveProblemNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SolveProblemNavigatorModule {

    @Singleton
    @Binds
    abstract fun bindSolveProblemNavigator(navigator: SolveProblemNavigatorImpl): SolveProblemNavigator
}
