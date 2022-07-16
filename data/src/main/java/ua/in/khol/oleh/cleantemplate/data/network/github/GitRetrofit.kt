package ua.`in`.khol.oleh.cleantemplate.data.network.github

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com/"

// Wrapper class to avoid unnecessary Retrofit2 dependency in 'presenter' module
class GitRetrofit(private val client: OkHttpClient) {

    val service: GitService = build()
        .create(GitService::class.java)

    private fun build(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}