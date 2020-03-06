package com.computerwizards.moodnotes.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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