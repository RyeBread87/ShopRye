package com.application.shoprye.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.application.shoprye.databinding.RyeJobItemBinding
import com.application.shoprye.fragments.ViewPagerFragmentDirections
import com.application.shoprye.models.RyeJob
import com.bumptech.glide.Glide

class RyeJobsAdapter : ListAdapter<RyeJob, RecyclerView.ViewHolder>(RyeJobDiffCallback()) {

    var ryeJobList = mutableListOf<RyeJob>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRyeJobs(ryeJobs: List<RyeJob>) {
        this.ryeJobList = ryeJobs.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RyeJobViewHolder(
            RyeJobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private fun myBind(holder: RyeJobViewHolder, position: Int) {
        val ryeJob = ryeJobList[position]

        holder.binding.ryeJobTitle.text = ryeJob.name
        Glide.with(holder.itemView.context).load(ryeJob.imageUrl).into(holder.binding.ryeJobItemImage)
    }

    override fun getItemCount(): Int {
        return ryeJobList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ryeJob = getItem(position)
        myBind(holder as RyeJobViewHolder, position)
        (holder).bind(ryeJob)
    }

    class RyeJobViewHolder (
        val binding: RyeJobItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.ryeJob?.let { ryeJob ->
                    navigateToRyeJob(ryeJob, it)
                }
            }
        }

        private fun navigateToRyeJob(
            ryeJob: RyeJob,
            view: View
        ) {
            val direction =
                ViewPagerFragmentDirections.actionViewPagerFragmentToRyeJobDetailFragment(
                    ryeJob.id.toString()
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: RyeJob) {
            binding.apply {
                ryeJob = item
                executePendingBindings()
            }
        }
    }
}

private class RyeJobDiffCallback : DiffUtil.ItemCallback<RyeJob>() {
    override fun areItemsTheSame(oldItem: RyeJob, newItem: RyeJob): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RyeJob, newItem: RyeJob): Boolean {
        return oldItem == newItem
    }
}