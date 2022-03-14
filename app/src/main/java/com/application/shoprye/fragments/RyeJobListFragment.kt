package com.application.shoprye.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.application.shoprye.adapters.RyeJobsAdapter
import com.application.shoprye.databinding.FragmentRyeJobListBinding
import com.application.shoprye.viewmodels.RyeJobListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RyeJobListFragment : Fragment() {

    private val viewModel: RyeJobListViewModel by viewModels()
    private lateinit var binding: FragmentRyeJobListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRyeJobListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        val adapter = RyeJobsAdapter()
        binding.ryeJobRecyclerview.adapter = adapter
        viewModel.getAllRyeJobs()
        viewModel.ryeJobList.observe(viewLifecycleOwner) {
            adapter.setRyeJobs(it)
        }
        subscribeUi(adapter)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: RyeJobsAdapter) {
        viewModel.ryeJobList.observe(viewLifecycleOwner) { ryeJobs ->
            adapter.submitList(ryeJobs)
        }
    }
}