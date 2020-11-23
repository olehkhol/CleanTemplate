package ua.`in`.khol.oleh.githobbit.domain.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ua.`in`.khol.oleh.githobbit.data.github.GitRepository
import ua.`in`.khol.oleh.githobbit.domain.Repository

class RepositoryDataSourceFactory(
    private var query: String = "",
    private val repository: GitRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Repository>() {

    private val sourceLiveData = MutableLiveData<RepositoryDataSource>()

    override fun create(): DataSource<Int, Repository> {
        val repoDataSource = RepositoryDataSource(query, repository, scope)
        sourceLiveData.postValue(repoDataSource)

        return repoDataSource
    }

    fun setQuery(query: String) {
        this.query = query
        sourceLiveData.value?.refresh()
    }
}