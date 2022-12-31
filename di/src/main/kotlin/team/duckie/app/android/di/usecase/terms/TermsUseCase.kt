/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.terms

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.terms.repository.TermsRepository
import team.duckie.app.android.domain.terms.usecase.GetTermsUseCase

@Module
@InstallIn(SingletonComponent::class)
object TermsUseCase {
    @Provides
    fun provideGetTermsUseCase(repository: TermsRepository): GetTermsUseCase {
        return GetTermsUseCase(repository)
    }
}
