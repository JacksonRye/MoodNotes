package com.computerwizards.moodnotes.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.computerwizards.moodnotes.online.model.RedditItem
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0L,

    var title: String = "",

    var message: String = "",

    var mood: String = "cool"

) : Parcelable

@Entity
data class Reddit constructor(
    @PrimaryKey
    val title: String,
    val subreddit: String,
    val thumbnail: String
)


// Map Reddits to domain entities
fun List<Reddit>.asDomainModel(): List<RedditItem> {
    return map {
        RedditItem(
            title = it.title,
            subreddit = it.subreddit,
            thumbnail = it.thumbnail
        )

    }
}