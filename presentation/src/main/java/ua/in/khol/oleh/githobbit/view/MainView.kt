package ua.`in`.khol.oleh.githobbit.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.githobbit.MainApplication
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ViewMainBinding
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.view.adapters.RepoAdapter
import ua.`in`.khol.oleh.githobbit.viewmodel.MainViewModel
import ua.`in`.khol.oleh.githobbit.viewmodel.ViewModelFactory
import javax.inject.Inject

private const val DEFAULT_QUERY = "Android"
private const val LAST_SEARCH_QUERY = "last_search_query"

class MainView : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainView: ViewMainBinding
    private lateinit var repoAdapter: RepoAdapter

    private lateinit var searchQuery: String
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).daggerComponent.inject(this)
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainView = ViewMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        repoAdapter = RepoAdapter()

        mainView.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        mainView.recyclerView.adapter = repoAdapter
        mainView.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        search(savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, searchQuery)
    }

    override fun onResume() {
        super.onResume()

        repoAdapter.clickedItem.observe(this) { item -> startDetailActivity(item) }
    }

    override fun onPause() {
        repoAdapter.clickedItem.removeObservers(this)

        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        (menu.findItem(R.id.menu_search_view)?.actionView as SearchView).apply {
            queryHint = getString(R.string.query_hint)
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    search(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        return true
    }

    private fun search(query: String) {
        searchQuery = query
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            mainViewModel.searchRepo(query)
                .collect { pagingData ->
                    repoAdapter.submitData(pagingData)
                }
        }
    }

    private fun startDetailActivity(repo: Repo?) = repo?.let {
        val intent = Intent(this, DetailView::class.java)
        intent.putExtra(REPO, repo)
        startActivity(intent)
    }
}
