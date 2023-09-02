/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.create.exam.viewmodel.sideeffect

import com.google.firebase.crashlytics.FirebaseCrashlytics
import team.duckie.app.android.domain.gallery.usecase.LoadGalleryImagesUseCase
import team.duckie.app.android.feature.create.exam.viewmodel.CreateProblemViewModel
import team.duckie.app.android.feature.create.exam.viewmodel.state.CreateProblemState

internal sealed class CreateProblemSideEffect {
    object FinishActivity : CreateProblemSideEffect()

    /**
     * [LoadGalleryImagesUseCase] 를 통해 얻은 이미지 목록을 [CreateProblemViewModel.galleryImages] 에 저장합니다.
     *
     * @param images 갤러리에서 불러온 이미지 목록
     */
    data class UpdateGalleryImages(val images: List<String>) : CreateProblemSideEffect()

    /**
     * [CreateProblemViewModel] 의 비즈니스 로직 처리중에 발생한 예외를 [exception] 으로 받고,
     * 해당 [exception] 을 [FirebaseCrashlytics] 에 제보합니다.
     *
     * @param exception 발생한 예외
     *
     * @see CreateProblemState.error
     */
    data class ReportError(val exception: Throwable) : CreateProblemSideEffect()

    /**
     * 이미 존재하는 [태그명][tagName]인 경우 방출하는 SideEffect
     *
     * @param exception 발생한 예외
     * @param tagName 테그명
     */
    data class TagAlreadyExist(val exception: Throwable, val tagName: String) :
        CreateProblemSideEffect()
}
