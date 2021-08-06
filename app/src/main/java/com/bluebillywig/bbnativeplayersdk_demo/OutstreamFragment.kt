package com.bluebillywig.bbnativeplayersdk_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeshared.Logger
import com.bluebillywig.bbnativeshared.enums.ApiProperty

/**
 * An outstream [Fragment] subclass.
 * Shows an outstream ad between TextViews
 */
class OutstreamFragment : Fragment(), BBNativePlayerViewDelegate {
	private lateinit var player: BBNativePlayerView
	private lateinit var playerContainer: LinearLayout
	private lateinit var outstreamView: View

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Create player view and display the ad using an ad embed url
		player = BBNativePlayer.createPlayerView(requireActivity(), "https://demo.bbvms.com/a/native_sdk_outstream.json")
		// To be able to listen to BBNativePlayer events, set the delegate to this
		// Overridden function didSetupWithJsonUrl is an example of a delegate callback
		player.delegate = this
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

		return outstreamView
	}

	override fun didSetupWithJsonUrl(url: String?) {
		super.didSetupWithJsonUrl(url)
		Logger.d("OutstreamFragment", "setup completed for url: $url")
		val playout = player.getApiProperty(ApiProperty.playoutData)
		Logger.d("OutstreamFragment", "got playout: $playout")
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