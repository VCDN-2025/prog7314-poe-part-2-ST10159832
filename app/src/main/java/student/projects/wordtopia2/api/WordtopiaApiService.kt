package student.projects.wordtopia2.api

import retrofit2.Call
import retrofit2.http.*

data class ScoreRequest(
    val username: String,
    val points: Int
)

data class ScoreResponse(
    val username: String,
    val points: Int
)

interface WordtopiaApiService {
    @POST("/api/leaderboard/submit")
    fun submitScore(@Body request: ScoreRequest): Call<Void>

    @GET("/api/leaderboard/top")
    fun getTopScores(): Call<List<ScoreResponse>>
}