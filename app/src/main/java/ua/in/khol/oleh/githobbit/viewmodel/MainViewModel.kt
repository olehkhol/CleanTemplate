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
    val reposLiveData: MutableLiveData<List<Repo>> = MutableLiveData()

    fun searchRepo(name: String) {
        val repos: ArrayList<Repo> = ArrayList()

        godRepository.searchRepo(name)
            .enqueue(object : Callback<Repos> {
                override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                    (response.body()?.items ?: ArrayList())
                        .forEach {
                            repos.add(Repo(it.name, it.stargazers_count))
                        }
                    reposLiveData.value = repos
                }

                override fun onFailure(call: Call<Repos>, t: Throwable) {
                }

            })
    }
}