package com.computerwizards.moodnotes.online

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.computerwizards.moodnotes.database.RedditRepository
import com.computerwizards.moodnotes.database.getDatabase
import com.computerwizards.moodnotes.online.model.Response
import kotlinx.coroutines.launch
import java.io.IOException

enum class AdviceApiStatus { LOADING, ERROR, DONE }

class OnlineViewModel(application: Application) : AndroidViewModel(application) {



    private val redditRepository = RedditRepository(getDatabase(application))
    val redditList = redditRepository.reddits


    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    init {
        getListingFromRepository()
    }



    fun getListingFromRepository() {
        viewModelScope.launch {
            try {
                redditRepository.refreshReddits()
                Log.d("OnlineViewModel", "${redditList.value}")
            } catch (networkError: IOException) {
                Log.e("OnlineViewModel", "${networkError.message}")
            }
        }

    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnlineViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OnlineViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
