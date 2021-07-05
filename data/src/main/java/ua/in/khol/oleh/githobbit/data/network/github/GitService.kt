package ua.`in`.khol.oleh.githobbit.data.network.github

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SearchRepoResponse
import ua.`in`.khol.oleh.githobbit.data.network.github.serialized.SubItem

interface GitService {

    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchRepoResponse

    // "https://api.github.com/repos/open-android/Android/subscribers"
    @GET("repos/{owner}/{repo}/subscribers")
    suspend fun getSubs(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ArrayList<SubItem>

    companion object {
        const val START_PAGE: Int = 1
        const val PAGE_SIZE: Int = 10
    }
}