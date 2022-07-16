package ua.`in`.khol.oleh.cleantemplate.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ua.`in`.khol.oleh.cleantemplate.databinding.LoadStateBinding

class RepoLoadStateViewHolder(
    private val binding: LoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error)
            binding.errorMsg.text = loadState.error.localizedMessage
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): RepoLoadStateViewHolder {
            val binding = LoadStateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return RepoLoadStateViewHolder(binding, retry)
        }
    }

}