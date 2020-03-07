package com.computerwizards.moodnotes.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.computerwizards.moodnotes.online.RedditApi
import com.computerwizards.moodnotes.online.model.ChildData
import com.computerwizards.moodnotes.online.model.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class RedditRepository(private val database: NoteDatabase) {

    val reddits: LiveData<List<ChildData>> = Transformations.map(database.redditDao.getReddits()) {
        it.asDomainModel()
    }

    suspend fun refreshReddits() {
        withContext(Dispatchers.IO) {
            Log.d("RedditR", "refresh videos called")
            val redditList = RedditApi.redditApiService.getListing().await()
            database.redditDao.insertAll(redditList.asDatabaseModel())
        }
    }

}