package ua.`in`.khol.oleh.githobbit.view.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class RepoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RepoLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: RepoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RepoLoadStateViewHolder {
        return RepoLoadStateViewHolder.from(parent, retry)
    }
}