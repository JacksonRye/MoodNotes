package com.computerwizards.moodnotes

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.computerwizards.moodnotes.database.Note
import com.computerwizards.moodnotes.online.model.Response


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)

    }
}

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