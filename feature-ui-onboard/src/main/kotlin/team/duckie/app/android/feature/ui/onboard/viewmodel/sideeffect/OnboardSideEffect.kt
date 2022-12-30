/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.ui.onboard.viewmodel.sideeffect

import androidx.datastore.core.DataStore
import com.google.firebase.crashlytics.FirebaseCrashlytics
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.domain.kakao.model.KakaoUser
import team.duckie.app.android.domain.kakao.usecase.KakaoLoginUseCase
import team.duckie.app.android.feature.ui.onboard.viewmodel.OnboardViewModel
import team.duckie.app.android.feature.ui.onboard.viewmodel.state.OnboardState

/**
 * [OnboardViewModel] 에서 사용되는 SideEffect 모음
 */
internal sealed class OnboardSideEffect {
    /**
     * [KakaoLoginUseCase] 를 통해 얻은 [KakaoUser] 정보를 [DataStore] 및
     * [OnboardViewModel.me] 에 저장합니다.
     *
     * @param user 저장할 유저 객체
     */
    class SaveUser(val user: KakaoUser) : OnboardSideEffect()

    /**
     * [LoadGalleryImagesUseCase] 를 통해 얻은 이미지 목록을 [OnboardViewModel.galleryImages] 에 저장합니다.
     *
     * @param images 갤러리에서 불러온 이미지 목록
     */
    class UpdateGalleryImages(val images: List<String>) : OnboardSideEffect()

    /**
     * [OnboardViewModel] 의 비즈니스 로직 처리중에 발생한 예외를 [exception] 으로 받고,
     * 해당 [exception] 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     *
     * @see OnboardState.Error
     */
    class ReportError(val exception: Throwable) : OnboardSideEffect()
}
