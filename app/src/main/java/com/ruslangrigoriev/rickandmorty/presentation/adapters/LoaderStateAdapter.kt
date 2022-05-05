package com.ruslangrigoriev.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.ItemLoadStateFooterBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoaderViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_state_footer, parent, false)
        return LoaderViewHolder(view, retry)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoaderViewHolder(
        private val view: View,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(loadState: LoadState) {
            val binding = ItemLoadStateFooterBinding.bind(view)
            with(binding) {
                footerProgressBar.isVisible = loadState is LoadState.Loading
                footerRetryBtn.isVisible = loadState is LoadState.Error
                footerRetryBtn.setOnClickListener { retry.invoke() }
                footerErrorTextView.isVisible = loadState is LoadState.Error
                if (loadState is LoadState.Error) {
                    footerErrorTextView.text =
                        "Сheck internet connection and RETRY or refresh list to go offline"
                }
            }
        }
    }
}