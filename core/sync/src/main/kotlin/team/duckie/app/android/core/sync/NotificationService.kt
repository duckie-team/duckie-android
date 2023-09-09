/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

@file:Suppress("unused")

package team.duckie.app.android.core.sync

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import team.duckie.app.android.domain.device.usecase.DeviceRegisterUseCase
import team.duckie.app.android.presentation.IntroActivity
import javax.inject.Inject


@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var deviceRegisterUseCase: DeviceRegisterUseCase

    private val job = SupervisorJob()
    private val externalScope = CoroutineScope(Dispatchers.IO + job)

    override fun onNewToken(token: String) {
        Log.d("MyDeviceToken", token)
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        externalScope.launch {
            deviceRegisterUseCase(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("MyFcmService", "Notification Title :: ${remoteMessage.notification?.title}")
        Log.d("MyFcmService", "Notification Body :: ${remoteMessage.notification?.body}")
        Log.d("MyFcmService", "Notification Data :: ${remoteMessage.data}")

        val intent = Intent(this, IntroActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        remoteMessage.notification?.let { notification ->
            NotificationProvider.sendNotification(
                context = this,
                title = notification.title,
                body = notification.body,
                intent = pendingIntent,
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
