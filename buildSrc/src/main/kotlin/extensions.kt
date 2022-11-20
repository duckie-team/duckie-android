import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

@Suppress("ControlFlowWithEmptyBody") // empty if block
fun PluginDependenciesSpec.installPlugins(
    isPresentation: Boolean = false,
    isDFM: Boolean = false,
    scabbard: Boolean = false,
    test: Boolean = false,
    hilt: Boolean = false,
) {
    if (!isPresentation && !isDFM) {
        id("com.android.library")
    }
    if (isDFM) {
        id("com.android.dynamic-feature")
    }
    id("kotlin-android")
    id("kotlin-kapt")
    if (!isDFM && hilt) {
        id("dagger.hilt.android.plugin")
    }
    if (test) {
        id("de.mannodermaus.android-junit5")
    }
    if (scabbard) {
//        id("scabbard.gradle") version Versions.Util.Scabbard
    }
}

fun DependencyHandler.installDependencies(
    isSharedModule: Boolean = false,
    orbit: Boolean = false,
    hilt: Boolean = false,
    compose: Boolean = false,
    test: Boolean = false,
) {
    if (orbit) {
        implementation(Dependencies.Orbit.Main)
    }
    if (!isSharedModule) {
        projectImplementation(ProjectConstants.SharedAndroid)
    }
    if (hilt) {
        implementation(Dependencies.Jetpack.Hilt)
        kapt(Dependencies.Compiler.Hilt)
    }
    if (compose) {
        Dependencies.Compose.forEach(::implementation)
        Dependencies.Debug.Compose.forEach(::debugImplementation)
        projectImplementation(ProjectConstants.SharedCompose)
    }
    if (test) {
        testImplementation(Dependencies.Orbit.Test)
        Dependencies.Test.forEach { testDependency ->
            testImplementation(testDependency)
        }
    }
}

fun DependencyHandler.projectImplementation(path: String) {
    implementation(project(path))
}

@Suppress(
    "OPT_IN_IS_NOT_ENABLED", // OptIn usage
    "UNCHECKED_CAST" // Collection<*> as Collection<Any>
)
@OptIn(ExperimentalStdlibApi::class) // buildList
// only List or String or platform enters.
fun List<*>.dependenciesFlatten() = buildList {
    this@dependenciesFlatten.forEach { dependency ->
        checkNotNull(dependency) {
            "dependency $dependency is null."
        }
        when (dependency) {
            is Collection<*> -> addAll(dependency as Collection<Any>)
            else -> add(dependency)
        }
    }
}

private fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

private fun DependencyHandler.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

private fun DependencyHandler.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

private fun DependencyHandler.kapt(dependency: Any) {
    add("kapt", dependency)
}

private fun DependencyHandler.project(path: String) =
    project(mapOf(Pair("path", path))) as ProjectDependency
