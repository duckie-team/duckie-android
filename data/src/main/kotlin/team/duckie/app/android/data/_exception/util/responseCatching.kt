/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._exception.util

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

// FIXME(sungbin): 역직렬화 로직이 잘못됨
/**
 * Caused by: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException:
 * Unrecognized field "isNewUser" (class team.duckie.app.android.data._exception.model.ExceptionBody),
 * not marked as ignorable (3 known properties: "errors", "code", "message"])
 * at [Source: (InputStreamReader); line: 1, column: 479] (through reference chain: team.duckie.app.android.data._exception.model.ExceptionBody["isNewUser"])
 */
@Suppress("TooGenericExceptionCaught")
internal suspend inline fun <reified DataModel, DomainModel> responseCatching(
    response: HttpResponse,
    parse: (body: DataModel) -> DomainModel,
): DomainModel {
    /*return try {
        val body: DataModel = response.body()
        parse(body)
    } catch (throwable: Throwable) {
        val errorBody: ExceptionBody = response.body()
        errorBody.throwing(throwable = throwable)
    }*/
    val body: DataModel = response.body()
    return parse(body)
}

// FIXME(sungbin): 역직렬화 로직이 잘못됨
/**
 * Caused by: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException:
 * Unrecognized field "isNewUser" (class team.duckie.app.android.data._exception.model.ExceptionBody),
 * not marked as ignorable (3 known properties: "errors", "code", "message"])
 * at [Source: (InputStreamReader); line: 1, column: 479] (through reference chain: team.duckie.app.android.data._exception.model.ExceptionBody["isNewUser"])
 */
@Suppress("TooGenericExceptionCaught")
internal inline fun <DomainModel> responseCatching(
    response: String,
    parse: (body: String) -> DomainModel,
): DomainModel {
    /*return try {
        parse(response)
    } catch (throwable: Throwable) {
        // TODO(riflockle7): 개발 도중 에러를 확인하기 위해 추가한 코드. 추후 논의를 통해 제거 필요
        throwable.printStackTrace()
        val errorBody = jsonMapper.readValue(response, ExceptionBody::class.java)
        errorBody.throwing(throwable = throwable)
    }*/
    return parse(response)
}
