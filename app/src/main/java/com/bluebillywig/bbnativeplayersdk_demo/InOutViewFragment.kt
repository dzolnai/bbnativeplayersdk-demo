package com.bluebillywig.bbnativeplayersdk_demo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeshared.enums.ApiMethod

/**
 * An inview/outview [Fragment] subclass.
 * When the player is in view play the video
 * but when not in view stop playing the video
 */
class InOutViewFragment : Fragment(), BBNativePlayerViewDelegate {
	private lateinit var playerView: BBNativePlayerView
	private lateinit var playerContainer: LinearLayout
	private lateinit var inOutViewView: View
	private lateinit var fragmentActivity: FragmentActivity

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		fragmentActivity = requireActivity()
		playerView = BBNativePlayer.createPlayerView(fragmentActivity, "https://demo.bbvms.com/p/native_sdk_inoutview/c/4256635.json", mapOf("noChromeCast" to false))

		playerView.delegate = this

//		fragmentActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		inOutViewView = inflater.inflate(R.layout.fragment_in_out_view, null)

		playerContainer = inOutViewView.findViewById(R.id.playerContainerView)
		playerContainer.addView(playerView)

		return inOutViewView
	}

	override fun onDestroyView() {
		playerView.callApiMethod(ApiMethod.pause, null)
		playerContainer.removeAllViews()
		super.onDestroyView()
	}

	override fun onDestroy() {
		playerView.destroy()
		super.onDestroy()
	}

	override fun onResume() {
		super.onResume()
		playerView.resumeCastSession()
	}

	override fun onPause() {
		playerView.pauseCastSession()
		super.onPause()
	}

//	override fun didTriggerRetractFullscreen(playerView: BBNativePlayerView) {
//		println("*** didTriggerRetractFullscreen")
//		fragmentActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//	}
}