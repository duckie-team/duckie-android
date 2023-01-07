/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel

import androidx.annotation.RestrictTo
import java.io.File
import team.duckie.app.android.domain.category.model.Category
import team.duckie.app.android.domain.tag.model.Tag
import team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect.OnboardSideEffect
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState

internal interface ApiViewModel {
    // TODO(sungbin): StateUpdater scope 제공
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    fun setEventHandler(handler: (state: OnboardState) -> Unit)

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    fun setSideEffectHandler(handler: suspend (effect: OnboardSideEffect) -> Unit)

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    fun setExceptionHandler(handler: suspend (exception: Throwable) -> Unit)

    suspend fun getKakaoAccessToken()

    suspend fun join(kakaoAccessToken: String)
    suspend fun attachAccessTokenToHeader(accessToken: String)

    suspend fun getCategories(withPopularTags: Boolean)

    suspend fun updateProfileImageFile(file: File)

    suspend fun createTag(name: String)

    suspend fun updateUser(
        id: Int,
        nickname: String?,
        profileImageUrl: String?,
        favoriteCategories: List<Category>?,
        favoriteTags: List<Tag>?,
    )
}
