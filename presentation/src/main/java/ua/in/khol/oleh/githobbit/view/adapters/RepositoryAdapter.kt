package ua.`in`.khol.oleh.githobbit.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.githobbit.databinding.RepoItemBinding
import ua.`in`.khol.oleh.githobbit.domain.entity.Repository
import ua.`in`.khol.oleh.githobbit.viewmodel.events.SingleLiveEvent

class RepositoryAdapter :
    PagedListAdapter<Repository, RepositoryAdapter.RepoViewHolder>(DIFF_CALLBACK) {

    val clickedItem: SingleLiveEvent<Repository> = SingleLiveEvent()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepoViewHolder.from(parent)

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    clickedItem.value = it
                }
            })
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean =
                oldItem == newItem
        }
    }

    class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: Repository) {
            binding.repo = repository
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