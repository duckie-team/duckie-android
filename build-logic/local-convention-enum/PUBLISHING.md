# PUBLISHING NOTE

### How?

1. DO NOT EDIT VERSION CONSTANT.
2. JUST `./gradlew :local-convention-enum:publishToMavenLocal`.

### Usage

1. Add `mavenLocal()` to `repositories`.
2. Add `team.duckie.app.android.local.convention.enum` plugin.
   ```gradle
   plugins {
       alias(libs.plugins.local.convention.enum)
   }
   ```
