/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

object ConventionEnum {
    private const val prefix = "duckie"

    const val AndroidApplication = "$prefix.android.application"
    const val AndroidApplicationCompose = "$prefix.android.application.compose"

    const val AndroidLibrary = "$prefix.android.library"
    const val AndroidLibraryCompose = "$prefix.android.library.compose"
    const val AndroidLibraryComposeUiTest = "$prefix.android.library.compose.uitest"

    const val AndroidHilt = "$prefix.android.hilt"

    const val JvmLibrary = "$prefix.jvm.library"
    const val JvmJUnit4 = "$prefix.jvm.junit4"
    const val JvmDokka = "$prefix.jvm.dokka"
    const val JvmDependencyGraph = "$prefix.jvm.dependency.graph"
}
