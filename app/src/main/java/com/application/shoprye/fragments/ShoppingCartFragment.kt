package com.application.shoprye.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.application.shoprye.MailClientChosenReceiver
import com.application.shoprye.R
import com.application.shoprye.ScrollingOffsetFixListener
import com.application.shoprye.fragments.ShoppingCartFragment.Callback
import com.application.shoprye.adapters.RYE_JOB_LIST_PAGE_INDEX
import com.application.shoprye.adapters.ShoppingCartAdapter
import com.application.shoprye.databinding.FragmentShoppingCartBinding
import com.application.shoprye.viewmodels.ShoppingCartListViewModel
import com.google.android.material.appbar.AppBarLayout
<<<<<<< HEAD
import com.google.android.material.floatingactionbutton.FloatingActionButton
=======
>>>>>>> f9d9a24441f44bfd23058c0af1d214a3fc0a905a
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingCartFragment : Fragment() {

    private val viewModel: ShoppingCartListViewModel by viewModels()
    private lateinit var binding: FragmentShoppingCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = ShoppingCartAdapter()
        binding = DataBindingUtil.inflate<FragmentShoppingCartBinding>(
            inflater,
            R.layout.fragment_shopping_cart,
            container,
            false
        ).apply {
            callback = Callback {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

                val uriText = ("mailto:" + Uri.encode(resources.getString(R.string.rye_email))
                        + "?subject=" + Uri.encode(resources.getString(R.string.rye_email_subject)) +
                        "&body=" + Uri.encode(getRequestEmailBody()))

                emailIntent.data = Uri.parse(uriText) // only email apps should handle this

                if (MailClientChosenReceiver.isSupported) {
                    startActivity(Intent.createChooser(
                        emailIntent,resources.getString(R.string.app_name),
                        MailClientChosenReceiver.getSharingSenderIntent(requireContext())
                        )
                    )
                }
                else {
                    startActivity(Intent.createChooser(emailIntent, resources.getString(R.string.app_name)))
                }
            }
        }
        binding.shoppingCartList.adapter = adapter
        binding.addRyeJob.setOnClickListener {
            navigateToRyeJobListPage()
        }

        subscribeUi(adapter, binding)
        getSoftButtonsBarSizePort(requireActivity())

        val appBarLayout = activity?.findViewById<View>(R.id.app_bar_layout) as AppBarLayout
        appBarLayout.addOnOffsetChangedListener(ScrollingOffsetFixListener(activity?.findViewById<View>(R.id.view_pager) as ViewPager2))

        return binding.root
    }

    private fun getRequestEmailBody(): String {
        val s = buildString {
            append("Hi Rye! I hereby request the below services of you:" + '\n' + '\n')
            viewModel.shoppingCartList.value!!.forEach { this.append("  -" + Html.fromHtml(it.ryeJob.name)
                    + ", for " + it.ryeJob.cost + '\n') }
            append('\n' + "Thanks!")
        }
        return s
    }

    private fun subscribeUi(adapter: ShoppingCartAdapter, binding: FragmentShoppingCartBinding) {
        viewModel.shoppingCartList.observe(viewLifecycleOwner) { result ->
            binding.hasSomethingInCart = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    private fun navigateToRyeJobListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            RYE_JOB_LIST_PAGE_INDEX
    }

    private fun getSoftButtonsBarSizePort(activity: FragmentActivity): Int {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            activity.windowManager.defaultDisplay.getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }

    fun interface Callback {
        fun send()
    }
}