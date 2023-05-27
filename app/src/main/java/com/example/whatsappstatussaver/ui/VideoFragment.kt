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
import com.example.whatsappstatussaver.adapter.VideoAdapter
import com.example.whatsappstatussaver.model.Status
import com.example.whatsappstatussaver.utils.Common
import java.io.File
import java.util.*


class VideoFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var progressBar: ProgressBar? = null
    private val videoList: MutableList<Status> = ArrayList()
    private val handler = Handler()
    private var videoAdapter: VideoAdapter? = null
    private var container: RelativeLayout? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var messageTextView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerViewVideo)
        progressBar = view.findViewById(R.id.prgressBarVideo)
        container = view.findViewById(R.id.videos_container)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        messageTextView = view.findViewById(R.id.messageTextVideo)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
            ContextCompat.getColor(requireActivity(), R.color.whats_App),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark)
        )
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener { status })
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(GridLayoutManager(activity, Common.GRID_COUNT))
        status
        super.onViewCreated(view, savedInstanceState)
    }

    private val status: Unit
        private get() {
            if (Common.STATUS_DIRECTORY.exists()) {
                Thread {
                    val statusFiles: Array<File> =
                        Common.STATUS_DIRECTORY.listFiles()
                    videoList.clear()
                    if (statusFiles != null && statusFiles.size > 0) {
                        Arrays.sort(statusFiles)
                        for (file in statusFiles) {
                            val status =
                                Status(file, file.name, file.absolutePath)
                            if (status.isVideo) {
                                videoList.add(status)
                            }
                        }
                        handler.post {
                            if (videoList.size <= 0) {
                                messageTextView!!.visibility = View.VISIBLE
                                messageTextView!!.setText(R.string.no_files_found)
                            } else {
                                messageTextView!!.visibility = View.GONE
                                messageTextView!!.text = ""
                            }
                            videoAdapter = VideoAdapter(videoList, container!!)
                            recyclerView!!.adapter = videoAdapter
                            videoAdapter!!.notifyDataSetChanged()
                            progressBar!!.visibility = View.GONE
                        }
                    } else {
                        handler.post {
                            progressBar!!.visibility = View.GONE
                            messageTextView!!.visibility = View.VISIBLE
                            messageTextView!!.setText(R.string.no_files_found)
                            Toast.makeText(
                                activity,
                                getString(R.string.no_files_found),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    swipeRefreshLayout!!.isRefreshing = false
                }.start()
            } else {
                messageTextView!!.visibility = View.VISIBLE
                messageTextView!!.setText(R.string.cant_find_whatsapp_dir)
                Toast.makeText(
                    activity,
                    getString(R.string.cant_find_whatsapp_dir),
                    Toast.LENGTH_SHORT
                ).show()
                swipeRefreshLayout!!.isRefreshing = false
            }
        }
}
