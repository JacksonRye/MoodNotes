package com.computerwizards.moodnotes.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.computerwizards.moodnotes.online.model.ChildData
import com.computerwizards.moodnotes.online.model.SingleData
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
    val subreddit: String
)


// Map Reddits to domain entities
fun List<Reddit>.asDomainModel(): List<ChildData> {
    return map {
        ChildData(
            data = SingleData(it.subreddit, it.title)
        )
    }
}