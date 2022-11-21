/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import java.io.File
import java.util.Locale
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register
import team.duckie.app.android.convention.PluginEnum

class DependencyGraphPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register<DependencyGraphTask>("dependencyGraph")
    }
}

abstract class DependencyGraphTask : DefaultTask() {
    @TaskAction
    fun run() {
        val dot = File(project.rootProject.rootDir, "assets/project-dependency-graph/graph.dot")
        dot.parentFile.deleteRecursively()
        dot.parentFile.mkdirs()

        dot.appendText(
            """
             |digraph {
             |  graph [label="${project.rootProject.name}\n ",labelloc=t,fontsize=30,ranksep=1.4];
             |  node [style=filled, fillcolor="#bbbbbb"];
             |  rankdir=TB;
             |
            """.trimMargin(),
        )

        val rootProjects = mutableListOf<Project>()
        val queue = mutableListOf(project.rootProject)
        while (queue.isNotEmpty()) {
            val project = queue.removeAt(0)
            rootProjects.add(project)
            queue.addAll(project.childProjects.values)
        }
        queue.add(project.rootProject)

        val dependencyProjects = LinkedHashSet<Project>()
        val dependencies = LinkedHashMap<Pair<Project, Project>, MutableList<String>>()
        val appModules = mutableListOf<Project>()
        val dfmModules = mutableListOf<Project>()
        val utilModules = mutableListOf<Project>()
        val featureModules = mutableListOf<Project>()
        val uiFeatureModules = mutableListOf<Project>()

        while (queue.isNotEmpty()) {
            val project = queue.removeAt(0)
            queue.addAll(project.childProjects.values)

            with(project.plugins) {
                when {
                    hasPlugin(PluginEnum.AndroidLibrary) -> appModules.add(project)
                    hasPlugin(PluginEnum.AndroidDfm) -> dfmModules.add(project)
                    else -> Unit // Do nothing
                }
            }

            with(project.name) {
                when {
                    startsWith("feature-") && !contains("-ui-") -> featureModules.add(project)
                    startsWith("feature-ui-") -> uiFeatureModules.add(project)
                    startsWith("util-") -> utilModules.add(project)
                    else -> Unit // Do nothing
                }
            }

            project.configurations.all {
                if (name.toLowerCase(Locale.getDefault()).contains("test")) {
                    return@all
                }
                getDependencies()
                    .withType(ProjectDependency::class.java)
                    .map(ProjectDependency::getDependencyProject)
                    .forEach { dependency ->
                        dependencyProjects.add(project)
                        dependencyProjects.add(dependency)
                        rootProjects.remove(dependency)

                        val graphKey = project to dependency
                        val traits = dependencies.computeIfAbsent(graphKey) {
                            mutableListOf()
                        }

                        if (name.toLowerCase(Locale.getDefault()).endsWith("implementation")) {
                            traits.add("style=dotted")
                        }
                    }
            }
        }

        dependencyProjects.sortedBy(Project::getPath).also { sortedDependencyProjects ->
            dependencyProjects.clear()
            dependencyProjects.addAll(sortedDependencyProjects)
        }

        dot.appendText("\n  # Projects\n\n")

        for (project in dependencyProjects) {
            val traits = mutableListOf<String>()

            when (project) {
                in appModules -> {
                    traits.add("shape=box")
                    traits.add("fillcolor=\"#baffc9\"") // 형광 연두색
                }
                in dfmModules -> traits.add("fillcolor=\"#c9baff\"") // 연두색
                in utilModules -> traits.add("fillcolor=\"#ffebba\"") // 연한 레몬색
                in featureModules -> traits.add("fillcolor=\"#81D4FA\"") // 하늘색
                in uiFeatureModules -> traits.add("fillcolor=\"#00aeff\"") // 파란색
                else -> traits.add("fillcolor=\"#eeeeee\"") // 연한 회색
            }

            dot.appendText("  \"${project.path}\" [${traits.joinToString(", ")}];\n")
        }

        dot.appendText("\n  {rank = same;")

        for (project in dependencyProjects) {
            if (rootProjects.contains(project)) {
                dot.appendText(" \"${project.path}\";")
            }
        }

        dot.appendText("}\n")
        dot.appendText("\n  # Dependencies\n\n")

        dependencies.forEach { (key, traits) ->
            dot.appendText("  \"${key.first.path}\" -> \"${key.second.path}\"")
            if (traits.isNotEmpty()) {
                dot.appendText(" [${traits.joinToString(", ")}]")
            }
            dot.appendText("\n")
        }

        dot.appendText("}\n")

        project.rootProject.exec {
            commandLine = listOf(
                "dot",
                "-Tpng",
                "-O",
                dot.path,
            )
        }

        dot.delete()
        println("Project module dependency graph created at ${dot.absolutePath}.png")
    }
}
