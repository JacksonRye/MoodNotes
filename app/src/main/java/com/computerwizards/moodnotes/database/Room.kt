package com.computerwizards.moodnotes.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("select * from note")
    fun getNotes(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

    @Query("select * from note where noteId = :noteId")
    fun get(noteId: Long): Note?

    @Query("select * from note order by noteId DESC limit 1")
    suspend fun getLastNote(): Note?

    @Query("delete from note")
    suspend fun clear()

}

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao
}

private lateinit var INSTANCE: NoteDatabase

fun getDatabase(context: Context): NoteDatabase {
    synchronized(NoteDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes"
            ).build()
        }
    }
    return INSTANCE
}

