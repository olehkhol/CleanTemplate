package ua.`in`.khol.oleh.githobbit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.githobbit.data.Repo
import ua.`in`.khol.oleh.githobbit.databinding.RepositoryCommonItemBinding

class CommonAdapter :
    RecyclerView.Adapter<CommonAdapter.CommonHolder>() {

    private var repos = ArrayList<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder {
        return CommonHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CommonHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun setItems(items: List<Repo>) {
        repos.clear()
        repos.addAll(items)
    }

    class CommonHolder(private val binding: RepositoryCommonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CommonHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RepositoryCommonItemBinding.inflate(inflater, parent, false)

                return CommonHolder(binding)
            }
        }
    }
}