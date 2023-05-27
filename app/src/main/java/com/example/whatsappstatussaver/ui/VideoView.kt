package com.example.whatsappstatussaver.ui

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappstatussaver.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.io.File

class VideoView : AppCompatActivity() {
    lateinit var mediaControls: MediaController
    lateinit var videoView: android.widget.VideoView
    private var mRewardedAd: RewardedAd?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        mediaControls = MediaController(this)
        MobileAds.initialize(
            this
        ) { }
//        rewAd()
        videoView = findViewById(R.id.video_view)
        val intent = intent
        val file1 = intent.getStringExtra("file")
        val file = File(file1)
        videoView.setVideoURI(Uri.parse(file.toString()))
        if (mediaControls == null) {
            mediaControls = MediaController(this@VideoView)
            mediaControls.setAnchorView(videoView)
        }
        videoView.setMediaController(mediaControls)
        videoView.setVideoURI(Uri.parse(file.toString()))
        videoView.start()
        videoView.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            Toast.makeText(
                applicationContext,
                "Oops An Error Occur While Playing Video...!!!",
                Toast.LENGTH_LONG
            ).show()
            false
        })
    }

/*    fun rewAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, "ca-app-pub-7545086986980596/2012368674",
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error.
                    Log.d("TAG", loadAdError.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    Log.d("TAG", "Ad was loaded.")
                    videoView.pause()
                    if (mRewardedAd != null) {
                        val activityContext: Activity = this@VideoView
                        mRewardedAd.show(
                            activityContext
                        ) { rewardItem -> // Handle the reward.
                            Log.d("TAG", "The user earned the reward.")
                            val rewardAmount = rewardItem.amount
                            val rewardType = rewardItem.type
                        }
                    } else {
                        Log.d("TAG", "The rewarded ad wasn't ready yet.")
                    }
                }
            })
    }*/

   /* fun rewAd() {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this, "ca-app-pub-7545086986980596/2012368674",
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error.
                    Log.d("TAG", loadAdError.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    Log.d("TAG", "Ad was loaded.")
                    videoView.pause()

                    if (mRewardedAd != null) {
                        val activityContext = this@VideoView
                        mRewardedAd?.show(activityContext, OnUserEarnedRewardListener { rewardItem ->
                            // Handle the reward.
                            Log.d("TAG", "The user earned the reward.")
                            val rewardAmount = rewardItem.amount
                            val rewardType = rewardItem.type
                        })
                    } else {
                        Log.d("TAG", "The rewarded ad wasn't ready yet.")
                    }
                }
            })
    }
*/
}