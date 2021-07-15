package com.bluebillywig.bbnativeplayersdk_demo

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Insets
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.Navigation.findNavController
import com.bluebillywig.bbnativeshared.Logger


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Logger.d("MainActivity - onCreate","The real size = " + getScreenWidth(this) + " x " + getScreenHeight(this))

        // TODO Remove the top bar in landscape
//        val layout = findViewById<ConstraintLayout>(R.id.subLayout)
//
//        val params = layout.layoutParams
//
//        params.height = getScreenHeight(this)
//        params.width = getScreenWidth(this)
//        layout.layoutParams = params

    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.navHostFragment).navigateUp()

    fun getScreenHeight(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.bottom - insets.bottom
        } else {
            val point = Point()
            val realPoint = Point()
            activity.windowManager.defaultDisplay.getSize(point)
            activity.windowManager.defaultDisplay.getRealSize(realPoint)
            point.y - (realPoint.y - point.y)
        }
    }

    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    private fun hideSystemUI() {
        val layout = findViewById<FrameLayout>(R.id.mainLayout)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, layout).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        val layout = findViewById<FrameLayout>(R.id.mainLayout)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, layout).show(WindowInsetsCompat.Type.systemBars())
    }

    override fun onConfigurationChanged(config: Configuration) {
        var orientation = "portrait"
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            orientation = "landscape"
        }
        Logger.d("MainActivity - onConfigurationChanged","orientation is $orientation")
        super.onConfigurationChanged(config)
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        } else {
            showSystemUI()
        }
    }
}