package ua.`in`.khol.oleh.githobbit.github

import retrofit2.http.GET
import retrofit2.http.Query
import ua.`in`.khol.oleh.githobbit.github.dacl.SearchResponse

interface GitService {

    // @GET("search/repositories?sort=stars") // don't need this sort option anymore
    @GET("search/repositories")
    suspend fun searchRepositoriesAsync(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResponse
}