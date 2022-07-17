package ua.`in`.khol.oleh.cleantemplate.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.cleantemplate.presentation.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.cleantemplate.R
import ua.`in`.khol.oleh.cleantemplate.databinding.ViewMainBinding
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo
import ua.`in`.khol.oleh.cleantemplate.presentation.view.adapters.RepoAdapter
import ua.`in`.khol.oleh.cleantemplate.presentation.view.adapters.RepoLoadStateAdapter
import ua.`in`.khol.oleh.cleantemplate.presentation.viewmodel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainView : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mainView: ViewMainBinding

    @Inject
    lateinit var repoAdapter: RepoAdapter

    private lateinit var searchQuery: String
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView =
            ViewMainBinding.inflate(layoutInflater).also { binding -> setContentView(binding.root) }

        // Setup the repo's recycler view
        mainView.recyclerView.apply {
            adapter = repoAdapter
                // Displaying the loading state in a footer
                .withLoadStateHeaderAndFooter(RepoLoadStateAdapter { repoAdapter.retry() },
                    RepoLoadStateAdapter { repoAdapter.retry() })
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        // The retry button should trigger a reload of the PagingData
        mainView.retryButton.setOnClickListener { repoAdapter.retry() }

        search(savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY)
    }

    override fun onStart() {
        super.onStart()

        // Listening to LoadState to change the UI at the beginning of the load
        repoAdapter.addLoadStateListener { loadState: CombinedLoadStates ->

            val mediatorRefreshState = loadState.mediator?.refresh
            val mediatorAppendState = loadState.mediator?.append
            val mediatorPrependState = loadState.mediator?.prepend

            // Only show the list if refresh succeeds
            mainView.recyclerView.isVisible = mediatorRefreshState is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            mainView.progressBar.isVisible = mediatorRefreshState is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            mainView.retryButton.isVisible = mediatorRefreshState is LoadState.Error

            val isListEmpty =
                mediatorRefreshState is LoadState.NotLoading && repoAdapter.itemCount == 0
            val errorState = mediatorRefreshState as? LoadState.Error
                ?: mediatorAppendState as? LoadState.Error
                ?: mediatorPrependState as? LoadState.Error
            // Toast any error or show 'No result'
            errorState?.let {
                Toast.makeText(
                    this,
                    "ERROR: ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            } ?: showNoResult(isListEmpty)
        }
    }

    private fun showNoResult(isListEmpty: Boolean) {
        if (isListEmpty) {
            mainView.emptyList.visibility = View.VISIBLE
            mainView.recyclerView.visibility = View.GONE
        } else {
            mainView.emptyList.visibility = View.GONE
            mainView.recyclerView.visibility = View.VISIBLE
        }
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
            mainViewModel
                .searchRepo(query)
                .collectLatest { pagingData ->
                    repoAdapter.submitData(pagingData)
                }
        }
    }

    private fun startDetailActivity(repo: Repo?) = repo?.let {
        val intent = Intent(this, DetailView::class.java)
        intent.putExtra(REPO, repo)
        startActivity(intent)
    }

    companion object {
        private const val DEFAULT_QUERY = "Android"
        private const val LAST_SEARCH_QUERY = "last_search_query"
    }
}
