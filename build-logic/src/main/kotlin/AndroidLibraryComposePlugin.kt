import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import team.duckie.app.android.convention.configureCompose

/**
 * Android 프레임워크의 Library 환경에서 Jetpack Compose 를 사용할 준비를 합니다.
 */
internal class AndroidLibraryComposePlugin : Plugin<Project> {
    override fun apply(
        target: Project,
    ) {
        with(
            receiver = target,
        ) {
            val extension = extensions.getByType<LibraryExtension>()

            configureCompose(
                extension = extension,
            )
        }
    }
}
