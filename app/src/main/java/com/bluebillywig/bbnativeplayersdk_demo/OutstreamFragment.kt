package com.bluebillywig.bbnativeplayersdk_demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.*
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
import com.bluebillywig.bbnativeshared.enums.ApiMethod
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * An outstream [Fragment] subclass.
 * Shows an outstream ad between TextViews
 */
class OutstreamFragment : Fragment(), BBNativePlayerViewDelegate, CoroutineScope {
	private var parentJob = Job()
	override val coroutineContext: CoroutineContext
		get() = Dispatchers.IO + parentJob

	private lateinit var playerView: BBNativePlayerView
	private lateinit var playerContainer: LinearLayout
	private lateinit var outstreamView: View

	@SuppressLint("HardwareIds")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		this.launch {
			// The fallback (behind the ?: statement) is not recommended, but again, it's a fallback
			// suppressing recommendation message by using @SuppressLint("HardwareIds")
			val adId = getAdId(this).await() ?: Secure.getString(requireContext().getContentResolver(), Secure.ANDROID_ID)
			Logger.d("OutstreamFragment", "Got adId: $adId")

			this.launch(Dispatchers.Main) {
				val options = HashMap<String, Any?>()
				options.put("adsystem_rdid", adId)
				options.put("adsystem_is_lat", false)
				options.put("allowCollapseExpand", true)

				// Create player view and display the ad using an ad embed url
				playerView = BBNativePlayer.createPlayerView(requireActivity(),"https://demo.bbvms.com/a/native_sdk_outstream.json", options)
			}
		}
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

		val outstreamFragment = this

		this.launch {
			waitForPlayer(this).await()

			this.launch(Dispatchers.Main) {
				// To be able to listen to BBNativePlayer events, set the delegate to this
				// Overridden function didSetupWithJsonUrl is an example of a delegate callback
				playerView.delegate = outstreamFragment

				playerContainer.addView(playerView)
			}
		}

		return outstreamView
	}

	override fun didSetupWithJsonUrl(playerView: BBNativePlayerView, url: String?) {
		super.didSetupWithJsonUrl(playerView, url)
		Logger.d("OutstreamFragment", "setup completed for url: $url")
		val playout = playerView.getApiProperty(ApiProperty.playoutData)
		Logger.d("OutstreamFragment", "got playout: $playout")
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

	private fun getAdId(scope: CoroutineScope): Deferred<String?> = scope.async(Dispatchers.IO) {
		val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(requireContext())
		adInfo.id
	}

	private fun waitForPlayer(scope: CoroutineScope): Deferred<Unit> = scope.async(Dispatchers.IO) {
		while (!::playerView.isInitialized && scope.isActive) {
			Thread.sleep(250)
		}
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment.
		 *
		 * @return A new instance of fragment OutstreamFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance() =
			OutstreamFragment().apply {
				arguments = Bundle().apply {

				}
			}
	}
}