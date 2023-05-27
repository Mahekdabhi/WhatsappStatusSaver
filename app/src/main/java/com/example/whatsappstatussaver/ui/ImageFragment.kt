package com.example.whatsappstatussaver.ui

import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.adapter.ImageAdapter
import com.example.whatsappstatussaver.model.Status
import com.example.whatsappstatussaver.utils.Common
import java.io.File
import java.util.*


class ImageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val imagesList: MutableList<Status> = ArrayList()
    private val handler = Handler()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var container: RelativeLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var messageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewImage)
        progressBar = view.findViewById(R.id.prgressBarImage)
        container = view.findViewById(R.id.image_container)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        messageTextView = view.findViewById(R.id.messageTextImage)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
            ContextCompat.getColor(requireActivity(), R.color.whats_App),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark)
        )
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener { getStatus() })
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(GridLayoutManager(activity, Common.GRID_COUNT))
        getStatus()
    }

    private fun getStatus() {
        if (Common.STATUS_DIRECTORY.exists()) {
            Thread {
                val statusFiles: Array<File> = Common.STATUS_DIRECTORY.listFiles()
                imagesList.clear()
                if (statusFiles.isNotEmpty()) {
                    Arrays.sort(statusFiles)
                    for (file in statusFiles) {
                        val status = Status(file, file.name, file.absolutePath)
                        if (!status.isVideo && status.title.endsWith(".jpg")) {
                            imagesList.add(status)
                        }
                    }
                    handler.post {
                        if (imagesList.size <= 0) {
                            messageTextView.visibility = View.VISIBLE
                            messageTextView.setText(R.string.no_files_found)
                        } else {
                            messageTextView.visibility = View.GONE
                            messageTextView.text = ""
                        }
                        imageAdapter = ImageAdapter(imagesList, container)
                        recyclerView.adapter = imageAdapter
                        imageAdapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    }
                } else {
                    handler.post {
                        progressBar.visibility = View.GONE
                        messageTextView.visibility = View.VISIBLE
                        messageTextView.setText(R.string.no_files_found)
                    }
                }
                swipeRefreshLayout.isRefreshing = false
            }.start()
        } else {
            messageTextView.visibility = View.VISIBLE
            messageTextView.setText(R.string.cant_find_whatsapp_dir)
            Toast.makeText(activity, getString(R.string.cant_find_whatsapp_dir), Toast.LENGTH_SHORT)
                .show()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}