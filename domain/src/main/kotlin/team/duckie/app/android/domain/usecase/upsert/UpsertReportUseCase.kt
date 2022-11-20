package team.duckie.app.android.domain.usecase.upsert

import im.toss.util.tuid.tuid
import team.duckie.app.domain.model.Report
import team.duckie.app.domain.model.User
import team.duckie.app.domain.model.util.FK
import team.duckie.app.domain.repository.UpsertRepository
import team.duckie.app.domain.repository.result.DuckApiResult
import team.duckie.app.domain.repository.result.runDuckApiCatching

class UpsertReportUseCase(
    private val repository: UpsertRepository,
) {
    /**
     * [신고][Report] 정보를 생성하거나 업데이트합니다.
     *
     * 기존에 등록된 정보가 없다면 새로 생성하고, 그렇지 않다면
     * 기존에 등록된 정보를 업데이트합니다.
     *
     * @param reporterId 신고자 [유저 아이디][User.nickname]
     * @param targetId 신고당한 [유저 아이디][User.nickname]
     * @param targetContentId 신고당한 컨텐츠 아이디.
     * 유저를 신고했을 경우엔 컨텐츠가 [targetId] 자체가 되므로 null 을 허용합니다.
     * @param message 신고 메시지.
     * **신고 메시지는 공백을 허용하지 않습니다.**
     *
     * @return Upsert 결과.
     * Upsert 결과는 반환 값이 없으므로 [Nothing] 타입의 [DuckApiResult] 를 을 반환합니다.
     */
    suspend operator fun invoke(
        @FK reporterId: String,
        @FK targetId: String,
        @FK targetContentId: String?,
        message: String,
    ) = runDuckApiCatching {
        repository.upsertReport(
            report = Report(
                id = tuid(),
                reporterId = reporterId,
                targetId = targetId,
                targetContentId = targetContentId,
                message = message,
                checked = false,
            ),
        )
    }
}
