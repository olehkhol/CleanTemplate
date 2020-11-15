package ua.`in`.khol.oleh.githobbit.github

import retrofit2.Call
import ua.`in`.khol.oleh.githobbit.github.dacl.Repos
import javax.inject.Inject

class GitHelper @Inject constructor(private val gitRetrofit: GitRetrofit) {
    companion object {
        private const val PAGE_SIZE: Int = 30 // by default
    }

    private var currentPage: Int = 0

    fun searchRepo(name: String): Call<Repos> {
        currentPage = 1
        return gitRetrofit.gitService.searchRepo(name, currentPage, PAGE_SIZE)
    }

    fun searchMore(name: String): Call<Repos> {
        currentPage++;
        return gitRetrofit.gitService.searchRepo(name, currentPage, PAGE_SIZE)
    }
}