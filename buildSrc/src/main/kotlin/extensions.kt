/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

import java.io.File
import org.gradle.api.Project

val Project.keystoreSigningAvailable: Boolean
    get() {
        val keystoreFolder = File("$rootDir/keystore")
        return keystoreFolder.exists().also { available ->
            println("keystoreSigningAvailable: $available")
        }
    }

data class KeystoreSecrets(
    val storePath: String,
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String,
)

val Project.keystoreSecrets: KeystoreSecrets
    get() {
        val content = File("$rootDir/keystore/secrets.txt")
        val lines = content.readLines()
        return KeystoreSecrets(
            storePath = "$rootDir/keystore/quack.pepk",
            storePassword = lines[0],
            keyAlias = lines[1],
            keyPassword = lines[0],
        )
    }
