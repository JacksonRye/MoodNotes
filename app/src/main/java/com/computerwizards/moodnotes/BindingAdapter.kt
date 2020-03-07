package com.computerwizards.moodnotes

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.online.model.Response


@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: List<Note>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("setMood")
fun ImageView.setMood(note: Note?) {
    note?.let {
        setImageResource(
            when (note.mood) {
                "happy" -> R.drawable.ic_happy
                "sad" -> R.drawable.ic_sad
                "in_love" -> R.drawable.ic_in_love
                else -> R.drawable.ic_cool
            }
        )
    }

}

@BindingAdapter("setAdvice")
fun setAdvice(text: TextView, Response: Response?) {

    Response?.let {
        text.text = it.slip?.advice
    }

}