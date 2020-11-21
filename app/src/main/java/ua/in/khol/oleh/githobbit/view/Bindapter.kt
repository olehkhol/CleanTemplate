package ua.`in`.khol.oleh.githobbit.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank())
        Picasso.get()
            .load(url)
            .into(this)
}

