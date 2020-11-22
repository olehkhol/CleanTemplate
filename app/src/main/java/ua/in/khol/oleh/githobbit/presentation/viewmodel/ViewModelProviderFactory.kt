package ua.`in`.khol.oleh.githobbit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.`in`.khol.oleh.githobbit.data.github.GitRepository

class ViewModelProviderFactory(private val gitRepository: GitRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(gitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class name")
    }
}