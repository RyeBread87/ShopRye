package com.application.shoprye.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.shoprye.models.RyeJob
import com.application.shoprye.repositories.RyeJobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RyeJobListViewModel @Inject internal constructor(
    private val mainRepository: RyeJobRepository
    ) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val ryeJobList = MutableLiveData<List<RyeJob>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    fun getAllRyeJobs() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            mainRepository.insertAllRyeJobs()
            val response = mainRepository.getAllRyeJobs()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    ryeJobList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}