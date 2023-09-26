/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.share

import android.content.Intent
import androidx.core.net.toUri
import java.io.File

object ShareUtil {
    fun intentInstagramStory(path: String): Intent {
        val file = File(path)
        return Intent("com.instagram.share.ADD_TO_STORY").apply {
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("interactive_asset_uri", file.toUri())
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
    }
}
