package com.bluebillywig.bbnativeplayersdk_demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.Navigation
import com.bluebillywig.bbnativeplayersdk_demo.PlayerDialogFragment
import com.bluebillywig.bbnativeplayersdk_demo.R

class LauncherFragment : Fragment(), LifecycleObserver {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val view = inflater.inflate(R.layout.fragment_launcher, container, false)

		addOnClickListener(view, R.id.apiButton, R.id.action_launcherFragment_to_apiFragment)
		addOnClickListener(view, R.id.videoListButton, R.id.action_launcherFragment_to_videoListFragment)
		addOnClickListener(view, R.id.inOutViewButton, R.id.action_launcherFragment_to_inOutViewFragment)
		addOnClickListener(view, R.id.webviewButton, R.id.action_launcherFragment_to_webviewFragment)

		view.findViewById<Button>(R.id.outstreamButton).setOnClickListener {
			val jsonUrl = view.findViewById<EditText>(R.id.outstreamTextField)?.text.toString()
			val bundle = bundleOf("jsonUrl" to jsonUrl)
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_outstreamFragment, bundle)
		}
		view.findViewById<Button>(R.id.shortsButton).setOnClickListener {
			val jsonUrl = view.findViewById<EditText>(R.id.shortsTextField)?.text.toString()
			val bundle = bundleOf("jsonUrl" to jsonUrl)
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_shortsFragment, bundle)
		}
		view.findViewById<Button>(R.id.prePostRollButton).setOnClickListener {
			PlayerDialogFragment("https://demo.bbvms.com/p/native_sdk_preroll/c/4256600.json").show(requireActivity().supportFragmentManager,"player")
		}

		return view
	}

	private fun addOnClickListener(view: View, buttonId: Int, actionId: Int) {
		view.findViewById<Button>(buttonId).setOnClickListener {
			Navigation.findNavController(view).navigate(actionId)
		}
	}
}