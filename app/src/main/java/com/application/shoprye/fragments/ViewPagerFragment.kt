package com.application.shoprye.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.application.shoprye.R
import com.application.shoprye.adapters.SHOPPING_CART_PAGE_INDEX
import com.application.shoprye.adapters.RYE_JOB_LIST_PAGE_INDEX
import com.application.shoprye.adapters.PagingAdapter
import com.application.shoprye.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = PagingAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            SHOPPING_CART_PAGE_INDEX -> R.drawable.ic_shopping_cart
            RYE_JOB_LIST_PAGE_INDEX -> R.drawable.ic_jobs
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            SHOPPING_CART_PAGE_INDEX -> getString(R.string.shopping_cart)
            RYE_JOB_LIST_PAGE_INDEX -> getString(R.string.rye_jobs_list)
            else -> null
        }
    }
}