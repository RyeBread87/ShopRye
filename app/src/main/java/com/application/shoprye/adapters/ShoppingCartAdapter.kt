package com.application.shoprye.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.application.shoprye.R
import com.application.shoprye.databinding.ShoppingCartEntryBinding
import com.application.shoprye.fragments.ViewPagerFragmentDirections
import com.application.shoprye.models.RyeJobAndShoppingCartEntry
import com.application.shoprye.viewmodels.RyeJobAndShoppingCartEntryViewModel

class ShoppingCartAdapter : ListAdapter<RyeJobAndShoppingCartEntry, ShoppingCartAdapter.ViewHolder>(
        ShoppingCartDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.shopping_cart_entry,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ShoppingCartEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.viewModel?.ryeJobId?.let { ryeJobId ->
                    navigateToRyeJob(ryeJobId, view)
                }
            }
        }

        private fun navigateToRyeJob(ryeJobId: Int, view: View) {
            val direction = ViewPagerFragmentDirections
                .actionViewPagerFragmentToRyeJobDetailFragment(ryeJobId.toString())
            view.findNavController().navigate(direction)
        }

        fun bind(ryeJobAndShoppingCartEntry: RyeJobAndShoppingCartEntry) {
            if (ryeJobAndShoppingCartEntry.shoppingCartEntryList.isEmpty()) {
                return
            }
            with(binding) {
                viewModel = RyeJobAndShoppingCartEntryViewModel(ryeJobAndShoppingCartEntry)
                executePendingBindings()
            }
        }
    }

    private class ShoppingCartDiffCallback : DiffUtil.ItemCallback<RyeJobAndShoppingCartEntry>() {

        override fun areItemsTheSame(
            oldItem: RyeJobAndShoppingCartEntry,
            newItem: RyeJobAndShoppingCartEntry
        ): Boolean {
            return oldItem.ryeJob.id == newItem.ryeJob.id
        }

        override fun areContentsTheSame(
            oldItem: RyeJobAndShoppingCartEntry,
            newItem: RyeJobAndShoppingCartEntry
        ): Boolean {
            return oldItem.ryeJob == newItem.ryeJob
        }
    }
}