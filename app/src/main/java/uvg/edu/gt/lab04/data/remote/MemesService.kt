package uvg.edu.gt.lab04.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.parameters
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import uvg.edu.gt.lab04.data.remote.dto.Meme

class MemesService (private val client : HttpClient){

    suspend fun getMemes() : List<Meme>{
        var memes : List<Meme>
        try {
            // Making Request
            val response : HttpResponse = client.request(HttpRoutes.GET_MEMES){
                method = HttpMethod.Get }

            // Translating JSON response
            val data : JSONObject = JSONObject(response.body() as String)

            // Checking response state
            memes = if( data.getBoolean("success")){
                val memesList = ArrayList<Meme>()
                val memesArray : JSONArray = data.getJSONObject("data")
                    .getJSONArray("memes")
                // Serializing each JSON meme object
                for (index in 0 until memesArray.length()){
                    memesList.add(Json.decodeFromString<Meme>(memesArray.getString(index)))
                    println(memesArray.getString(index))
                }
                memesList
            } else
                emptyList()

        } catch (e : RedirectResponseException){
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            memes = emptyList<Meme>()
        } catch (e: ClientRequestException){
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            memes = emptyList<Meme>()
        } catch (e: ServerResponseException){
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            memes = emptyList<Meme>()
        }
        return memes
    }

    suspend fun getTemplateWithCaption(templateId : String, upperText: String, bottomText : String) : String {
        var templateUrl : String
        try {
            // Making Request
            val response : HttpResponse = client.request(HttpRoutes.CAPTION_IMAGE){
                method = HttpMethod.Post
                url {
                    parameters.append("template_id", templateId)
                    parameters.append( "username", "smaugtur")
                    parameters.append("password", "soroArx5#")
                    parameters.append("text0", upperText)
                    parameters.append("text1", bottomText)
                }
            }

            // Translating JSON response
            val data : JSONObject = JSONObject(response.body() as String)

            println(templateId + upperText + bottomText)
            println(response.body() as String)

            // Checking response state
            templateUrl = if( data.getBoolean("success")){
                data.getJSONObject("data")
                    .getString("url")
            } else
                HttpRoutes.PLACE_HOLDER_IMAGE

        } catch (e : RedirectResponseException){
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            templateUrl = HttpRoutes.PLACE_HOLDER_IMAGE
        } catch (e: ClientRequestException){
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            templateUrl = HttpRoutes.PLACE_HOLDER_IMAGE
        } catch (e: ServerResponseException){
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            templateUrl = HttpRoutes.PLACE_HOLDER_IMAGE
        }
        return templateUrl
    }
}