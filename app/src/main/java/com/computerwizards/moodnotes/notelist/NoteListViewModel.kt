package com.computerwizards.moodnotes.notelist

import android.util.Log
import androidx.lifecycle.*
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.database.NoteDao
import com.computerwizards.moodnotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteListViewModel(private val dao: NoteDao) : ViewModel() {


    private val repository = NoteRepository(dao)

    val noteList = repository.notes

    private var lastNote = MutableLiveData<Note?>()

    private val _navigateToNote = MutableLiveData<Note?>()
    val navigateToNote: LiveData<Note?> = _navigateToNote


    init {
        initializeLastNote()
    }

    private fun initializeLastNote() {
        viewModelScope.launch {
            lastNote.value = getLastNoteFromDatabase()
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.clear()
        }
    }

    fun addNote() {
        Log.d("NoteListVM", "onAdd clicked")

        viewModelScope.launch {

            val note = Note()
            repository.insert(note)
            lastNote.value = getLastNoteFromDatabase()

            lastNote.value?.let {
                onNoteClicked(it)
            }


        }

    }

    private suspend fun getLastNoteFromDatabase() = repository.getLastNote()


    private fun onNoteClicked(note: Note) {
        _navigateToNote.value = note
    }

    fun onNavigated() {
        _navigateToNote.value = null
    }


    class Factory(private val dao: NoteDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteListViewModel(dao) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}


