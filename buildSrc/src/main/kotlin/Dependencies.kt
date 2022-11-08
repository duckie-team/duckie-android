object Dependencies {
    const val Ksp = "com.google.devtools.ksp:symbol-processing-api:${Versions.Ksp}"
    const val FirebaseBom = "com.google.firebase:firebase-bom:${Versions.FirebaseBom}"
    const val Coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"

    object Orbit {
        const val Test = "org.orbit-mvi:orbit-test:${Versions.Orbit}"
        const val Main = "org.orbit-mvi:orbit-viewmodel:${Versions.Orbit}"
    }

    object FirebaseEachKtx { // 각각 쓰이는 모듈이 다름
        const val Storage = "com.google.firebase:firebase-storage-ktx"
        const val Performance = "com.google.firebase:firebase-perf-ktx"
        const val Analytics = "com.google.firebase:firebase-analytics-ktx"
        const val RemoteConfig = "com.google.firebase:firebase-config-ktx"
        const val Crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    }

    object EachKtx {
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val Fragment = "androidx.fragment:fragment-ktx:${Versions.Ktx.Fragment}"
        const val Activity = "androidx.activity:activity-ktx:${Versions.Ktx.Activity}"
        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Ktx.Lifecycle}"
    }

    object EachUi {
        const val Browser = "androidx.browser:browser:${Versions.Ui.Browser}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}"
    }

    val SharedKtx = listOf(
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Ktx.Lifecycle}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Ktx.Lifecycle}"
    )

    val Compose = listOf(
        "androidx.compose.material:material:${Versions.Compose.Main}",
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Main}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "com.github.skydoves:landscapist-coil:${Versions.Compose.Landscapist}",
        "com.google.accompanist:accompanist-placeholder:${Versions.Compose.Accompanist}",
        "com.google.accompanist:accompanist-swiperefresh:${Versions.Compose.Accompanist}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.LifecycleCompose}",
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.Compose.LifecycleCompose}",
        "com.google.accompanist:accompanist-navigation-animation:${Versions.Compose.Accompanist}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.ConstraintLayout}",
        "androidx.hilt:hilt-navigation-compose:${Versions.Compose.HiltNavigation}",
    )

    val Ui = listOf(
        "androidx.core:core-splashscreen:${Versions.Ui.Splash}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Main}"
    )

    val Jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Util.Jackson}"
    )

    val Network = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}",
        "io.ktor:ktor-client-core:${Versions.Network.Ktor}",
        "io.ktor:ktor-client-cio:${Versions.Network.Ktor}",
        "io.ktor:ktor-client-logging:${Versions.Network.Ktor}",
        "io.ktor:ktor-serialization-jackson:${Versions.Network.Ktor}",
        "io.ktor:ktor-client-content-negotiation:${Versions.Network.Ktor}",

    )

    val Login = listOf(
        "com.kakao.sdk:v2-user:${Versions.Login.Kakao}"
    )

    object Util { // Erratum 은 :presentation 에서만 쓰임
        const val Erratum = "land.sungbin:erratum:${Versions.Util.Erratum}"
        const val Logeukes = "land.sungbin:logeukes:${Versions.Util.Logeukes}"
    }

    val Analytics = listOf(
        "com.github.anrwatchdog:anrwatchdog:${Versions.Analytics.AnrWatchDog}"
    )

    object Jetpack {
        const val Room = "androidx.room:room-ktx:${Versions.Jetpack.Room}"
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Jetpack.Hilt}"
        const val DataStore =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}"
    }

    object Compiler {
        const val RoomKsp = "androidx.room:room-compiler:${Versions.Jetpack.Room}"
        const val Hilt = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.Hilt}"
    }

    val Test = listOf(
        "org.hamcrest:hamcrest:${Versions.Test.Hamcrest}",
        "org.junit.jupiter:junit-jupiter-api:${Versions.Test.JUnit}",
        "org.junit.jupiter:junit-jupiter-engine:${Versions.Test.JUnit}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.Coroutine}"
    )

    object Debug {
        const val LeakCanary =
            "com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}"
        val Compose = listOf(
            "androidx.compose.ui:ui-tooling:${Versions.Compose.Main}",
            "androidx.compose.ui:ui-tooling-preview:${Versions.Compose.Main}"
        )
    }

    object Quack {
        const val components = "team.duckie.quack:quack-ui-components:${Versions.Quack.quack}"
        const val lintCore = "team.duckie.quack:quack-lint-core:${Versions.Quack.quack}"
        const val lintQuack = "team.duckie.quack:quack-lint-quack:${Versions.Quack.quack}"
        const val lineCompose = "team.duckie.quack:quack-lint-compose:${Versions.Quack.quack}"
    }
}
