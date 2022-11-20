package team.duckie.app.android.util

import java.util.Date

object DateUtil {
    private enum class TimeValue(val value: Int, val maximum: Int, val message: String) {
        SEC(60, 60, "분 전"),
        MIN(60, 24, "시간 전"),
        HOUR(24, 30, "일 전"),
        DAY(30, 12, "달 전"),
        MONTH(12, Int.MAX_VALUE, "년 전")
    }

    fun formatTimeString(date: Date): String {
        val currentTime = System.currentTimeMillis()
        val beforeTime = date.time
        var diffTime = (currentTime - beforeTime) / 1000
        var message = ""

        if (diffTime < TimeValue.SEC.value) {
            message = "방금 전"
        } else {
            for (time in TimeValue.values()) {
                diffTime /= time.value
                if (diffTime < time.maximum) {
                    message = "${diffTime}${time.message}"
                    break
                }
            }
        }
        return message
    }
}
