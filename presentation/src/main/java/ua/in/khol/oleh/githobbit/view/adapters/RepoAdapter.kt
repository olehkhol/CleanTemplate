package ua.`in`.khol.oleh.githobbit.view.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ua.`in`.khol.oleh.githobbit.domain.model.Repo
import ua.`in`.khol.oleh.githobbit.viewmodel.events.SingleLiveEvent
import javax.inject.Inject

class RepoAdapter @Inject constructor() :
    PagingDataAdapter<Repo, RepoViewHolder>(DIFF_CALLBACK) {

    val clickedItem: SingleLiveEvent<Repo> = SingleLiveEvent()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoViewHolder.from(parent)

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener { clickedItem.value = item }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {

            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem == newItem
        }
    }
}