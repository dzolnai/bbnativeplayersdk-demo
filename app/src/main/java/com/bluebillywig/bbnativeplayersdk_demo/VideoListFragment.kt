package com.bluebillywig.bbnativeplayersdk_demo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bluebillywig.bbnativeplayersdk_demo.model.ClipList
import com.bluebillywig.bbnativeshared.Logger
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 * Use the [VideoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoListFragment : Fragment(), CoroutineScope {
	private lateinit var videoListView: View
	private lateinit var videoListLayout: LinearLayout
	private val job = Job()
	override val coroutineContext: CoroutineContext
		get() = job

	private val baseUrl = "https://demo.bbvms.com"
	private val outline = 32

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		videoListView = inflater.inflate(R.layout.fragment_video_list, container, false)
		videoListLayout = videoListView.findViewById(R.id.videoListLayout)

		return videoListView
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.post{ fetchVideos() }
	}

	private fun fetchVideos() {
		val cliplistUrl = "${baseUrl}/json/search?cliplistid=1623750782772352&allowCache=true"

		val stageWidth = videoListView.width - (2 * outline)

		var clipList: ClipList

		// Were using Dispatchers.IO here to get rid of the inappropriate blocking method call message
		launch(Dispatchers.Default) {
			var repoListJsonStr = ""
			repoListJsonStr = URL(cliplistUrl).readText()

			// TODO Use MediaclipList from shared lib, but this breaks on not being able to create class ContentItem when retrieving the json
			// clipList = Gson().fromJson(repoListJsonStr, MediaClipList::class.java)
			clipList = Gson().fromJson(repoListJsonStr, ClipList::class.java)
			if ( clipList != null ) {
				clipList.items.forEachIndexed { index, it ->
					val url = "${baseUrl}/p/default/c/${it.id}.json"
					Logger.d("VideoListFragment", "fetchVideos clip: $url")

					context?.let { context ->
						val thumbnailUrl =
							"${baseUrl}/mediaclip/${it.id}/pthumbnail/default/default.jpg?scalingMode=cover"

						// This is needed because UI updates can only be run on the main thread
						ContextCompat.getMainExecutor(context).execute {
							Logger.d("VideoListFragment", "Adding textview $index")
							videoListLayout.addView(
								createTextView(
									context,
									it.description,
									url,
									thumbnailUrl,
									stageWidth
								)
							)
							videoListLayout.addView(createDivider())
						}
					}
				}
			}
		}
	}

	private fun createTextView(context: Context, text: String, jsonUrl: String, thumbnailUrl: String, width: Int): TextView {
		val textView = TextView(context)
		val layoutParams = LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
		)
		layoutParams.setMargins(outline, 0, outline, 0)
		textView.layoutParams = layoutParams
		textView.text = text
		textView.setPadding(0, outline, 0, outline)
		textView.ellipsize = TextUtils.TruncateAt.END
		textView.setLines(2)
		textView.setOnClickListener {
			PlayerDialogFragment(jsonUrl).show(requireActivity().supportFragmentManager,"player")
		}

		Glide.with(context)
			.load(thumbnailUrl)
			.apply(RequestOptions().fitCenter()).into(
				object : CustomTarget<Drawable>(width, width) {
					override fun onLoadCleared(placeholder: Drawable?) {
						textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							placeholder,
							null,
							null
						)
					}

					override fun onResourceReady(
						resource: Drawable,
						transition: Transition<in Drawable>?
					) {
						textView.setCompoundDrawablesWithIntrinsicBounds(
							null,
							resource,
							null,
							null
						)
					}
				}
			)

		return textView
	}

	private fun createDivider(): View {
		val dividerView = View(context)
		dividerView.setBackgroundColor(Color.LTGRAY)
		val dividerLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1)
		dividerLayoutParams.setMargins(0, 16, 0, 16)
		dividerView.layoutParams = dividerLayoutParams

		return dividerView
	}
}