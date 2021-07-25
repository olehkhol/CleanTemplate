package ua.`in`.khol.oleh.githobbit.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ua.`in`.khol.oleh.githobbit.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.githobbit.R
import ua.`in`.khol.oleh.githobbit.databinding.ViewDetailBinding
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.view.adapters.SubAdapter
import ua.`in`.khol.oleh.githobbit.viewmodel.DetailViewModel

@AndroidEntryPoint
class DetailView : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var subAdapter: SubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO HILT (applicationContext as MainApplication).daggerComponent.inject(this)
        super.onCreate(savedInstanceState)

        val binding: ViewDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.view_detail)

        subAdapter = SubAdapter()
        binding.subscribersRecycler.apply {
            adapter = subAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }

        (intent.getSerializableExtra(REPO) as Repo).let {
            binding.repo = it
//            viewModel.getSubscribers(it.ownerName, it.repoName)
        }

    }

    override fun onStart() {
        super.onStart()

//        viewModel.subList.observe(this) {
//            subscriberAdapter.submitList(it)
    }

    override fun onStop() {
//        viewModel.subList.removeObservers(this)
//
        super.onStop()
    }
}