package com.example.whatsappstatussaver.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.model.Status
import com.example.whatsappstatussaver.utils.Common
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.copyFileToDirectory
import java.io.File
import java.io.IOException

class VideoAdapter(videoList: List<Status>, container: RelativeLayout) :
    RecyclerView.Adapter<ItemViewHolder>() {
    private val videoList: List<Status>
    private var context: Context? = null
    private val container: RelativeLayout

    init {
        this.videoList = videoList
        this.container = container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val status: Status = videoList[position]
        Glide.with(context!!).asBitmap().load(status.file).into(holder.imageView)
        holder.imageView.setOnClickListener {
            val path: String = videoList[position].path
            val intent = Intent(context, com.example.whatsappstatussaver.ui.VideoView::class.java)
            intent.putExtra("file", path)
            context!!.startActivity(intent)
        }
        holder.share.setOnClickListener { v ->
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/mp4"
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.parse("file://" + status.file.absolutePath)
            )
            context!!.startActivity(Intent.createChooser(shareIntent, "Share image"))
        }
        val inflater = LayoutInflater.from(context)
        holder.share.setOnClickListener { v ->
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "video/mp4"
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.parse("file://" + status.file.absolutePath)
            )
            context!!.startActivity(Intent.createChooser(shareIntent, "Share image"))
        }
        holder.save.setOnClickListener {
            checkFolder()
            val path: String = videoList[position].path
            val file = File(path)
            val destPath = Environment.getExternalStorageDirectory().absolutePath + Common.APP_DIR
            val destFile = File(destPath)
            try {
                FileUtils.copyFileToDirectory(file, destFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            MediaScannerConnection.scanFile(
                context,
                arrayOf(destPath + status.title),
                arrayOf("*/*"),
                object : MediaScannerConnectionClient {
                    override fun onMediaScannerConnected() {}
                    override fun onScanCompleted(path: String, uri: Uri) {}
                })
            Toast.makeText(context, "Saved to:" + destPath + status.title, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    private fun checkFolder() {
        val path = Environment.getExternalStorageDirectory().absolutePath + Common.APP_DIR
        val dir = File(path)
        var isDirectoryCreated = dir.exists()
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir()
        } else {
            Log.d("mahek", "created")
        }
    }
}
