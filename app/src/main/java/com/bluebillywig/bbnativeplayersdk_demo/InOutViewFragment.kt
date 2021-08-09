package com.bluebillywig.bbnativeplayersdk_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeshared.enums.ApiMethod

/**
 * An inview/outview [Fragment] subclass.
 * When the player is in view play the video
 * but when not in view stop playing the video
 */
class InOutViewFragment : Fragment() {
	private lateinit var player: BBNativePlayerView
	private lateinit var playerContainer: LinearLayout
	private lateinit var inOutViewView: View

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		player = BBNativePlayer.createPlayerView(requireActivity(), "https://demo.bbvms.com/p/native_sdk_inoutview/c/4256635.json")
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
		playerContainer.addView(player)

		return inOutViewView
	}

	override fun onDestroyView() {
		player.callApiMethod(ApiMethod.pause, null)
		playerContainer.removeAllViews()
		super.onDestroyView()
	}

	override fun onDestroy() {
		player.destroy()
		super.onDestroy()
	}
}