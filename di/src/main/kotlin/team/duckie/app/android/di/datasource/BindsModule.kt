/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.data.auth.datasource.AuthRemoteDataSourceImpl
import team.duckie.app.android.data.user.datasource.UserRemoteDataSourceImpl
import team.duckie.app.android.domain.auth.datasource.AuthDataSource
import team.duckie.app.android.domain.user.datasource.UserDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun provideAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthDataSource
    @Binds
    abstract fun provideUserRemoteDataSourceImpl(impl: UserRemoteDataSourceImpl): UserDataSource
}
