rootProject.name = "duckie"
include(
    ":data",
    ":domain",
    ":presentation",
    ":shared:domain",
    ":shared:compose",
    ":shared:android",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
