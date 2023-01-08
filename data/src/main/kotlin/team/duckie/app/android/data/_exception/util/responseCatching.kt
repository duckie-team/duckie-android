/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data._exception.util

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import team.duckie.app.android.data._exception.model.ExceptionBody
import team.duckie.app.android.data._exception.model.throwing
import team.duckie.app.android.data._util.jsonMapper

@Suppress("TooGenericExceptionCaught")
internal suspend inline fun <reified DataModel, DomainModel> responseCatching(
    response: HttpResponse,
    parse: (body: DataModel) -> DomainModel,
): DomainModel {
    return try {
        val body: DataModel = response.body()
        parse(body)
    } catch (throwable: Throwable) {
        val errorBody: ExceptionBody = response.body()
        errorBody.throwing(throwable = throwable)
    }
}

@Suppress("TooGenericExceptionCaught")
internal inline fun <DomainModel> responseCatching(
    response: String,
    parse: (body: String) -> DomainModel,
): DomainModel {
    return try {
        parse(response)
    } catch (throwable: Throwable) {
        val errorBody = jsonMapper.readValue(response, ExceptionBody::class.java)
        errorBody.throwing(throwable = throwable)
    }
}
