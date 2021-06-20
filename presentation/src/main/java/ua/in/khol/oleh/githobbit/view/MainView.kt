package ua.`in`.khol.oleh.githobbit.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.`in`.khol.oleh.githobbit.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.githobbit.MainApplication
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ViewMainBinding
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.view.adapters.RepoAdapter
import ua.`in`.khol.oleh.githobbit.view.adapters.RepoLoadStateAdapter
import ua.`in`.khol.oleh.githobbit.viewmodel.MainViewModel
import ua.`in`.khol.oleh.githobbit.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainView : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainView: ViewMainBinding
    private var repoAdapter: RepoAdapter = RepoAdapter()

    private lateinit var searchQuery: String
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).daggerComponent.inject(this)
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainView = ViewMainBinding.inflate(layoutInflater)
            .also { binding ->
                setContentView(binding.root)
            }

        // Listening to LoadState to change the UI at the beginning of the load
        repoAdapter.addLoadStateListener { loadState: CombinedLoadStates ->
            // TODO adapt in the future for the RemoteMediator too
            val refreshState = loadState.source.refresh
            val appendState = loadState.source.append
            val prependState = loadState.source.prepend

            // Only show the list if refresh succeeds
            mainView.recyclerView.isVisible = refreshState is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            mainView.progressBar.isVisible = refreshState is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            mainView.retryButton.isVisible = refreshState is LoadState.Error

            val isListEmpty = refreshState is LoadState.NotLoading && repoAdapter.itemCount == 0
            val errorState = refreshState as? LoadState.Error
                ?: appendState as? LoadState.Error
                ?: prependState as? LoadState.Error
            // Toast any error or show 'No result'
            errorState?.let {
                Toast.makeText(
                    this,
                    "ERROR: ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            } ?: showNoResult(isListEmpty)
        }

        // Setup the repo's recycler view
        mainView.recyclerView.adapter = repoAdapter
            // Displaying the loading state in a footer
            .withLoadStateHeaderAndFooter(RepoLoadStateAdapter { repoAdapter.retry() },
                RepoLoadStateAdapter { repoAdapter.retry() })
        mainView.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainView.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        // The retry button should trigger a reload of the PagingData
        mainView.retryButton.setOnClickListener { repoAdapter.retry() }

        search(savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY)
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
            mainViewModel.searchRepo(query)
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
