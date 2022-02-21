package com.bluebillywig.bbnativeplayersdk_demo

import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bluebillywig.bbnativeplayersdk.BBNativePlayer
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerAPI
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeshared.enums.ApiMethod
import com.bluebillywig.bbnativeshared.enums.ApiProperty
import com.bluebillywig.bbnativeshared.enums.Phase
import com.bluebillywig.bbnativeshared.enums.State
import com.bluebillywig.bbnativeshared.model.MediaClip
import com.bluebillywig.bbnativeshared.model.Playout
import com.bluebillywig.bbnativeshared.model.Project

private var pickerVals = arrayOf("Select API method", "Play", "Pause", "Seek", "Mute", "Unmute", "Load","getMuted", "getDuration", "getPhase", "getState", "getMode", "getClipData", "getPlayoutData", "getProjectData", "getVolume", "OpenModalPlayer")

/**
 * An api list [Fragment] subclass.
 * Show a selector with all of the api methods and see
 * the player (BBNativePlayerView) respond to those methods
 */
class ApiFragment : Fragment(), BBNativePlayerViewDelegate {
	private lateinit var playerView: BBNativePlayerView
	private lateinit var playerContainer: LinearLayout
	private lateinit var apiView: View
	private lateinit var apiMethodPicker: NumberPicker
	private var delegateText: TextView? = null
	private var outputText: TextView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		playerView = BBNativePlayer.createPlayerView(requireActivity(), "https://demo.bbvms.com/p/default/c/4256615.json")
		playerView.delegate = this
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)
		// Inflate and set the layout
		// Pass null as the parent view because its going in the dialog layout
		apiView = inflater.inflate(R.layout.fragment_api, null)

		playerContainer = apiView.findViewById(R.id.playerContainerView)
		playerContainer.addView(playerView)


		apiMethodPicker = apiView.findViewById(R.id.apiMethodPicker)
		apiMethodPicker.setPadding(10, 10, 10, 10);
		apiMethodPicker.setMinValue(0)
		apiMethodPicker.setMaxValue(pickerVals.count() - 1)
		apiMethodPicker.wrapSelectorWheel = false

		apiMethodPicker.setDisplayedValues(pickerVals)

		apiMethodPicker.setOnScrollListener { picker, state ->
			if (state == SCROLL_STATE_IDLE) {
				picker.postDelayed({ // to make sure value is in picker regardless the order of scroll or value change events
					handleApiMethodSelection(pickerVals[picker.value])

					// use the value and  hidePicker()
				}, 500)
			}
		}

		// setup text fields
		delegateText = apiView.findViewById(R.id.delegateText)
		delegateText?.setMovementMethod(ScrollingMovementMethod());
		delegateText?.setPadding(10, 10, 10, 10);
		outputText = apiView.findViewById(R.id.outputText)
		outputText?.setPadding(10, 10, 10, 10);

		return apiView
	}

	override fun onDestroyView() {
		playerView.player?.pause()
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

	private fun addToEventDebugTextfield(msg: String ) {
		delegateText?.append(msg)
		delegateText?.append("\n")
	}

	fun showValue(title: String, message: String) {
		outputText?.text = title + ": " +  message
	}

	// HANDLE API METHOD CALLS
	private fun handleApiMethodSelection( value: String ) {
		when (value) {
			"Play" -> {
				playerView.player?.play()
			}
			"Pause" -> {
				playerView.player?.pause()
			}
			"Seek" -> {
				playerView.player?.seek(10.0)
			}
			"Mute" -> {
				playerView.player?.muted = true
			}
			"Unmute" -> {
				playerView.player?.muted = false
			}
			"Load" -> {
				playerView.player?.loadWithClipId("4256575", null, true)
			}
			"getClipData" -> {
				val mediaClip: MediaClip? = playerView.player?.clipData
				if (mediaClip != null) {
					showValue("Clip id", mediaClip.id!!)
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getDuration" -> {
				val duration: Double? = playerView.player?.duration
				if (duration != null) {
					showValue("Duration", "${duration}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getMuted" -> {
				val muted: Boolean? = playerView.player?.muted
				if (muted != null) {
					showValue("Muted?", "${muted}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getPhase" -> {
				val phase: Phase? = playerView.player?.phase
				if (phase != null) {
					showValue("Phase", "${phase}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getState" -> {
				val state: State? = playerView.player?.state
				if (state != null) {
					showValue("State", "${state}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getMode" -> {
				val mode: String? = playerView.player?.mode
				if (mode != null) {
					showValue("Mode", mode)
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getPlayoutData" -> {
				val playout: Playout? = playerView.player?.playoutData
				if (playout != null) {
					showValue("Playout", "${playout.name}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getProjectData" -> {
				val project: Project? = playerView.player?.projectData
				if (project != null) {
					showValue("Project", "${project.name}")
				} else {
					showValue("Data", "Not available atm")
				}
			}

			"getVolume" -> {
				val volume: Double? = playerView.player?.volume
				if (volume != null) {
					showValue("Volume", "${volume}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"OpenModalPlayer" -> {
				activity?.let { PlayerDialogFragment("https://demo.bbvms.com/p/default/c/4256615.json").show(it.supportFragmentManager, "player") }
			}
		}
	}

	// PLAYER DELEGATE METHODS
	override fun didSetupWithJsonUrl(playerView: BBNativePlayerView, url: String?) {
		addToEventDebugTextfield("Player API Delegate: didSetupWithJson")
	}

	override fun didTriggerMediaClipLoaded(playerView: BBNativePlayerView, clipData: MediaClip?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerMediaClipLoaded")
	}

	override fun didTriggerMediaClipFailed(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerMediaClipFailed")
	}

	override fun didTriggerViewStarted(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerViewStarted")
	}

	override fun didTriggerViewFinished(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerViewFinished")
	}

	override fun didTriggerProjectLoaded(playerView: BBNativePlayerView, projectData: Project?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerProjectLoaded")
	}

	override fun didTriggerCanPlay(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerCanPlay")
	}

	override fun didTriggerDurationChange(playerView: BBNativePlayerView, duration: Double?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerDurationChange : ${duration}")
	}

	override fun didTriggerPlay(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerPlay")
	}

	override fun didTriggerPause(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerPause")
	}

	override fun didTriggerPlaying(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerPlaying")
	}

	override fun didTriggerSeeking(playerView: BBNativePlayerView, ) {
		addToEventDebugTextfield("Player API Delegate: didTriggerSeeking")
	}

	override fun didTriggerSeeked(playerView: BBNativePlayerView, seekOffset: Double?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerSeeked : ${seekOffset}")
	}

	//todo uncomment when sdk is updated on this project
//	override fun didTriggerStall() {
//		addToEventDebug("Player API Delegate: didTriggerStall")
//	}

	override fun didTriggerAutoPause(playerView: BBNativePlayerView, why: String?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerAutoPause")
	}

	override fun didTriggerAutoPausePlay(playerView: BBNativePlayerView, why: String?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerAutoPausePlay")
	}

	override fun didFailWithError(playerView: BBNativePlayerView, error: String?) {
		addToEventDebugTextfield("Player API Delegate: didFailWithError")
	}

	override fun didTriggerAdError(playerView: BBNativePlayerView, error: String?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerAdError : ${error}")
	}

	override fun didTriggerPhaseChange(playerView: BBNativePlayerView, phase: Phase?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerPhaseChange : ${phase}")
	}

	override fun didTriggerStateChange(playerView: BBNativePlayerView, state: State?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerStateChange :${state}")
	}
}
