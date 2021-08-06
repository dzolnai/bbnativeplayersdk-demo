package com.bluebillywig.bbnativeplayersdk_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.doOnLayout
import androidx.fragment.app.DialogFragment
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeshared.enums.ApiMethod

private lateinit var player: BBNativePlayerView
private lateinit var playerContainer: LinearLayout
private lateinit var outstreamView: View

class OutstreamFragment : Fragment() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		player = BBNativePlayer.createPlayerView(requireActivity(), "https://demo.bbvms.com/a/native_sdk_outstream.json")
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		outstreamView = inflater.inflate(R.layout.fragment_outstream, null)

		playerContainer = outstreamView.findViewById(R.id.playerContainerView)
		playerContainer.addView(player)

		// todo ?    set player size to be width and height 9/16 of width
		return outstreamView
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

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment OutstreamFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			OutstreamFragment().apply {
				arguments = Bundle().apply {

				}
			}
	}
}