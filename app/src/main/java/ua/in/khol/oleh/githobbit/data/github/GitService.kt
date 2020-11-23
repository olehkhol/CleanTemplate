package ua.`in`.khol.oleh.githobbit.data.github

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.`in`.khol.oleh.githobbit.data.github.dacl.SearchRepositoryResponse
import ua.`in`.khol.oleh.githobbit.data.github.dacl.SubscriberItem

interface GitService {

    // @GET("search/repositories?sort=stars") // don't need this sort option anymore
    @GET("search/repositories")
    suspend fun searchRepositoriesAsync(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchRepositoryResponse


    // "https://api.github.com/repos/open-android/Android/subscribers"
    @GET("repos/{owner}/{repo}/subscribers")
    suspend fun getSubscribers(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ArrayList<SubscriberItem>
}