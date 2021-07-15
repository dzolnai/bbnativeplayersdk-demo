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

		view.findViewById<Button>(R.id.apiButton).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_apiFragment)
		}

		view.findViewById<Button>(R.id.videoListButton).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_videoListFragment)
		}

		view.findViewById<Button>(R.id.inOutViewButton).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_inOutViewFragment)
		}

		view.findViewById<Button>(R.id.outstreamButton).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_outstreamFragment)
		}

		view.findViewById<Button>(R.id.prePostRollButton).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_prePostRollFragment)
		}

		view.findViewById<Button>(R.id.webviewButton).setOnClickListener {
			Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_webviewFragment)
		}

		return view
	}
}