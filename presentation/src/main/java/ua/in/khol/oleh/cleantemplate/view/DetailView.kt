package ua.`in`.khol.oleh.cleantemplate.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ua.`in`.khol.oleh.cleantemplate.ExtraConstants.Companion.REPO
import ua.`in`.khol.oleh.cleantemplate.R
import ua.`in`.khol.oleh.cleantemplate.databinding.ViewDetailBinding
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo
import ua.`in`.khol.oleh.cleantemplate.view.adapters.SubAdapter
import ua.`in`.khol.oleh.cleantemplate.viewmodel.DetailViewModel

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