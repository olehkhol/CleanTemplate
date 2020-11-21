package ua.`in`.khol.oleh.githobbit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.databinding.RepoItemBinding

class RepoAdapter : PagedListAdapter<Repo, RepoAdapter.MainViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder.from(parent)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.repoName == newItem.repoName && oldItem.starsCount == newItem.starsCount

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

    class MainViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(inflater, parent, false)

                return MainViewHolder(binding)
            }
        }
    }
}