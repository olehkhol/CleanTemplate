package ua.`in`.khol.oleh.githobbit.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.`in`.khol.oleh.githobbit.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ViewMainBinding
import ua.`in`.khol.oleh.githobbit.di.ApplicationInjector
import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.view.adapters.RepositoryAdapter
import ua.`in`.khol.oleh.githobbit.viewmodel.MainViewModel
import ua.`in`.khol.oleh.githobbit.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class MainView : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var pagedAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationInjector.get().inject(this)
        super.onCreate(savedInstanceState)

        val binding: ViewMainBinding =
            DataBindingUtil.setContentView(this, R.layout.view_main)

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        pagedAdapter = RepositoryAdapter()
        binding.recycler.apply {
            adapter = pagedAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.repositories.observe(this) {
            pagedAdapter.submitList(it)
        }
        pagedAdapter.clickedItem.observe(this) {
            //startDetailActivity(it)
        }
    }

    override fun onStop() {
        pagedAdapter.clickedItem.removeObservers(this)
        viewModel.repositories.removeObservers(this)

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
