package ua.`in`.khol.oleh.githobbit.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ViewDetailBinding
import ua.`in`.khol.oleh.githobbit.di.ApplicationInjector
import ua.`in`.khol.oleh.githobbit.domain.Repository
import ua.`in`.khol.oleh.githobbit.presentation.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.githobbit.presentation.view.adapters.SubscriberAdapter
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.DetailViewModel
import ua.`in`.khol.oleh.githobbit.presentation.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class DetailView : AppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: DetailViewModel
    private lateinit var subscriberAdapter: SubscriberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationInjector.get().inject(this)
        super.onCreate(savedInstanceState)

        val binding: ViewDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.view_detail)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(DetailViewModel::class.java)

        subscriberAdapter = SubscriberAdapter()
        binding.subscribersRecycler.apply {
            adapter = subscriberAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }

        (intent.getSerializableExtra(REPO) as Repository).let {
            binding.repo = it
            viewModel.getSubscribers(it.ownerName, it.repoName)
        }

    }

    override fun onStart() {
        super.onStart()

        viewModel.subscriberList.observe(this) {
            subscriberAdapter.submitList(it)
        }
    }

    override fun onStop() {
        viewModel.subscriberList.removeObservers(this)

        super.onStop()
    }
}