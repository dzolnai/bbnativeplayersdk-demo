package com.bluebillywig.bbnativeplayersdk_demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.Navigation
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
		addOnClickListener(view, R.id.outstreamButton, R.id.action_launcherFragment_to_outstreamFragment)
		addOnClickListener(view, R.id.prePostRollButton, R.id.action_launcherFragment_to_prePostRollFragment)
		addOnClickListener(view, R.id.webviewButton, R.id.action_launcherFragment_to_webviewFragment)

		return view
	}

	private fun addOnClickListener(view: View, buttonId: Int, actionId: Int) {
		view.findViewById<Button>(buttonId).setOnClickListener {
			Navigation.findNavController(view).navigate(actionId)
		}
	}
}