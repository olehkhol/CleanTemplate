package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.model.GodRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val godRepository: GodRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD: Int = 5
    }

    private val repos: ArrayList<Repo> = ArrayList()
    private lateinit var query: String
    private var isMoreProcessing: Boolean = false

    val reposLiveData: MutableLiveData<ArrayList<Repo>> = MutableLiveData()

    fun searchRepos(name: String) {
        query = name
        repos.clear()

        viewModelScope.launch {
            godRepository.searchRepositories(name).forEach {
                repos.add(Repo(it.name, it.stargazers_count))
            }
            reposLiveData.value = repos
        }
    }

    fun doListScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount)
            searchMoreRepos()
    }

    private fun searchMoreRepos() {
        if (!isMoreProcessing) { // TODO Find a better way to check if the coroutine is running
            isMoreProcessing = true
            viewModelScope.launch {
                val oldReposSize = repos.size
                godRepository.searchMoreRepositories(query).forEach {
                    repos.add(Repo(it.name, it.stargazers_count))
                }
                // Updates live data only if the size of the list changed
                if (oldReposSize < repos.size)
                    reposLiveData.value = repos

                isMoreProcessing = false
            }
        }
    }
}