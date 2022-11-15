package land.sungbin.androidprojecttemplate.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import land.sungbin.androidprojecttemplate.data.Constants.BaseUrl
import land.sungbin.androidprojecttemplate.domain.model.constraint.Category
import javax.inject.Inject

class RemoteDataSource(
   @Inject private val client: HttpClient
){
   suspend fun getCategories() : List<Category>{
      /*val request = client.get {
         url(BaseUrl+"/router")
      }
      val body: List<Category> = request.body()
      */
      return listOf(
         Category.Celebrity,
         Category.Game,
      )
   }
}