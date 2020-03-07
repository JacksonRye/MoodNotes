package com.computerwizards.moodnotes.online

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.computerwizards.moodnotes.online.model.RedditResponse
import com.computerwizards.moodnotes.online.model.Response
import com.computerwizards.moodnotes.online.model.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback

enum class AdviceApiStatus { LOADING, ERROR, DONE }

class OnlineViewModel : ViewModel() {


    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )
    private val _status = MutableLiveData<AdviceApiStatus>()
    val status: LiveData<AdviceApiStatus> = _status

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    init {
        getAdvice()
        getListing()
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

    fun getListing() {
        RedditApi.redditApiService.getListing().enqueue(object : Callback<RedditResponse> {
            override fun onFailure(call: Call<RedditResponse>, t: Throwable) {
                Log.e("OnlineViewModel", "Reddit Error: ${t.message}")
            }

            override fun onResponse(
                call: Call<RedditResponse>,
                response: retrofit2.Response<RedditResponse>
            ) {
                Log.d("OnlineViewModel", "RedditResponse: ${response.body()?.asDomainModel()}")
            }
        })
    }

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnlineViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OnlineViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
