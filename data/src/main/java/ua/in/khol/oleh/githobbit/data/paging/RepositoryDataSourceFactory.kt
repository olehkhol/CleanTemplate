package ua.`in`.khol.oleh.githobbit.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository
import javax.inject.Inject

class RepositoryDataSourceFactory @Inject constructor(
    private val gitRepository: GitRepository
) : DataSource.Factory<Int, Repository>() {

    private var query: String = ""
    private val dataSource = MutableLiveData<RepositoryDataSource>()

    override fun create(): DataSource<Int, Repository> {
        val repositoryDataSource = RepositoryDataSource(query, gitRepository)
        dataSource.postValue(repositoryDataSource)

        return repositoryDataSource
    }

    fun setQuery(query: String) {
        this.query = query
        dataSource.value?.refresh(query)
    }
}