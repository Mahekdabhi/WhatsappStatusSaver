package com.example.whatsappstatussaver.adapter

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.model.Status
import com.example.whatsappstatussaver.utils.Common
import com.squareup.picasso.Picasso
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.copyFileToDirectory
import java.io.File
import java.io.IOException

class ImageAdapter(imagesList: List<Status>, container: RelativeLayout) :
    RecyclerView.Adapter<ItemViewHolder>(){
    private  val  imagesList: List<Status>
    private  var  context: android.content.Context? = null
    private  val  container: RelativeLayout
    init {
        this.imagesList = imagesList
        this.container = container
    }
    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        context = parent.context
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }
    override  fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        val  status: Status = imagesList[position]
        Picasso.get().load(status.file).into(holder.imageView)
        holder.imageView.setOnClickListener(View.OnClickListener {
            val  path: String = imagesList[position].path
            val intent = Intent(context, com.example.whatsappstatussaver.ui.ImageView::class.java)
            intent.putExtra("file", path)
            context!!.startActivity(intent)
        })
        holder.save.setOnClickListener(View.OnClickListener {
            checkFolder()
            val  path: String = imagesList[position].path
            val  file = java.io.File(path)
            val destPath = Environment.getExternalStorageDirectory().absolutePath + Common.APP_DIR
            val  destFile = java.io.File(destPath)
            try {
                FileUtils.copyFileToDirectory(file, destFile)
            }catch (  e: java.io.IOException){
                e.printStackTrace()
            }
            MediaScannerConnection.scanFile(context, arrayOf(destPath + status.title), arrayOf("*/*"), object : MediaScannerConnectionClient{
                override  fun onMediaScannerConnected(){}
                override  fun onScanCompleted(path: String, uri: android.net.Uri){}
            })
            Toast.makeText(context, "Saved to:" + destPath + status.title, Toast.LENGTH_SHORT).show()
        })
        holder.share.setOnClickListener { v ->
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/jpg"
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                android.net.Uri.parse("file://" + status.file.absolutePath)
            )
            context!!.startActivity(Intent.createChooser(shareIntent, "Share image"))
        }
    }
    private   fun checkFolder(){
        val path = Environment.getExternalStorageDirectory().absolutePath + Common.APP_DIR
        val dir = java.io.File(path)
        var  isDirectoryCreated = dir.exists()
        if (!isDirectoryCreated){
            isDirectoryCreated = dir.mkdir()
        } else {
            android.util.Log.d("mahek", "created")
        }
    }
    override  fun getItemCount(): Int{
        return imagesList.size
    }
}
