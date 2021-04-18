package ua.`in`.khol.oleh.githobbit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.`in`.khol.oleh.githobbit.data.paging.RepositoryDataSourceFactory

class ViewModelProviderFactory(
    private val repositoryDataSourceFactory: RepositoryDataSourceFactory
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repositoryDataSourceFactory) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class name")
        }
    }
}