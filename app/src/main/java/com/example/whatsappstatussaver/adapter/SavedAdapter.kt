package com.example.whatsappstatussaver.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.model.Status
import com.google.android.gms.ads.rewarded.RewardedAd
import com.squareup.picasso.Picasso

/*class SavedAdapter(imagesList: List<Status>) :
    RecyclerView.Adapter<ItemViewHolder>() {
    private val imagesList: List<Status>
    private var context: Context? = null
    private val rewardedAd: RewardedAd? = null

    init {
        this.imagesList = imagesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.save.setImageDrawable(
            ContextCompat.getDrawable(
                context!!,
                R.drawable.ic_baseline_delete_24
            )
        )
        holder.share.visibility = View.VISIBLE
        holder.save.visibility = View.VISIBLE
        val status = imagesList[position]

        if (status.isVideo) Glide.with(context!!).asBitmap().load(status.file).into(holder.imageView)
        else Picasso.get().load(status.file).into(holder.imageView)
        holder.save.setOnClickListener { view ->
            if (status.file.delete()) {
                imagesList.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(context, "Unable to Delete File", Toast.LENGTH_SHORT).show()
        }
        holder.imageView.setOnClickListener {
            val path: String = imagesList[position].path
            val intent: Intent
            intent = if (!imagesList[position].isVideo) {
                Intent(context, ImageView::class.java)
            } else {
                Intent(context, VideoView::class.java)
            }
            intent.putExtra("file", path)
            context!!.startActivity(intent)
        }
        holder.share.setOnClickListener { v ->
            val shareIntent = Intent(Intent.ACTION_SEND)
            if (status.isVideo) shareIntent.type = "image/mp4" else shareIntent.type = "image/jpg"
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.parse("file://" + status.file.absolutePath)
            )
            context!!.startActivity(Intent.createChooser(shareIntent, "Share image"))
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}*/

class SavedAdapter(private val imagesList: MutableList<Status>) :
    RecyclerView.Adapter<ItemViewHolder>() {

    private lateinit var context: Context
    private lateinit var rewardedAd: RewardedAd

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.save.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24))
        holder.share.visibility = View.VISIBLE
        holder.save.visibility = View.VISIBLE

        val status = imagesList[position]

        if (status.isVideo)
            Glide.with(context).asBitmap().load(status.file).into(holder.imageView)
        else
            Picasso.get().load(status.file).into(holder.imageView)

        holder.save.setOnClickListener {
            if (status.file.delete()) {
                imagesList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, imagesList.size)
                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, "Unable to Delete File", Toast.LENGTH_SHORT).show()
        }

        holder.imageView.setOnClickListener {
            val path = imagesList[position].path
            val intent: Intent = if (!imagesList[position].isVideo) {
                Intent(context, com.example.whatsappstatussaver.ui.ImageView::class.java)
            } else {
                Intent(context, com.example.whatsappstatussaver.ui.VideoView::class.java)
            }
            intent.putExtra("file", path)
            context.startActivity(intent)
        }

        holder.share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            if (status.isVideo)
                shareIntent.type = "image/mp4"
            else
                shareIntent.type = "image/jpg"
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.file.absolutePath))
            context.startActivity(Intent.createChooser(shareIntent, "Share image"))
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}
