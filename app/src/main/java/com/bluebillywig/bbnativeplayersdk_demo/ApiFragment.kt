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
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeshared.enums.ApiMethod
import com.bluebillywig.bbnativeshared.enums.ApiProperty
import com.bluebillywig.bbnativeshared.enums.Phase
import com.bluebillywig.bbnativeshared.enums.State
import com.bluebillywig.bbnativeshared.model.MediaClip
import com.bluebillywig.bbnativeshared.model.Playout
import com.bluebillywig.bbnativeshared.model.Project


private lateinit var player: BBNativePlayerView
private lateinit var playerContainer: LinearLayout
private lateinit var apiView: View
private lateinit var apiMethodPicker: NumberPicker
private var delegateText: TextView? = null
private var outputText: TextView? = null

private var pickerVals = arrayOf("Select API method", "Play", "Pause", "Seek", "Mute", "Unmute", "Load","getMuted", "getDuration", "getPhase", "getState", "getMode", "getClipData", "getPlayoutData", "getProjectData", "getVolume", "OpenModalPlayer")

class ApiFragment : Fragment(), BBNativePlayerViewDelegate {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		player = BBNativePlayer.createPlayerView(requireActivity(), "https://demo.bbvms.com/p/default/c/4256615.json")
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)
		player.delegate = this
		// Inflate and set the layout
		// Pass null as the parent view because its going in the dialog layout
		apiView = inflater.inflate(R.layout.fragment_api, null)

		playerContainer = apiView.findViewById(R.id.playerContainerView)
		playerContainer.addView(player)


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

		// todo ?    set player size to be width and height 9/16 of width
		return apiView
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
				player?.callApiMethod(ApiMethod.play, null)
			}
			"Pause" -> {
				player?.callApiMethod(ApiMethod.pause, null)
			}
			"Seek" -> {
				player?.callApiMethod(ApiMethod.seek, mapOf("offsetInSeconds" to 10))
			}
			"Mute" -> {
				player?.setApiProperty(ApiProperty.muted, true)
			}
			"Unmute" -> {
				player?.setApiProperty(ApiProperty.muted, false)
			}
			"Load" -> {
				player?.callApiMethod(
					ApiMethod.load,
					mapOf("clipId" to "4256575", "autoPlay" to true)
				)
			}
			"getClipData" -> {
				var mediaClip: MediaClip = player?.getApiProperty(ApiProperty.clipData) as MediaClip
				if (mediaClip != null) {
					showValue("Clip id", mediaClip.id!!)
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getDuration" -> {
				var duration: Number = player?.getApiProperty(ApiProperty.duration) as Number
				if (duration != null) {
					showValue("Duration", "${duration}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getMuted" -> {
				var muted: Boolean = player?.getApiProperty(ApiProperty.muted) as Boolean
				if (muted != null) {
					showValue("Muted?", "${muted}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getPhase" -> {
				var phase: Phase = player?.getApiProperty(ApiProperty.phase) as Phase
				if (phase != null) {
					showValue("Phase", "${phase}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getState" -> {
				var state: State = player?.getApiProperty(ApiProperty.state) as State
				if (state != null) {
					showValue("State", "${state}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getMode" -> {
				var mode: String = player?.getApiProperty(ApiProperty.mode) as String
				if (mode != null) {
					showValue("Mode", mode)
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getPlayoutData" -> {
				if ( player?.getApiProperty(ApiProperty.playoutData) != null ) {
					var playout: Playout = player?.getApiProperty(ApiProperty.playoutData) as Playout
					showValue("Playout", "${playout.name}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"getProjectData" -> {
				if (player?.getApiProperty(ApiProperty.projectData) != null) {
					var project: Project = player?.getApiProperty(ApiProperty.projectData) as Project
					showValue("Project", "${project.name}")
				} else {

					showValue("Data", "Not available atm")
				}
			}

			"getVolume" -> {
				var volume: Number = player?.getApiProperty(ApiProperty.volume) as Number
				if (volume != null) {
					showValue("Volume", "${volume}")
				} else {
					showValue("Data", "Not available atm")
				}
			}
			"OpenModalPlayer" -> {
				//VideoModal()
			}
		}
	}

	// PLAYER DELEGATE METHODS
	override fun didSetupWithJsonUrl(url: String?) {
		addToEventDebugTextfield("Player API Delegate: didSetupWithJson")
	}

	override fun didTriggerMediaClipLoaded(clipData: MediaClip?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerMediaClipLoaded")
	}

	override fun didTriggerMediaClipFailed() {
		addToEventDebugTextfield("Player API Delegate: didTriggerMediaClipFailed")
	}

	override fun didTriggerViewStarted() {
		addToEventDebugTextfield("Player API Delegate: didTriggerViewStarted")
	}

	override fun didTriggerViewFinished() {
		addToEventDebugTextfield("Player API Delegate: didTriggerViewFinished")
	}

	override fun didTriggerProjectLoaded(projectData: Project?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerProjectLoaded")
	}

	override fun didTriggerCanPlay() {
		addToEventDebugTextfield("Player API Delegate: didTriggerCanPlay")
	}

	override fun didTriggerDurationChange(duration: Double?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerDurationChange : ${duration}")
	}

	override fun didTriggerPlay() {
		addToEventDebugTextfield("Player API Delegate: didTriggerPlay")
	}

	override fun didTriggerPause() {
		addToEventDebugTextfield("Player API Delegate: didTriggerPause")
	}

	override fun didTriggerPlaying() {
		addToEventDebugTextfield("Player API Delegate: didTriggerPlaying")
	}

	override fun didTriggerSeeking() {
		addToEventDebugTextfield("Player API Delegate: didTriggerSeeking")
	}

	override fun didTriggerSeeked(seekOffset: Double?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerSeeked : ${seekOffset}")
	}

	//todo uncomment when sdk is updated on this project
//	override fun didTriggerStall() {
//		addToEventDebug("Player API Delegate: didTriggerStall")
//	}

	override fun didTriggerAutoPause(why: String?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerAutoPause")
	}

	// todo
	override fun didTriggerAutoPausePlay(why: String?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerAutoPausePlay")
	}

	override fun didFailWithError(error: String?) {
		addToEventDebugTextfield("Player API Delegate: didFailWithError")
	}

	override fun didTriggerAdError(error: String?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerAdError : ${error}")
	}

	override fun didTriggerPhaseChange(phase: Phase?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerPhaseChange : ${phase}")
	}

	override fun didTriggerStateChange(state: State?) {
		addToEventDebugTextfield("Player API Delegate: didTriggerStateChange :${state}")
	}
}
