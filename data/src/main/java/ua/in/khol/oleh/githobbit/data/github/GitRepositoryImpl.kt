package ua.`in`.khol.oleh.githobbit.data.github

import android.util.Log
import ua.`in`.khol.oleh.githobbit.data.mappers.GitMapper
import retrofit2.HttpException
import ua.`in`.khol.oleh.githobbit.domain.models.Repository
import ua.`in`.khol.oleh.githobbit.domain.models.Subscriber
import ua.`in`.khol.oleh.githobbit.domain.repositories.GitRepository

class GitRepositoryImpl(
    private val gitRetrofit: GitRetrofit,
    private val gitMapper: GitMapper
) : GitRepository {

    companion object {
        private val TAG: String? = GitRepositoryImpl::class.java.name
    }

    override suspend fun search(name: String, page: Int, pageSize: Int): List<Repository> {
        return try {
            gitRetrofit.service.searchRepositoriesAsync(name, page, pageSize).items
                // TODO replace object with static
                .map { gitMapper.asRepository(it) }
        } catch (e: HttpException) {
            // TODO Replace this logging with a return event or something to help notify the user
            Log.e(TAG, e.printStackTrace().toString())
            // Just returns an empty list
            ArrayList()
        }
    }

    override suspend fun get(
        owner: String,
        repo: String,
        page: Int,
        pageSize: Int
    ): List<Subscriber> {
        return try {
            gitRetrofit.service.getSubscribers(owner, repo, page, pageSize)
                .map { gitMapper.asSubscriber(it) }
        } catch (e: HttpException) {
            Log.e(TAG, e.printStackTrace().toString())
            ArrayList()
        }
    }
}