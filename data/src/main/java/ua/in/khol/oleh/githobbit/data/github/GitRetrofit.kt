package ua.`in`.khol.oleh.githobbit.data.github

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitRetrofit {
    const val BASE_URL = "https://api.github.com/"

    val service: GitService = buildRetrofit().create(GitService::class.java)

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}