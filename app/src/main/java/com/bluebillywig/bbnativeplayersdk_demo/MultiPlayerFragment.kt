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
import com.bluebillywig.bbnativeplayersdk.BBNativeShortsViewDelegate

/**
 * An inview/outview [Fragment] subclass.
 * When the player is in view play the video
 * but when not in view stop playing the video
 */
class MultiPlayerFragment : Fragment(), BBNativeShortsViewDelegate {
	private lateinit var fragmentActivity: FragmentActivity
	private lateinit var playerView1: BBNativePlayerView
	private lateinit var playerView2: BBNativePlayerView
	private lateinit var playerView3: BBNativePlayerView
	private lateinit var playerView4: BBNativePlayerView
	private lateinit var player1Container: LinearLayout
	private lateinit var player2Container: LinearLayout
	private lateinit var player3Container: LinearLayout
	private lateinit var player4Container: LinearLayout

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		fragmentActivity = requireActivity()

		// PUC OD Video I (with HLS)
		playerView1 = BBNativePlayer.createPlayerView(fragmentActivity,"https://testsuite.acc.bbvms.com/p/puc_chromecast_airplay/c/7708.json", mapOf("noStats" to true, "autoPlay" to false))

		// PUC OD Video J (without HLS/DASH)
		playerView2 = BBNativePlayer.createPlayerView(fragmentActivity,"https://testsuite.acc.bbvms.com/p/puc_chromecast_airplay/c/9347.json", mapOf("noStats" to true, "autoPlay" to false))

		// PUC Live HLS Omroep Zeeland
		playerView3 = BBNativePlayer.createPlayerView(fragmentActivity,"https://testsuite.acc.bbvms.com/p/puc_chromecast_airplay/c/8962.json", mapOf("noStats" to true, "autoPlay" to false))

		// Rihanna - Lift Me Up (with subtitles)
		playerView4 = BBNativePlayer.createPlayerView(fragmentActivity,"https://testsuite.acc.bbvms.com/p/puc_chromecast_airplay/c/7455.json", mapOf("noStats" to true, "autoPlay" to false))

	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		val multiPlayerFragment: View = inflater.inflate(R.layout.fragment_multi_player, null)

		player1Container = multiPlayerFragment.findViewById<LinearLayout>(R.id.player1_view)
		player1Container.addView(playerView1)
		player2Container = multiPlayerFragment.findViewById<LinearLayout>(R.id.player2_view)
		player2Container.addView(playerView2)
		player3Container = multiPlayerFragment.findViewById<LinearLayout>(R.id.player3_view)
		player3Container.addView(playerView3)
		player4Container = multiPlayerFragment.findViewById<LinearLayout>(R.id.player4_view)
		player4Container.addView(playerView4)

		return multiPlayerFragment
	}

	override fun onDestroyView() {
		player1Container.removeAllViews()
		player2Container.removeAllViews()
		player3Container.removeAllViews()
		player4Container.removeAllViews()
		super.onDestroyView()
	}

	override fun onDestroy() {
		playerView1.__destruct()
		playerView2.__destruct()
		playerView3.__destruct()
		playerView4.__destruct()
		super.onDestroy()
	}

	override fun onResume() {
		super.onResume()
	}

	override fun onPause() {
		super.onPause()
	}
}
