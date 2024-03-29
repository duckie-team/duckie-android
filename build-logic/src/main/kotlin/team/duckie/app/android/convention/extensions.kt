/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("SameParameterValue")

package team.duckie.app.android.convention

import DependencyHandler.Extensions.debugImplementations
import DependencyHandler.Extensions.implementations
import DependencyHandler.Extensions.testImplementations
import DependencyHandler.Extensions.testRuntimeOnlys
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.NamedDomainObjectContainerScope
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.applyPlugins(vararg plugins: String) {
    plugins.forEach { plugin ->
        pluginManager.apply(plugin)
    }
}

internal fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

@Suppress("nothing_to_inline")
internal inline operator fun <T : Any, C : NamedDomainObjectContainer<T>> C.invoke(
    configuration: Action<NamedDomainObjectContainerScope<T>>,
) = apply { configuration.execute(NamedDomainObjectContainerScope.of(this)) }

internal fun DependencyHandler.setupJunit(core: Any, engine: Any) {
    testImplementations(core)
    testRuntimeOnlys(engine)
}

internal fun DependencyHandler.setupCompose(core: Any, debug: Any) {
    implementations(core)
    debugImplementations(debug)
}
