package com.example.whatsappstatussaver.ui

import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.adapter.SavedAdapter
import com.example.whatsappstatussaver.model.Status
import com.example.whatsappstatussaver.utils.Common
import java.io.File
import java.util.*


class SavedFilesFragment() : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val savedFilesList: MutableList<Status> = ArrayList<Status>()
    private val handler = Handler()
    private lateinit var savedAdapter: SavedAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var no_files_found: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewFiles)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutFiles)
        progressBar = view.findViewById(R.id.progressBar)
        no_files_found = view.findViewById(R.id.no_files_found)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
            ContextCompat.getColor(requireActivity(), R.color.white),
            ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark)
        )
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener { files })
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(GridLayoutManager(activity, Common.GRID_COUNT))
        files
    }//  Toast.makeText(getActivity(), "handler.post", Toast.LENGTH_LONG).show();

    //    private Bitmap getThumbnail(Status status) {
    //      Toast.makeText(getActivity(), " Arrays.sort(savedFiles);t", Toast.LENGTH_LONG).show();
    private val files: Unit
        private get() {
            val app_dir = File(
                Environment.getExternalStorageDirectory().toString() +
                        File.separator + Common.APP_DIR
            )
            if (app_dir.exists()) {
                no_files_found.visibility = View.GONE
                Thread {
                    val savedFiles: Array<File>? = app_dir.listFiles()
                    savedFilesList.clear()
                    if (savedFiles != null && savedFiles.size > 0) {
                        Arrays.sort(savedFiles)
                        //      Toast.makeText(getActivity(), " Arrays.sort(savedFiles);t", Toast.LENGTH_LONG).show();
                        for (file: File in savedFiles) {
                            val status: Status =
                                Status(file, file.name, file.absolutePath)
                            savedFilesList.add(status)
                        }
                        handler.post(Runnable {

                            //  Toast.makeText(getActivity(), "handler.post", Toast.LENGTH_LONG).show();
                            savedAdapter = SavedAdapter(savedFilesList)
                            recyclerView.setAdapter(savedAdapter)
                            savedAdapter.notifyDataSetChanged()
                            progressBar.setVisibility(View.GONE)
                        })
                    } else {
                        handler.post(Runnable {
                            progressBar.setVisibility(View.GONE)
                            no_files_found.setVisibility(View.VISIBLE)
                        })
                    }
                    swipeRefreshLayout.setRefreshing(false)
                }.start()
            } else {
                Toast.makeText(activity, "Dir doest not exists", Toast.LENGTH_SHORT).show()
                no_files_found.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    //        return a.gautham.statusdownloader.Utils.ThumbnailUtils.createVideoThumbnail(status.getFile().getAbsolutePath(),
    //                3);
    //    }
}
