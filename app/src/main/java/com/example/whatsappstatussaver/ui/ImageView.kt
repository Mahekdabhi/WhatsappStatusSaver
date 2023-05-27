package com.example.whatsappstatussaver.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappstatussaver.R
import java.io.File

class ImageView : AppCompatActivity() {
    lateinit var myImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        val intent = intent
        val file1 = intent.getStringExtra("file")
        val file = file1?.let { File(it) }
        if (file?.exists() == true) {
            val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
            myImage = findViewById(R.id.image_view1)
            myImage.setImageBitmap(myBitmap)
        }
    }
}