plugins {
    // id("com.spotify.ruler")
    installPlugins(
        isPresentation = true,
        isDFM = false,
        scabbard = false,
        test = false,
        hilt = true,
    )
    id("com.android.application")
    // id("com.google.gms.google-services")
    // id("com.google.firebase.crashlytics")
    // id("com.google.firebase.firebase-perf")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("name.remal.check-dependency-updates") version Versions.BuildUtil.CheckDependencyUpdates
    id("kotlin-parcelize")
}

/*ruler {
    abi.set("arm64-v8a")
    locale.set("ko")
    screenDensity.set(480)
    sdkVersion.set(31)
}*/

android {
    namespace = "land.sungbin.androidprojecttemplate"

    // TODO: set your signing configs
    /*signingConfigs {
        create("release") {
            storeFile = file(BuildConstants.StoreFilePath)
            storePassword = BuildConstants.StorePassword
            keyAlias = BuildConstants.KeyAlias
            keyPassword = BuildConstants.KeyPassword
        }
    }*/

    defaultConfig {
        versionCode = ApplicationConstants.versionCode
        versionName = ApplicationConstants.versionName
    }

    buildTypes {
        debug {
            aaptOptions.cruncherEnabled = false // png optimization (default: true)
        }

        // TODO
        /*release {
            signingConfig = signingConfigs.getByName("release")
        }*/
    }

    buildFeatures {
        compose = true
        dataBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Main
    }
}

dependencies {

}
