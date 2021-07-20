package com.bluebillywig.bbnativeplayersdk_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView

private lateinit var player: BBNativePlayerView
private lateinit var playerContainer: LinearLayout
private lateinit var inOutViewView: View


class InOutViewFragment : Fragment() {

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

		// todo ?    set player size to be width and height 9/16 of width
		return inOutViewView
	}
}