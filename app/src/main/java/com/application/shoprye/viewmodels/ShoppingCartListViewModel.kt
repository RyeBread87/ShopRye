package com.application.shoprye.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.application.shoprye.models.RyeJobAndShoppingCartEntry
import com.application.shoprye.repositories.ShoppingCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShoppingCartListViewModel @Inject internal constructor(
    private val shoppingCartRepository: ShoppingCartRepository
    ) : ViewModel() {

    val shoppingCartList: LiveData<List<RyeJobAndShoppingCartEntry>> =
        shoppingCartRepository.getShoppingCartEntries().asLiveData()
}