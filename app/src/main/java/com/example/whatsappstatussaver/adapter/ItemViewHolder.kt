package com.example.whatsappstatussaver.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappstatussaver.R


class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var save: ImageButton
    var share: ImageButton
    var imageView: ImageView

    init {
        imageView = itemView.findViewById(R.id.ivThumbnail)
        save = itemView.findViewById(R.id.save)
        share = itemView.findViewById(R.id.share)
    }
}