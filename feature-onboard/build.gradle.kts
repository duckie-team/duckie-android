@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidLibraryCompose)
    id(ConventionEnum.AndroidLibraryComposeUiTest)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
}

android {
    namespace = "team.duckie.app.android.feature.onboard"
}
