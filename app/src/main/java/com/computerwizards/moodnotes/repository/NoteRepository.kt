package com.computerwizards.moodnotes.repository

import androidx.lifecycle.LiveData
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.database.NoteDao

class NoteRepository(private val noteDao: NoteDao) {


    val notes: LiveData<List<Note>> = noteDao.getNotes()

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun getLastNote(): Note? {
        return noteDao.getLastNote()
    }

    fun get(noteId: Long): Note? {
        return noteDao.get(noteId)
    }

    suspend fun clear() {
        noteDao.clear()
    }

    fun delete(note: Note) {
        noteDao.delete(note)
    }


}