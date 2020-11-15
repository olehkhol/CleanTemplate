package ua.`in`.khol.oleh.githobbit.github

import retrofit2.Call
import ua.`in`.khol.oleh.githobbit.github.dacl.Repos
import javax.inject.Inject

class GitHelper @Inject constructor(private val gitRetrofit: GitRetrofit) {
    fun searchRepo(name: String): Call<Repos> {
        return gitRetrofit.service.searchRepo(name)
    }
}