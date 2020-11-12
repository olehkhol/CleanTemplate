package ua.`in`.khol.oleh.githobbit.github

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ua.`in`.khol.oleh.githobbit.github.dacl.Repos

interface GitService {
    @GET("/search/repositories")
    fun searchRepo(@Query("q") q: String): Call<Repos>
}