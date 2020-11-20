package ua.`in`.khol.oleh.githobbit.github

import android.util.Log
import retrofit2.HttpException
import ua.`in`.khol.oleh.githobbit.github.dacl.Item
import javax.inject.Inject

class GitHelper @Inject constructor(private val gitRetrofit: GitRetrofit) {

    companion object {
        private val TAG: String? = GitHelper::class.java.name
        private const val PAGE_SIZE: Int = 30 // default by Github api
    }

    private var currentPage: Int = 0

    suspend fun searchRepositories(name: String): List<Item> {
        currentPage = 1

        return searchRepositoriesAsync(name)
    }

    suspend fun searchMoreRepositories(name: String): List<Item> {
        return searchRepositoriesAsync(name)
    }

    private suspend fun searchRepositoriesAsync(name: String): List<Item> {
        return try {
            gitRetrofit.service.searchRepositoriesAsync(name, currentPage++, PAGE_SIZE).items
        } catch (e: HttpException) {
            // TODO Replace this logging with a return event or something to help notify the user
            Log.e(TAG, e.printStackTrace().toString())
            // Decreases page number back
            currentPage--
            // Just returns an empty list
            ArrayList()
        }
    }
}