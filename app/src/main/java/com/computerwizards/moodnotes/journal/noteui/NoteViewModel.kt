package com.computerwizards.moodnotes.journal.noteui

import androidx.lifecycle.*
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.database.NoteDao
import com.computerwizards.moodnotes.database.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(dao: NoteDao, private val note: Note) : ViewModel() {


    private val repository =
        NoteRepository(dao)

    private val _selectedNote = MutableLiveData<Note>()
    val selectedNote: LiveData<Note> = _selectedNote

    private val _saveNote = MutableLiveData<Boolean?>()
    val saveNote: LiveData<Boolean?> = _saveNote

    private val _navigateToMood = MutableLiveData<Boolean?>()
    val navigateToMood: LiveData<Boolean?> = _navigateToMood


    init {
        _selectedNote.value = note
    }


    fun onSave() {
        viewModelScope.launch {
            repository.insert(note)
        }
        _saveNote.value = true
    }

    fun savedNote() {
        _saveNote.value = null
    }

    fun navigateToMood() {
        _navigateToMood.value = true
    }

    fun navigateToMoodComplete() {
        _navigateToMood.value = null
    }

    fun onCancel() {

    }


    class Factory(private val dao: NoteDao, private val note: Note) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(dao, note) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}
