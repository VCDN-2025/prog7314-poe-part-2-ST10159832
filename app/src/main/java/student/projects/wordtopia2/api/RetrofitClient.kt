package student.projects.wordtopia2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://wordtopia-api-305153713749.us-central1.run.app/"

    val instance: WordtopiaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WordtopiaApiService::class.java)
    }
}