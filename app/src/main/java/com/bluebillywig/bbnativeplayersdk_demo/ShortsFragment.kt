package com.bluebillywig.bbnativeplayersdk_demo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.bluebillywig.bbnativeplayersdk.BBNativeShorts
import com.bluebillywig.bbnativeplayersdk.BBNativeShortsView
import com.bluebillywig.bbnativeplayersdk.BBNativeShortsViewDelegate
import com.bluebillywig.bbnativeshared.Logger
import com.bluebillywig.bbnativeshared.enums.ApiMethod

/**
 * An inview/outview [Fragment] subclass.
 * When the player is in view play the video
 * but when not in view stop playing the video
 */
class ShortsFragment : Fragment(), BBNativeShortsViewDelegate {
    private lateinit var shortsView: BBNativeShortsView
    private lateinit var containerView: LinearLayout
    private lateinit var fragmentActivity: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var jsonUrl: String? = "https://demo.bbvms.com/sh/58.json"
        val arguments_ = arguments
        if (arguments_ != null) {
            jsonUrl = arguments_.getString("jsonUrl")
        }
        Logger.d("ShortsFragment", "json url: $jsonUrl")

        fragmentActivity = requireActivity()
        shortsView = BBNativeShorts.createShortsView(fragmentActivity, jsonUrl)

        shortsView.delegate = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val shortsFragment: View = inflater.inflate(R.layout.fragment_shorts, null)
        containerView = shortsFragment.findViewById(R.id.shortsContainerView)
        containerView.addView(shortsView)

        return shortsFragment
    }

    override fun onDestroyView() {
//        shortsView.callApiMethod(ApiMethod.pause, null)
        containerView.removeAllViews()
        super.onDestroyView()
    }

    override fun onDestroy() {
        shortsView.__destruct()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
//        shortsView.resumeCastSession()
    }

    override fun onPause() {
//        shortsView.pauseCastSession()
        super.onPause()
    }
}
