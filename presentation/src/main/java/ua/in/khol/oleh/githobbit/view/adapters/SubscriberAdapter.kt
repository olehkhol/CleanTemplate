package ua.`in`.khol.oleh.githobbit.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.githobbit.databinding.SubscribersItemBinding
import ua.`in`.khol.oleh.githobbit.domain.entity.Subscriber

class SubscriberAdapter :
    PagedListAdapter<Subscriber, SubscriberAdapter.SubscriberVieHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SubscriberVieHolder.from(parent)

    override fun onBindViewHolder(holder: SubscriberVieHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Subscriber>() {
            override fun areItemsTheSame(oldItem: Subscriber, newItem: Subscriber): Boolean {
                return oldItem.id == newItem.id
            }


            override fun areContentsTheSame(oldItem: Subscriber, newItem: Subscriber): Boolean {
                return oldItem == newItem
            }
        }
    }

    class SubscriberVieHolder(private val binding: SubscribersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subscriber: Subscriber) {
            binding.subscriber = subscriber
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SubscriberVieHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SubscribersItemBinding.inflate(inflater, parent, false)

                return SubscriberVieHolder(binding)
            }
        }
    }
}