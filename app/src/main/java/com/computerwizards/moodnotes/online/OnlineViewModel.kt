package com.computerwizards.moodnotes.online

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.computerwizards.moodnotes.database.RedditRepository
import com.computerwizards.moodnotes.database.getDatabase
import com.computerwizards.moodnotes.online.model.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException

enum class AdviceApiStatus { LOADING, ERROR, DONE }

class OnlineViewModel(application: Application) : AndroidViewModel(application) {


    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    private val redditRepository = RedditRepository(getDatabase(application))
    val redditList = redditRepository.reddits

    private val _status = MutableLiveData<AdviceApiStatus>()
    val status: LiveData<AdviceApiStatus> = _status

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    init {
        getAdvice()
        getListingFromRepository()
        redditList.observeForever(Observer {
            Log.d("OnlineViewModel", "now observing redditList: ${it.size}")
        })
    }

    fun getAdvice() {
        AdviceApi.adviceApiService.getAdvice().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("OnlineViewModel", "Error: ${t.message}")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                Log.d("OnlineViewModel", "${response.body()}")
                _response.value = response.body()
            }
        })

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
