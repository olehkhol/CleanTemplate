package ua.`in`.khol.oleh.githobbit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetRepos
import ua.`in`.khol.oleh.githobbit.domain.usecase.contract.GetSubs

class ViewModelFactory(private val getRepos: GetRepos, private val getSubs: GetSubs) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                MainViewModel(getRepos) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                @Suppress("UNCHECKED_CAST")
                DetailViewModel(getSubs) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
}