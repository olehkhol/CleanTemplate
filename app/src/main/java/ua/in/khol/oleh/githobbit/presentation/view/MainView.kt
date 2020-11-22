package ua.`in`.khol.oleh.githobbit.presentation.view

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
import ua.`in`.khol.oleh.githobbit.databinding.ActivityMainBinding
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.MainViewModel
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MainView : AppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var repoAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(MainViewModel::class.java)

        repoAdapter = RepoAdapter()
        binding.repositoryRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = repoAdapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.repoList.observe(this) { repoAdapter.submitList(it) }
    }

    override fun onStop() {
        viewModel.repoList.removeObservers(this)

        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchView = (menu?.findItem(R.id.menu_search_view)?.actionView as SearchView)
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchRepos(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }
}
