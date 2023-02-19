/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.domain.file.usecase

import androidx.compose.runtime.Immutable
import java.io.File
import javax.inject.Inject
import team.duckie.app.android.domain.file.constant.FileType
import team.duckie.app.android.domain.file.repository.FileRepository

@Immutable
class FileUploadUseCase @Inject constructor(
    private val repository: FileRepository,
) {
    suspend operator fun invoke(file: File, type: FileType): Result<String> {
        return runCatching {
            repository.upload(file = file, type = type.value)
        }
    }
}
