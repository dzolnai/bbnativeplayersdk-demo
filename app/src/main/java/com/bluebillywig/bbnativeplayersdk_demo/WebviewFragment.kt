package com.bluebillywig.bbnativeplayersdk_demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bluebillywig.bbnativeshared.Logger

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebViewFragment : Fragment() {
	private lateinit var webView: WebView

	private class WebChromeClientCallback : WebChromeClient() {
		override fun onProgressChanged(view: WebView?, newProgress: Int) {
			super.onProgressChanged(view, newProgress)
		}
	}

	private class JavascriptAppInterface(private val activity: AppCompatActivity, private val view: View) {
		@JavascriptInterface
		fun startPlayer(url: String?) {
			Logger.d("Javascript interface", "start player called with url: $url")
			if (url != null) {
				PlayerDialogFragment(url).show(activity.supportFragmentManager, "player")
			}
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_webview, container, false)

		webView = view.findViewById(R.id.webview)

		webView.webViewClient = WebViewClient()
		webView.webChromeClient = WebChromeClientCallback()

		val javascriptAppInterface = JavascriptAppInterface(requireActivity() as AppCompatActivity, view)
		webView.addJavascriptInterface(javascriptAppInterface, "NativeBridge")

		webView.settings.javaScriptEnabled = true

		webView.loadUrl("https://bluebillywig.tv/native-demo/?" + System.currentTimeMillis())

		return view
	}
}