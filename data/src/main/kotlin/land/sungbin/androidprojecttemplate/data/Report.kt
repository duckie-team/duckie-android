package land.sungbin.androidprojecttemplate.data

/**
 * 신고[Report] data class
 *
 * @param reporter 신고한 사람
 * @param reported 신고 당한 사람
 * @param reportedChat 신고 당한 글
 * @param message 신고 내용
 */
data class Report(
    val reporter: String,
    val reported: String,
    val reportedChat: String,
    val message: String,
)