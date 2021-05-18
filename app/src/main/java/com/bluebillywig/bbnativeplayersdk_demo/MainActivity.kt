package com.bluebillywig.bbnativeplayersdk_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var playerView = BBNativePlayerView(this.applicationContext)
    }
}