package com.application.shoprye.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.shoprye.fragments.RyeJobListFragment
import com.application.shoprye.fragments.ShoppingCartFragment

const val SHOPPING_CART_PAGE_INDEX = 0
const val RYE_JOB_LIST_PAGE_INDEX = 1

class PagingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        SHOPPING_CART_PAGE_INDEX to { ShoppingCartFragment() },
        RYE_JOB_LIST_PAGE_INDEX to { RyeJobListFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}