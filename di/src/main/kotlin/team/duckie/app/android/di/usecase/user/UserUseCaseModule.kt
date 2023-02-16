/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.di.usecase.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.duckie.app.android.domain.user.repository.UserRepository
import team.duckie.app.android.domain.user.usecase.FetchUserFollowingUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserMeFollowersUseCase
import team.duckie.app.android.domain.user.usecase.FetchUserMeFollowingsUseCase
import team.duckie.app.android.domain.user.usecase.GetMeUseCase
import team.duckie.app.android.domain.user.usecase.GetUserUseCase
import team.duckie.app.android.domain.user.usecase.NicknameDuplicateCheckUseCase
import team.duckie.app.android.domain.user.usecase.SetMeUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    fun provideGetMeUseCase(repository: UserRepository): GetMeUseCase {
        return GetMeUseCase(repository)
    }
    @Provides
    fun provideSetMeUseCase(repository: UserRepository): SetMeUseCase {
        return SetMeUseCase(repository)
    }
    @Provides
    fun provideGetUserUseCase(repository: UserRepository): GetUserUseCase {
        return GetUserUseCase(repository)
    }

    @Provides
    fun provideUserUpdateUseCase(repository: UserRepository): UserUpdateUseCase {
        return UserUpdateUseCase(repository)
    }

    @Provides
    fun provideUserNicknameValidateCheckUseCase(repository: UserRepository): NicknameDuplicateCheckUseCase {
        return NicknameDuplicateCheckUseCase(repository)
    }

    @Provides
    fun provideUserFollowingUseCase(repository: UserRepository): FetchUserFollowingUseCase {
        return FetchUserFollowingUseCase(repository)
    }

    @Provides
    fun provideUserMeFollowingsUseCase(repository: UserRepository): FetchUserMeFollowingsUseCase {
        return FetchUserMeFollowingsUseCase(repository)
    }

    @Provides
    fun provideUserMeFollowersUseCase(repository: UserRepository): FetchUserMeFollowersUseCase {
        return FetchUserMeFollowersUseCase(repository)
    }
}
