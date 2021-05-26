package ua.`in`.khol.oleh.githobbit.data.network.github

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SearchRepositoryResponse
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SubscriberItem

interface GitService {

    @GET("search/repositories")
    suspend fun searchRepos(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchRepositoryResponse

    // "https://api.github.com/repos/open-android/Android/subscribers"
    @GET("repos/{owner}/{repo}/subscribers")
    suspend fun getSubs(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ArrayList<SubscriberItem>
}