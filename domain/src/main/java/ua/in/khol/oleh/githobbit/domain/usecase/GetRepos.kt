package ua.`in`.khol.oleh.githobbit.domain.usecase

import ua.`in`.khol.oleh.githobbit.domain.repository.contract.GitRepository

class GetRepos(private val repository: GitRepository) {

    fun getSearchResultStream(query: String) = repository.getSearchResultStream(query)
}