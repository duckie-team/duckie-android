/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.core.sync

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

object NotificationProvider {

    private const val DEFAULT_CHANNEL_ID = "DUCKIE_CHANNEL"
    private const val DEFAULT_CHANNEL_NAME = "Duckie"

    private val DEFAULT_NOTIFICATION_SOUND =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    private const val DEFAULT_NOTIFICATION_PUSH_ID = 1
    private const val DEFAULT_NOTIFICATION_TITLE = "Duckie"
    private const val DEFAULT_NOTIFICATION_BODY = "덕키에서 회원님에게 알람이 도착했어요!"
    private val DEFAULT_NOTIFICATION_VIBRATE = LongArray(2)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String): NotificationChannel {
        return NotificationChannel(
            channelId,
            DEFAULT_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            enableLights(true)
            enableVibration(true)
        }
    }

    fun sendNotification(
        context: Context,
        title: String?,
        body: String?,
        intent: PendingIntent,
        messageId: String?,
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_logo)
            .setColor(context.getColor(R.color.duckie_orange))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentTitle(title ?: DEFAULT_NOTIFICATION_TITLE)
            .setContentText(body ?: DEFAULT_NOTIFICATION_BODY)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setSound(DEFAULT_NOTIFICATION_SOUND)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setVibrate(DEFAULT_NOTIFICATION_VIBRATE)
            .setAutoCancel(true)
            .setFullScreenIntent(intent, true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel(messageId ?: DEFAULT_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(
            DEFAULT_NOTIFICATION_PUSH_ID,
            notificationBuilder.build(),
        )
    }
}
