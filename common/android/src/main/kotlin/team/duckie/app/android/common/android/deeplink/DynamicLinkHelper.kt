/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.common.android.deeplink

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink

object DynamicLinkHelper {
    private const val DuckieDomainPrefix = "https://duckie.page.link"
    private const val SuccessToCopyMessage = "클립보드에 복사되었어요"
    private const val DuckieExamDynamicLink = "https://duckie.com/exam"

    fun createAndShareLink(context: Context, examId: Int) {
        createDynamicLink(examId)
            .addOnSuccessListener { result ->
                val shortLink = result.shortLink

                ClipboardHelper.copyToClipboard(context, "dynamicLink", shortLink.toString())

                Toast.makeText(context, SuccessToCopyMessage, Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }

    fun createDynamicLink(examId: Int): Task<ShortDynamicLink> {
        return FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("$DuckieExamDynamicLink/$examId"))
            .setDomainUriPrefix(DuckieDomainPrefix)
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder()
                    .build(),
            )
            .buildShortDynamicLink()
    }

    fun parseExamId(deepLink: Uri): String? {
        val pathSegments = deepLink.pathSegments
        if (pathSegments.size >= 2 && pathSegments[0] == "exam") {
            return pathSegments[1]
        }
        return null
    }
}
