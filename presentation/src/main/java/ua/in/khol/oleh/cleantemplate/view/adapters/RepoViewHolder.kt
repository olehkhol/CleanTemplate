package ua.`in`.khol.oleh.cleantemplate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.cleantemplate.databinding.RepoItemBinding
import ua.`in`.khol.oleh.cleantemplate.domain.model.Repo


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