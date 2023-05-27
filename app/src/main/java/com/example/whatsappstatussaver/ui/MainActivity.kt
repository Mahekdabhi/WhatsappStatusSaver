package com.example.whatsappstatussaver.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.adapter.PageAdapter
import com.google.android.gms.ads.*

import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private var back_pressed: Long = 0
    lateinit var mAdView : AdView

    private val AD_UNIT_ID = "ca-app-pub-4889775654215028/3838524610"

    private val mAppUnitId: String by lazy {

        "ca-app-pub-4889775654215028~3897400211"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        MobileAds.initialize(this)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        /* MobileAds.initialize(this, object : OnInitializationCompleteListener {
             override fun onInitializationComplete(initializationStatus: InitializationStatus) {
                 val testDeviceIds = listOf(AdRequest.DEVICE_ID_EMULATOR, "YOUR_TEST_DEVICE_ID")
                 val requestConfiguration = RequestConfiguration.Builder()
                     .setTestDeviceIds(testDeviceIds)
                     .build()
                 MobileAds.setRequestConfiguration(requestConfiguration)

                 // Load an ad.
                 val adRequest = AdRequest.Builder().build()
                 adView.loadAd(adRequest)
             }
         })*/


        /* adView.adListener = object: AdListener() {
             override fun onAdClicked() {
                 // Code to be executed when the user clicks on an ad.
                 Log.e("adViewwwww", "onAdLoaded:${onAdClicked()} ", )
             }

             override fun onAdClosed() {
                 // Code to be executed when the user is about to return
                 // to the app after tapping on an ad.
                 Log.e("adViewwwww", "onAdLoaded:${onAdClosed()} ", )
             }

             override fun onAdFailedToLoad(adError : LoadAdError) {
                 // Code to be executed when an ad request fails.
                 Log.e("adViewwwww", "onAdLoaded:${onAdFailedToLoad(adError)} ", )
             }

             override fun onAdImpression() {
                 // Code to be executed when an impression is recorded
                 // for an ad.'
                 Log.e("adViewwwww", "onAdLoaded:${onAdImpression()} ", )
             }

             override fun onAdLoaded() {
                 Log.e("adViewwwww", "onAdLoaded:${onAdLoaded()} ", )
                 // Code to be executed when an ad finishes loading.
             }

             override fun onAdOpened() {
                 // Code to be executed when an ad opens an overlay that
                 // covers the screen.
                 Log.e("adViewwwww", "onAdLoaded:${onAdOpened()} ", )
             }
         }*/

//        val mAdView = findViewById<AdView>(R.id.adView)
//        MobileAds.initialize(this) {}
//        val adRequest = AdRequest.Builder().build()
//        mAdView.loadAd(adRequest)

//        val testDeviceIds = listOf("YOUR_TEST_DEVICE_ID")
//        val config = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
//        MobileAds.setRequestConfiguration(config)
//
//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)

/*
        MobileAds.initialize(this)

        // Add test device ID(s) to the RequestConfiguration
        val config = RequestConfiguration.Builder()
            .setTestDeviceIds(listOf("60AC56F79029EC817D4A74585D266447"))
            .build()

        MobileAds.setRequestConfiguration(config)

        // Load the ad
        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)



*/

//        Log.e("adddd", "onCreate:${mAdView.loadAd(adRequest)} ", )
//
//        initializeBannerAd()
//
//        loadBannerAd()


        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.image)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.video)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.save)))
        val adapter: PagerAdapter = PageAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.setAdapter(adapter)
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

/*    private fun initializeBannerAd() {

        MobileAds.initialize(this, mAppUnitId)

    }

    private fun loadBannerAd() {

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }*/

    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish()
            moveTaskToBack(true)
        } else {
            Snackbar.make(viewPager!!, "Press Again to Exit", Snackbar.LENGTH_LONG).show()
            back_pressed = System.currentTimeMillis()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_aboutUs -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data =
                    Uri.parse("mailto:mahekdabhi7678@gmail.com")
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                startActivity(Intent.createChooser(emailIntent, "Contact us!"))
                true
            }
            R.id.menu_privacyPolicy -> {
                startActivity(Intent(applicationContext, PrivacyPolicy::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS = 1234
        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"
    }
}
