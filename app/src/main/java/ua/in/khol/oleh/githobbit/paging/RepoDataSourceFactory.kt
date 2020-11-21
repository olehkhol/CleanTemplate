package ua.`in`.khol.oleh.githobbit.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.github.GitRepository

class RepoDataSourceFactory(
    private var query: String = "",
    private val repository: GitRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Repo>() {

    private val sourceLiveData = MutableLiveData<RepoDataSource>()

    override fun create(): DataSource<Int, Repo> {
        val repoDataSource = RepoDataSource(query, repository, scope)
        sourceLiveData.postValue(repoDataSource)

        return repoDataSource
    }

    fun setQuery(query: String) {
        this.query = query
        sourceLiveData.value?.refresh()
    }
}