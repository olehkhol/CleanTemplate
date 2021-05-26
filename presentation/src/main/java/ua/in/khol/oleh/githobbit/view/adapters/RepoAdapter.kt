package ua.`in`.khol.oleh.githobbit.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.githobbit.databinding.RepoItemBinding
import ua.`in`.khol.oleh.githobbit.domain.entity.Repo
import ua.`in`.khol.oleh.githobbit.viewmodel.events.SingleLiveEvent

class RepoAdapter :
    PagingDataAdapter<Repo, RepoAdapter.RepoViewHolder>(DIFF_CALLBACK) {

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

    class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RepoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(inflater, parent, false)

                return RepoViewHolder(binding)
            }
        }
    }
}