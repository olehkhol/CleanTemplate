package ua.`in`.khol.oleh.githobbit.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.githobbit.MainApplication
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ActivityMainBinding
import ua.`in`.khol.oleh.githobbit.viewmodel.MainViewModel
import ua.`in`.khol.oleh.githobbit.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MainView : AppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var commonAdapter: CommonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(MainViewModel::class.java)

        viewManager = LinearLayoutManager(this)
        commonAdapter = CommonAdapter()
        recyclerView = binding.repositoryRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = commonAdapter
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = viewManager as LinearLayoutManager
                val totalItemCount = manager.itemCount
                val visibleItemCount = manager.childCount
                val lastVisibleItem = manager.findLastVisibleItemPosition()

                viewModel.doListScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        viewModel.reposLiveData.observe(this) {
            commonAdapter.setItems(it)
            commonAdapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        viewModel.reposLiveData.removeObservers(this)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search_view -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
