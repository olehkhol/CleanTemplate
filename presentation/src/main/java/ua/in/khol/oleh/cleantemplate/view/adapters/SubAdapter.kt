package ua.`in`.khol.oleh.cleantemplate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.cleantemplate.databinding.SubItemBinding
import ua.`in`.khol.oleh.cleantemplate.domain.model.Sub

class SubAdapter :
    PagingDataAdapter<Sub, SubAdapter.SubViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SubViewHolder.from(parent)

    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Sub>() {

            override fun areItemsTheSame(oldItem: Sub, newItem: Sub) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Sub, newItem: Sub) =
                oldItem == newItem
        }
    }

    class SubViewHolder(private val binding: SubItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sub: Sub) {
            binding.sub = sub
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SubViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SubItemBinding.inflate(inflater, parent, false)

                return SubViewHolder(binding)
            }
        }
    }
}