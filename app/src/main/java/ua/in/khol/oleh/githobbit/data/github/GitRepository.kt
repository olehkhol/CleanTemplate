package ua.`in`.khol.oleh.githobbit.data.github

import android.util.Log
import retrofit2.HttpException
import ua.`in`.khol.oleh.githobbit.data.github.dacl.RepositoryItem
import ua.`in`.khol.oleh.githobbit.data.github.dacl.SubscriberItem
import javax.inject.Inject

class GitRepository @Inject constructor(private val gitRetrofit: GitRetrofit) {

    companion object {
        private val TAG: String? = GitRepository::class.java.name
    }

    suspend fun search(name: String, page: Int, pageSize: Int): List<RepositoryItem> {
        return try {
            gitRetrofit.service.searchRepositoriesAsync(name, page, pageSize).items
        } catch (e: HttpException) {
            // TODO Replace this logging with a return event or something to help notify the user
            Log.e(TAG, e.printStackTrace().toString())
            // Just returns an empty list
            ArrayList()
        }
    }

    suspend fun get(owner: String, repo: String, page: Int, pageSize: Int): List<SubscriberItem> {
        return try {
            gitRetrofit.service.getSubscribers(owner, repo, page, pageSize)
        } catch (e: HttpException) {
            Log.e(TAG, e.printStackTrace().toString())
            ArrayList()
        }
    }
}