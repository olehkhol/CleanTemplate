package ua.`in`.khol.oleh.githobbit.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.`in`.khol.oleh.githobbit.MainApplication
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ViewMainBinding
import ua.`in`.khol.oleh.githobbit.domain.Repository
import ua.`in`.khol.oleh.githobbit.presentation.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.githobbit.presentation.view.adapters.RepositoryAdapter
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.MainViewModel
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MainView : AppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ViewMainBinding =
            DataBindingUtil.setContentView(this, R.layout.view_main)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(MainViewModel::class.java)

        repositoryAdapter = RepositoryAdapter()
        binding.repositoriesRecycler.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.repositoryList.observe(this) {
            repositoryAdapter.submitList(it)
        }
        repositoryAdapter.clickedItem.observe(this) {
            startDetailActivity(it)
        }
    }

    override fun onStop() {
        repositoryAdapter.clickedItem.removeObservers(this)
        viewModel.repositoryList.removeObservers(this)

        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        (menu?.findItem(R.id.menu_search_view)?.actionView as SearchView).apply {
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchRepos(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        return true
    }

    private fun startDetailActivity(repository: Repository?) = repository?.let {
        val intent = Intent(this, DetailView::class.java)
        intent.putExtra(REPO, repository)
        startActivity(intent)
    }
}
