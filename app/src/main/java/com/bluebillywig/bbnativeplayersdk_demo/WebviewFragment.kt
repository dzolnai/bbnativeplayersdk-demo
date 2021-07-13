package com.bluebillywig.bbnativeplayersdk_demo

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeshared.Logger
import com.bluebillywig.bbnativeshared.model.MediaClip


private class WebChromeClientCallback : WebChromeClient() {
	override fun onProgressChanged(view: WebView?, newProgress: Int) {
		super.onProgressChanged(view, newProgress)
	}
}

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebViewFragment : Fragment(), BBNativePlayerViewDelegate {
	private lateinit var webView: WebView
	private lateinit var player: BBNativePlayerView

	@SuppressLint("SetJavaScriptEnabled")
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_webview, container, false)

		webView = view.findViewById(R.id.webview)

		val b: WebView? = null

		var a = b ?: "test"

		webView.webViewClient = WebViewClient()
		webView.webChromeClient = WebChromeClientCallback()

		val javascriptAppInterface = JavascriptAppInterface(requireActivity() as AppCompatActivity, view)
		webView.addJavascriptInterface(javascriptAppInterface, "NativeBridge")

		webView.settings.javaScriptEnabled = true

		webView.loadUrl("https://bluebillywig.tv/native-demo-android/?" + System.currentTimeMillis())

		player = BBNativePlayer.createPlayerView(requireActivity(), "https://bb.dev.bbvms.com/p/floris_ads/c/1089624.json")
		player.delegate = this

		return view
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment WebviewFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			WebViewFragment().apply {
				arguments = Bundle().apply {}
			}
	}

	class JavascriptAppInterface(private val activity: AppCompatActivity, private val view: View) {
		@JavascriptInterface
		fun startPlayer(url: String?) {
			Logger.d("Javascript interface", "start player called with url: $url")
			PlayerDialogFragment().show(activity.supportFragmentManager, "player")
		}
	}

	class PlayerDialogFragment() : DialogFragment(), BBNativePlayerViewDelegate {
		private lateinit var player: BBNativePlayerView
		private lateinit var playerLayout: LinearLayout
		private lateinit var dialogView: View

		override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)

			player = BBNativePlayer.createPlayerView(requireActivity(), "https://bb.dev.bbvms.com/p/floris_ads/c/1089624.json")
			player.delegate = this
		}

		override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
		): View? {
			super.onCreateView(inflater, container, savedInstanceState)

			// Inflate and set the layout for the dialog
			// Pass null as the parent view because its going in the dialog layout
			dialogView = inflater.inflate(R.layout.dialog_player, null)

			playerLayout = dialogView.findViewById(R.id.playerLayout)

			playerLayout.addView(player)

			return dialogView
		}

		override fun didTriggerMediaClipLoaded(clipData: MediaClip?) {
			super.didTriggerMediaClipLoaded(clipData)
			var dar = clipData?.dar ?: "16:9"
			val regex = Regex("\\d+:\\d+")
			if (!dar.contains(regex)) {
				dar = "16:9"
			}

			val (widthString, heightString) = dar.split(':')
			val darWidth = widthString.toInt()
			val darHeight = heightString.toInt()

			val width = dialogView.width + 32
			val height = dialogView.height + 32

			Logger.d("PlayerDialogFragment", "width: ${width} height: ${height} darWidth: $darWidth darHeight: $darHeight")

			if (height > width && darWidth > darHeight) {
				// Portrait view and landscape video handling
				val dialogHeight = width / darWidth * darHeight
				Logger.d("PlayerDialogFragment", "Dialog calculated height: $dialogHeight")

				val params = playerLayout.layoutParams
				params.height = dialogHeight

			} else if (width > height && darHeight > darWidth) {
				// Landscape view and portrait video handling
				val dialogWidth = height / darHeight * darWidth
				Logger.d("PlayerDialogFragment", "Dialog calculated width: $dialogWidth")

				val params = playerLayout.layoutParams
				params.width = dialogWidth
			}

			playerLayout.visibility = View.VISIBLE
		}

		override fun onDestroyView() {
			Logger.d("PlayerDialogFragment", "onDestroyView")
			player.callApiMethod("pause", null)
			player.destroy()
			playerLayout.removeAllViews()
			super.onDestroyView()
		}
	}
}