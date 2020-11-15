package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.github.dacl.Repos
import ua.`in`.khol.oleh.githobbit.model.GodRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val godRepository: GodRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THRESHOLD: Int = 5
    }

    val reposLiveData: MutableLiveData<ArrayList<Repo>> = MutableLiveData()
    lateinit var query: String

    fun searchRepo(name: String) {
        val repos: ArrayList<Repo> = ArrayList()

        godRepository.searchRepo(name)
            .enqueue(object : Callback<Repos> {
                override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                    (response.body()?.items ?: ArrayList())
                        .forEach {
                            repos.add(Repo(it.name, it.stargazers_count))
                        }
                    query = name
                    reposLiveData.value = repos
                }

                override fun onFailure(call: Call<Repos>, t: Throwable) {
                }

            })
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val repos: ArrayList<Repo> = ArrayList()
            godRepository.searchMore(query)
                .enqueue(object : Callback<Repos> {
                    override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                        (response.body()?.items ?: ArrayList())
                            .forEach {
                                repos.add(Repo(it.name, it.stargazers_count))
                            }

                        reposLiveData.value?.addAll(repos)
                        reposLiveData.value = reposLiveData.value
                    }

                    override fun onFailure(call: Call<Repos>, t: Throwable) {
                    }

                })
        }
    }
}