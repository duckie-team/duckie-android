/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.impl

import java.io.File
import team.duckie.app.android.domain.auth.usecase.CheckAccessTokenUseCase
import team.duckie.app.android.domain.auth.usecase.JoinUseCase
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.category.usecase.GetCategoriesUseCase
import team.duckie.app.android.domain.file.constant.FileType
import team.duckie.app.android.domain.file.usecase.FileUploadUseCase
import team.duckie.app.android.domain.kakao.usecase.GetKakaoAccessTokenUseCase
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.domain.tag.usecase.TagCreateUseCase
import team.duckie.app.android.domain.user.usecase.UserUpdateUseCase
import team.duckie.app.android.feature.ui.onboard.constant.OnboardStep
import team.duckie.app.android.feature.ui.onboard.viewmodel.ApiViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState

internal class ApiViewModelInstance(
    private val getKakaoAccessTokenUseCase: GetKakaoAccessTokenUseCase,
    private val joinUseCase: JoinUseCase,
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val fileUploadUseCase: FileUploadUseCase,
    private val tagCreateUseCase: TagCreateUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
) : ApiViewModel {
    private lateinit var eventHandler: (state: OnboardState) -> Unit
    private lateinit var sideEffectHandler: suspend (effect: OnboardSideEffect) -> Unit
    private lateinit var exceptionHandler: suspend (exception: Throwable) -> Unit

    override fun setEventHandler(handler: (state: OnboardState) -> Unit) {
        eventHandler = handler
    }

    override fun setSideEffectHandler(handler: suspend (effect: OnboardSideEffect) -> Unit) {
        sideEffectHandler = handler
    }

    override fun setExceptionHandler(handler: suspend (exception: Throwable) -> Unit) {
        exceptionHandler = handler
    }

    override suspend fun getKakaoAccessToken() {
        getKakaoAccessTokenUseCase()
            .onSuccess { token ->
                sideEffectHandler(OnboardSideEffect.DelegateJoin(token))
            }
            .attachExceptionHandling()
    }

    override suspend fun join(kakaoAccessToken: String) {
        joinUseCase(kakaoAccessToken)
            .onSuccess { response ->
                eventHandler(OnboardState.Joined(response.isNewUser))
                sideEffectHandler(OnboardSideEffect.UpdateUser(response.user))
                sideEffectHandler(OnboardSideEffect.UpdateAccessToken(response.accessToken))
            }
            .attachExceptionHandling()
    }

    override suspend fun checkAccessToken(token: String, nextStep: OnboardStep) {
        checkAccessTokenUseCase(token)
            .onSuccess {
                sideEffectHandler(OnboardSideEffect.AttachAccessTokenToHeader(token))
                eventHandler(OnboardState.NavigateStep(nextStep))
            }
            .attachExceptionHandling {
                eventHandler(OnboardState.AccessTokenValidationFailed)
            }
    }

    override suspend fun getCategories(withPopularTags: Boolean) {
        getCategoriesUseCase(withPopularTags)
            .onSuccess { categories ->
                eventHandler(OnboardState.CategoriesLoaded(categories))
            }
            .attachExceptionHandling()
    }

    override suspend fun updateFile(file: File, type: FileType) {
        fileUploadUseCase(file, type)
            .onSuccess { url ->
                eventHandler(OnboardState.FileUploaded(url))
            }
            .attachExceptionHandling()
    }

    override suspend fun createTag(name: String) {
        tagCreateUseCase(name)
            .onSuccess { tag ->
                eventHandler(OnboardState.TagCreated(tag))
            }
            .attachExceptionHandling()
    }

    override suspend fun updateUser(
        id: Int,
        nickname: String,
        profileImageUrl: String,
        favoriteCategories: List<Category>,
        favoriteTags: List<Tag>,
    ) {
        userUpdateUseCase(
            id = id,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            favoriteCategories = favoriteCategories,
            favoriteTags = favoriteTags,
        )
            .onSuccess { user ->
                sideEffectHandler(OnboardSideEffect.UpdateUser(user))
            }
            .attachExceptionHandling()
    }

    private suspend fun Result<*>.attachExceptionHandling(
        additinal: (exception: Throwable) -> Unit = {},
    ) {
        onFailure { exception ->
            exceptionHandler(exception)
            additinal(exception)
        }
    }
}
