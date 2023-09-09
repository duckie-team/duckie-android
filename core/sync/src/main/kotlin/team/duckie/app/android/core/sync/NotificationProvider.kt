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
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

object NotificationProvider {

    private const val CHANNEL_ID = "DUCKIE_CHANNEL"

    private const val CHANNEL_NAME = "Duckie"

    private val NOTIFICATION_SOUND =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    private const val PUSH_NOTIFICATION_ID = 100

    private const val DEFAULT_NOTIFICATION_TITLE = "Duckie"
    private const val DEFAULT_NOTIFICATION_BODY = "덕키에서 회원님에게 알람이 도착했어요!"


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            enableLights(true)
            enableVibration(true)
        }
    }

    fun sendNotification(context: Context, title: String?, body: String?, intent: PendingIntent) {
        val largeIconBitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_notification_logo)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_logo)
            .setLargeIcon(largeIconBitmap)
            .setColor(context.getColor(R.color.duckie_orange))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(title ?: DEFAULT_NOTIFICATION_TITLE)
            .setContentText(body ?: DEFAULT_NOTIFICATION_BODY)
            .setSound(NOTIFICATION_SOUND)
            .setAutoCancel(true)
            .setContentIntent(intent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createNotificationChannel()
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(
            PUSH_NOTIFICATION_ID,
            notificationBuilder.build()
        )
    }
}
