package com.computerwizards.moodnotes.about

import android.util.Log
import androidx.lifecycle.*
import com.computerwizards.moodnotes.online.AdviceApi
import kotlinx.coroutines.launch

class AboutViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    init {
        getAdvice()
    }

    fun getAdvice() {
        viewModelScope.launch {
            try {
                _response.value = AdviceApi.adviceApiService.getAdvice().await().slip?.advice
                    ?: "Network Unavailable"
            } catch (networkError: Exception) {
                Log.e("AboutViewModel", "Failed to connect: ${networkError.message}")
            }
        }
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
                return AboutViewModel() as T
            } else throw IllegalArgumentException("unknown viewmodel")
        }

    }

}
