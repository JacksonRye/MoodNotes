package com.computerwizards.moodnotes.journal.moodui

import androidx.lifecycle.*
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.database.NoteDao
import com.computerwizards.moodnotes.database.NoteRepository
import kotlinx.coroutines.launch

// TODO Work on MoodFragment Navigations


class MoodViewModel(dao: NoteDao, var note: Note) : ViewModel() {

    private val repository =
        NoteRepository(dao)

    private val _navigateToNote = MutableLiveData<Boolean?>()
    val navigateToNote: LiveData<Boolean?> = _navigateToNote

    fun navigateToNote() {
        _navigateToNote.value = true
    }

    fun navigateToNoteCompleted() {
        _navigateToNote.value = null
    }

    fun setMood(mood: String) {
        note.mood = mood
        viewModelScope.launch {
            repository.update(note)
        }
        navigateToNote()
    }


    class Factory(val dao: NoteDao, val note: Note) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MoodViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MoodViewModel(dao, note) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
