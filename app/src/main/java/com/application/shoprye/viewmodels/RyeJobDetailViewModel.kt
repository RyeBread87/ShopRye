package com.application.shoprye.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.application.shoprye.repositories.RyeJobRepository
import com.application.shoprye.repositories.ShoppingCartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RyeJobDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    ryeJobRepository: RyeJobRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {

    private val ryeJobIdString: String? = savedStateHandle.get<String>(RYE_JOB_ID_SAVED_STATE_KEY)
    val ryeJobId = if (ryeJobIdString == null) 1 else Integer.parseInt(ryeJobIdString)  // doing this to make a test work
                                                                                        // TODO - find a better way to do this?
    val isInCart = shoppingCartRepository.isInCart(ryeJobId).asLiveData()
    val ryeJob = ryeJobRepository.getRyeJob(ryeJobId).asLiveData()

    fun addToShoppingCart() {
        viewModelScope.launch {
            shoppingCartRepository.createShoppingCartEntry(ryeJobId)
        }
    }

    companion object {
        private const val RYE_JOB_ID_SAVED_STATE_KEY = "ryeJobId"
    }
}