/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-android/blob/develop/LICENSE
 */

package team.duckie.app.android.data.terms.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import team.duckie.app.android.data._datasource.client
import team.duckie.app.android.data._exception.util.responseCatching
import team.duckie.app.android.data.terms.mapper.toDomain
import team.duckie.app.android.data.terms.model.TermsResponseData
import team.duckie.app.android.domain.terms.model.Terms
import team.duckie.app.android.domain.terms.repository.TermsRepository

class TermsRepositoryImpl @Inject constructor() : TermsRepository {
    override suspend fun get(version: String): Terms {
        val response = client.get("/terms/$version")
        return responseCatching(
            response = response.body(),
            parse = TermsResponseData::toDomain,
        )
    }
}
