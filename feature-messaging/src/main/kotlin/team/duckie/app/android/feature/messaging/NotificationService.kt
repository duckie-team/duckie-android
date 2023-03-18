/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.feature.messaging

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("MyFcmService", token)
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        // TODO(EvergreenTree97): Token Server에 전달
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(EvergreenTree97): 알림 눌렀을 때
        Log.d("MyFcmService", "Notification Title :: ${remoteMessage.notification?.title}")
        Log.d("MyFcmService", "Notification Body :: ${remoteMessage.notification?.body}")
        Log.d("MyFcmService", "Notification Data :: ${remoteMessage.data}")
    }
}
