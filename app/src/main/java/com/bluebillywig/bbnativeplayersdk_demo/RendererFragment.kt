package com.bluebillywig.bbnativeplayersdk_demo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.FragmentActivity
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerView
import com.bluebillywig.bbnativeplayersdk.BBNativePlayerViewDelegate
import com.bluebillywig.bbnativeplayersdk.BBNativeRenderer
import com.bluebillywig.bbnativeplayersdk.BBNativeRendererView
import com.bluebillywig.bbnativeplayersdk.BBNativeRendererViewDelegate
import com.bluebillywig.bbnativeshared.Logger
import com.bluebillywig.bbnativeshared.enums.ApiMethod

/**
 * A renderer [Fragment] subclass
 * When the player is in view play the video
 * but when not in view stop playing the video
 */
class RendererFragment : Fragment(), BBNativePlayerViewDelegate {
	private lateinit var rendererView: BBNativeRendererView
	private lateinit var containerView: LinearLayout
	private lateinit var fragmentActivity: FragmentActivity

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		var jsonUrl: String? = "https://demo.bbvms.com/r/puc_outstream_skinless.json"
		val arguments_ = arguments
		if (arguments_ != null) {
			jsonUrl = arguments_.getString("jsonUrl")
		}
		Logger.d("RendererFragment", "json url: $jsonUrl")

		fragmentActivity = requireActivity()
		rendererView = BBNativeRenderer.createRendererView(fragmentActivity, jsonUrl, mapOf("overscanAd" to true))
		rendererView.id = View.generateViewId()
		val rendererLayoutParams = ViewGroup.LayoutParams(600, 500)
		rendererView.layoutParams = rendererLayoutParams
		rendererView.playerViewDelegate = this
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreateView(inflater, container, savedInstanceState)

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		val rendererFragment: View = inflater.inflate(R.layout.fragment_renderer, null)
		containerView = rendererFragment.findViewById(R.id.rendererContainerView)
		containerView.addView(rendererView)

		return rendererFragment
	}

	override fun onDestroyView() {
//        rendererView.callApiMethod(ApiMethod.pause, null)
		containerView.removeAllViews()
		super.onDestroyView()
	}

	override fun onDestroy() {
		rendererView.__destruct()
		super.onDestroy()
	}

	override fun onResume() {
		super.onResume()
//        rendererView.resumeCastSession()
	}

	override fun onPause() {
//        rendererView.pauseCastSession()
		super.onPause()
	}

	override fun didTriggerApiReady(playerView: BBNativePlayerView) {
		println("*** didTriggerApiReady")

		val vastXml = """<?xml version="1.0" encoding="UTF-8"?>
<VAST xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="vast.xsd" version="3.0">
 <Ad id="5925573263">
  <InLine>
   <AdSystem>GDFP</AdSystem>
   <AdTitle>External - Single Inline Linear</AdTitle>
   <Description><![CDATA[External - Single Inline Linear ad]]></Description>
   <Error><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=videoplayfailed[ERRORCODE]]]></Error>
   <Impression><![CDATA[https://pagead2.googlesyndication.com/pcs/view?xai=AKAOjsuW29e1GlUDh8tv485bJvOjly3vXoSKTdLEltgoSw_ZJW9JHnJkJQ4cubwBuzwbkEec106nx-nTnrnBDLNRHPa9uughHPEhOdOb_KcJBOAZSVpwrrPmOlBSiWHuFMTfUHBdGsGV2Tzdg6JqO2Qw7lSwPPKn0ZRmngiLTWf3bKr356uZyjOsI_6oMsKWxXgcCZmZdV4fEJCcpPZLXvTtfQb2bPbvkfP5URTRdjf-lIAWB_GcCbDkt9kuIvt8czpBFvTMnVk1X5Lb-nHgSB9Wckn7n8ASmz-an4Jgt6_2uCp7o1DO1NKyW_wER4iAZN1HEO9u21DUUruTzErzHPeyTds-xLmusdLfUXLFz-ciHnAbyBk540OA5ys1PG41qDrbALsh88_wIu5V&sig=Cg0ArKJSzMDE5l9Q8hxlEAE&uach_m=%5BUACH%5D&adurl=]]></Impression>
   <Creatives>
    <Creative id="138381721867" AdID="H0Hrk8zCNZI" sequence="1">
     <CreativeExtensions><CreativeExtension type="UniversalAdId"><UniversalAdId idRegistry="googlevideo">H0Hrk8zCNZI</UniversalAdId></CreativeExtension></CreativeExtensions>
     <Linear>
      <Duration>00:00:10</Duration>
      <TrackingEvents>
       <Tracking event="start"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=part2viewed&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="firstQuartile"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=videoplaytime25&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="midpoint"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=videoplaytime50&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="thirdQuartile"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=videoplaytime75&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="complete"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=videoplaytime100&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="mute"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=admute&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="unmute"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=adunmute&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="rewind"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=adrewind&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="pause"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=adpause&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="resume"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=adresume&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="creativeView"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=vast_creativeview&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="fullscreen"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=adfullscreen&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="acceptInvitationLinear"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=acceptinvitation&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="closeLinear"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=adclose&ad_mt=%5BAD_MT%5D]]></Tracking>
       <Tracking event="exitFullscreen"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=vast_exit_fullscreen&ad_mt=%5BAD_MT%5D]]></Tracking>
      </TrackingEvents>
      <VideoClicks>
       <ClickThrough id="GDFP"><![CDATA[https://pagead2.googlesyndication.com/pcs/click?xai=AKAOjsvha2HR3hxN-YrsxYlG4zaecfu7GT_HHbWo8YZOMvhPDGo4cmim9rscV1xo-nloOHmJrUYuxYhThwDi0h38ySQX3ZSENVlGC4-GgmYGGKI41ZAzJLgAjg31igjU5fkqXKrlbpF4mGZg9cfAQgtc7lVdX2MADxGWXTKFHbCh4nmMv_B6pU4dhgxIhfy2UV6pnWbSXmBw9EIwAa0zJN3weQAejUHC-dyTzJmRoWDrZYN95diHZDUfiJ7EQMYiZD0EjJkmDGzs6I3eT9NtOdGzAGPyagqeCPrXcj0TkP6wIW83nx5V_tY9LMDQIpBKZFnb7C1d2dUp9BhoEto4X16YnOepHgIYjQoEeYokMMCgIYC_rzZ1zSA4A393QJA&sig=Cg0ArKJSzNHfkZBaDO8m&fbs_aeid=%5Bgw_fbsaeid%5D&adurl=https://googleads.github.io/googleads-ima-html5/vsi/]]></ClickThrough>
      </VideoClicks>
      <Icons>
       <Icon program="GoogleWhyThisAd" width="18" height="18" xPosition="right" yPosition="bottom">
        <StaticResource creativeType="image/png"><![CDATA[https://imasdk.googleapis.com/formats/wta/help_outline_white_24dp_with_3px_trbl_padding.png?wp=ca-pub-9939518381636264]]></StaticResource>
        <IconClicks>
         <IconClickThrough><![CDATA[https://adssettings.google.com/whythisad?source=display&reasons=AdhHJETQuA4w0sIB8KRw2QTdhIY8-R0F5iCDIZwM7twC0uKlPRVDQVQQAtr4kNCq8KoY9edHdCQHRqpNal4gUfEbZUjnvT0e_7GeggRBOF_zMuMx7bTyc-Mjc9sD4fYSvLCgy3bMbxWXIJD-IMaPI5TgwZVLI39L25lfhenZGOSfYyL5futJ3I4f5iiVON2XAeyIKpB249LBufGfoQaWJj1Xr6Cqj0ma47HbcHwbr1-bmX-WJ_k-ZV_WCBzZEJDxmp2HkduTGzqeWX1qy-Fo-RqZOlBc5dw0WSo2ZDf-gZhA043DaILv29yXtehHOhRUonLWQQkZSTSbHFtfBQ-EzpPvi0_1PyFlF2xFQxy56uodOkH2a-YPZPdClCk5DcHlDjVzJyh8pfUlwMpyOa3BM7UP47gmIng-gKXI-ExWPY8t-BfkM6KngfZ_Y9EE1NbrMPz_7azC9Pulz07SuFuPL4NCUwNo3Ek311wEMRm4nj1EJh1Y8JspNgxcHw0-xIb-Yjxf5PQuTgeeyECHK8BEYxWnHGNC-nbkoeItn9o1DEW986rxEh1k9Ii2ZzZ4IHogtgDBCdLfWCahmuvkiloo2vsJLBZCkdGU4oV154tlNyiG1K1CrRSBiZpTMj8gYjSX1TsSBm2Q6SSrU-f5LnKIxlFKoiauUOTmhHklDjTZgAv7OZkGVolm6buOfLoDPt501qHf1SMbIMuZRQnSsKS--zfafHNp7qbiLQCP90rPwONEYrG9zWxYEONYEEbrqbpaDp9iX5RwSmqcRj5pH-JoQQW62CQ_gq4roE46cN_thNPYqIuBf4LibCk4I4CXaE6oJxCrYJKIv-dxbk1qQDGwK133utAGCz2J-K3U3AJgVcSEqejqoQ&opi=122715837]]></IconClickThrough>
        </IconClicks>
       </Icon>
      </Icons>
      <MediaFiles>
       <MediaFile id="GDFP" delivery="progressive" width="640" height="360" type="video/mp4" bitrate="140" scalable="true" maintainAspectRatio="true"><![CDATA[https://redirector.gvt1.com/videoplayback/id/f1be9c477e89fd68/itag/18/source/dclk_video_ads/acao/yes/cpn/_fSzLXyEANrWd2Eh/ctier/L/ei/SuIQaPrvK-21svQP6tja8A4/ip/0.0.0.0/requiressl/yes/rqh/1/susc/dvc/xpc/Eghovf3BOnoBAQ%3D%3D/expire/1777472970/sparams/expire,ei,ip,requiressl,acao,ctier,source,id,itag,rqh,susc,xpc/sig/AJfQdSswRgIhAK4z6FsxRlYdwavFgN8cGCxj0X-XgS2lwNv_tS1REzRSAiEA0mVPU76kq002Fv1awyMgT3SzcqP9UG7gbGJzFWDwtk0%3D/file/file.mp4]]></MediaFile>
       <MediaFile id="GDFP" delivery="progressive" width="1280" height="720" type="video/mp4" bitrate="237" scalable="true" maintainAspectRatio="true"><![CDATA[https://redirector.gvt1.com/videoplayback/id/f1be9c477e89fd68/itag/22/source/dclk_video_ads/acao/yes/cpn/_fSzLXyEANrWd2Eh/ctier/L/ei/SuIQaPrvK-21svQP6tja8A4/ip/0.0.0.0/requiressl/yes/rqh/1/susc/dvc/xpc/Eghovf3BOnoBAQ%3D%3D/expire/1777472970/sparams/expire,ei,ip,source,id,itag,requiressl,acao,ctier,rqh,susc,xpc/sig/AJfQdSswRAIgEbCqF-wTm_cr8stoFxbakQhNKOytrslVL5cN5vCgowwCIF7bxI_vWtzsOvoC-FXVhw1g60Npq1mstULneL7h0p7Q/file/file.mp4]]></MediaFile>
       <MediaFile id="GDFP" delivery="progressive" width="1280" height="720" type="video/mp4" bitrate="259" scalable="true" maintainAspectRatio="true"><![CDATA[https://redirector.gvt1.com/videoplayback/id/f1be9c477e89fd68/itag/106/source/dclk_video_ads/acao/yes/cpn/_fSzLXyEANrWd2Eh/ctier/L/ei/SuIQaPrvK-21svQP6tja8A4/ip/0.0.0.0/requiressl/yes/rqh/1/susc/dvc/xpc/Eghovf3BOnoBAQ%3D%3D/expire/1777472970/sparams/expire,ei,ip,requiressl,acao,ctier,source,id,itag,rqh,susc,xpc/sig/AJfQdSswRQIhALtJ8cLzvdt2fqTIMWMQJrws9FQ7j50yPaM74ueshacEAiB-mPTkdo-3KtNCwM6zfhTUyMFDvsO1Lu69GQrBfJvaGA%3D%3D/file/file.mp4]]></MediaFile>
       <MediaFile id="GDFP" delivery="progressive" width="854" height="480" type="video/mp4" bitrate="183" scalable="true" maintainAspectRatio="true"><![CDATA[https://redirector.gvt1.com/videoplayback/id/f1be9c477e89fd68/itag/109/source/dclk_video_ads/acao/yes/cpn/_fSzLXyEANrWd2Eh/ctier/L/ei/SuIQaPrvK-21svQP6tja8A4/ip/0.0.0.0/requiressl/yes/rqh/1/susc/dvc/xpc/Eghovf3BOnoBAQ%3D%3D/expire/1777472970/sparams/expire,ei,ip,requiressl,acao,ctier,source,id,itag,rqh,susc,xpc/sig/AJfQdSswRQIgdH-k_I0lOuWSukSpsnVvt6Ux-EJptJtlp-hyKMC7dLICIQDN4dmUpgTR9CffT8BQDCbg7dBSPWmR8Pxd9yTh_6arjg%3D%3D/file/file.mp4]]></MediaFile>
       <MediaFile id="GDFP" delivery="streaming" width="256" height="144" type="application/x-mpegURL" minBitrate="96" maxBitrate="494" scalable="true" maintainAspectRatio="true"><![CDATA[https://redirector.gvt1.com/api/manifest/hls_variant/id/f1be9c477e89fd68/itag/0/source/dclk_video_ads/acao/yes/cpn/_fSzLXyEANrWd2Eh/ctier/L/ei/SuIQaPrvK-21svQP6tja8A4/hfr/all/ip/0.0.0.0/keepalive/yes/playlist_type/DVR/requiressl/yes/rqh/5/susc/dvc/xpc/Eghovf3BOnoBAQ%3D%3D/expire/1777472970/sparams/expire,ei,ip,hfr,requiressl,source,playlist_type,acao,ctier,id,itag,rqh,susc,xpc/sig/AJfQdSswRQIgSWk3e1B_CSvH6J0cI0qmIXLEKDMnI8Gb3rq7uhWyTyMCIQCb2v2oKdlQqiXqhpIS_5qutIVspYSzsrvIV8opqKNlqw%3D%3D/file/index.m3u8]]></MediaFile>
       <MediaFile id="GDFP" delivery="streaming" width="0" height="0" type="application/dash+xml" scalable="true" maintainAspectRatio="true"><![CDATA[https://redirector.gvt1.com/api/manifest/dash/id/f1be9c477e89fd68/itag/0/source/dclk_video_ads/acao/yes/cpn/_fSzLXyEANrWd2Eh/ctier/L/ei/SuIQaPrvK-21svQP6tja8A4/hfr/all/ip/0.0.0.0/keepalive/yes/playlist_type/DVR/requiressl/yes/rqh/2/susc/dvc/xpc/Eghovf3BOnoBAQ%3D%3D/expire/1777472970/sparams/expire,ei,ip,playlist_type,acao,ctier,id,itag,hfr,requiressl,source,rqh,susc,xpc/sig/AJfQdSswRQIgatLZIR1gnzt3dEDKVbhxhvh2Jd7M94E5gvV2OO9cQ0wCIQDhQqW--RhOAw5_BdYF6LAmCMQ7Gf2Z7089EaEEpuP3yQ%3D%3D/file/index.mpd]]></MediaFile>
      </MediaFiles>
     </Linear>
    </Creative>
    <Creative id="138381056765" sequence="1">
     <CompanionAds>
      <Companion id="138381056765" width="300" height="250">
       <StaticResource creativeType="image/png"><![CDATA[https://pagead2.googlesyndication.com/simgad/4446644594546952943]]></StaticResource>
       <TrackingEvents>
        <Tracking event="creativeView"><![CDATA[https://pagead2.googlesyndication.com/pcs/view?xai=AKAOjstc_5B2Cmmx7JoHt1xe-QsXFiISMFj2dOTYZlicu687YF-gC7JBGc5uDiNtn1Ir77hO_Ex7uiLf1PG0fv0PsyRUKMNbr4EvIpMv532nNfbfsT8AOtv4tR6wv9yP9K659k0iXCna5ABEFheOxdQddcxZgo5EVDYA3CFPAj5RDOWM3fxQ3SDaSa7CYU8UeuLOFVcD0XKVemhsSiNnyVdm7AVN8z40mOHAA2RcVL8IAJRsxCuwKd_m_wsCCSk5vuXMdXXyQUJH5usVpi-edOpea-K1GYIhuFolB7Vpw2uBdtRn6PWsC7LH9ylp41j0n94NUisc-FBI1RdFiwQaaVe6W4DRrL5A6mnPID7QlSpBymb8tez-oMVdRO2OGTMUebls4AtSKz6VN-KT&sig=Cg0ArKJSzGmY1MzNtXN9EAE&uach_m=%5BUACH%5D&adurl=]]></Tracking>
       </TrackingEvents>
       <CompanionClickThrough><![CDATA[https://pagead2.googlesyndication.com/pcs/click?xai=AKAOjsuPgM9N8cWIPKKgmboJNFGHomWhC2aVR1PsYSuCjRrwuzPTKE-JJdggAxMlG9KEhIX39gFf03Ownq0s4lM-BLINQ1rftqiUb940rEO__A-lCMpTYGXzxT7NOI7SWX8xaG_D8K7ub3JLaAyzTNRatekUjZ7DT6afqJnHUGJqB1E1ppqBR5oMFupfkXnPKhRuseVPqS_6e7RxQNGbevE9JY9VUKze_dNVwqU8FEYWWe28B-p14loE4ono4-G1VlkafrT1NXLIX_vy6TrXSQOho0xjsnWeLIVIefVFRdLF3evYOnBLMWkkFoeC7EUSjerLXhYgY5zEqp5MU9snbHt0PRl28z89For6lzP217WBKtXW5M0EG7moqvKct3k&sig=Cg0ArKJSzEYxck5RWJip&fbs_aeid=%5Bgw_fbsaeid%5D&adurl=https://googleads.github.io/googleads-ima-html5/vsi/]]></CompanionClickThrough>
      </Companion>
      <Companion id="138381188849" width="728" height="90">
       <StaticResource creativeType="image/png"><![CDATA[https://pagead2.googlesyndication.com/simgad/7802555171787573026]]></StaticResource>
       <TrackingEvents>
        <Tracking event="creativeView"><![CDATA[https://pagead2.googlesyndication.com/pcs/view?xai=AKAOjst1NrYL44vdhDZZaA8EiXnnUeKAEqiWRDc1gGU-JtI2VAv5C0jPMROD9m3aCspyZEvoOKMGJxtj_zl2zvEeVtnHHHtTqqLGwaeGQWC9ZIGEb5sKYlc6u7JTKmWVU6UXDC1FxR7xm1RNqOD5Fl3QbgoPpN_ktd0o2zTZiW2IJziQ8J-anWaNlUMmF16lXLoKl3kD8-syIGj8dIWWP1wNIlHYWSppThgAHHm9Z8uXKweaUnYTHaTUlbfgMSiGNxGL71-yc5OiQyh1_PXDS0I5PILOeppDfZEnDtOAXYWRPdnivd1tTQ-hTh18BXUcfCrOoWDjxFs0Xe28y7RHWdRl_C2VhH6dH-zsjr3QRtwZZhymukHeW5k-RL2mQK9MZ6ZPrxPTUPvnj_Au&sig=Cg0ArKJSzC5FLiD2Bch3EAE&uach_m=%5BUACH%5D&adurl=]]></Tracking>
       </TrackingEvents>
       <CompanionClickThrough><![CDATA[https://pagead2.googlesyndication.com/pcs/click?xai=AKAOjsvC4KR0_jwQMkltgX_naCTuRaU-uMZtq63TMprqKj-kL-tdZhU-kw_ar0Qh2OuEU53HYVFY0MtWOue5RB94dj3fG4ktYtVeha__wnVzfphSy-1Cirzdft3629ItihNpCHvTbZ1YfxZDf_T5YhKEujkYtimVTKJDFIsLIoEBs4db1eUPIvfEJH09b_RtM4CrHwcz_4CfXoG9wp768jKJeJaW3ZVtcUjqo4inM1P7U7TrvJgzv-KHnWfjdEUlUKXgVoJt88eq82fNZsen6BMgagTqu83kRNh5XEC0tWQbMclENpdf2pKRMlhjvyOH0nWsraTPT9iAOMJKEIWoaB1aVZW7gB1gW_yxQissmFTH2OOWIUWc_NB6ByH-Wm0&sig=Cg0ArKJSzNgIzfc_hoj4&fbs_aeid=%5Bgw_fbsaeid%5D&adurl=https://googleads.github.io/googleads-ima-html5/vsi/]]></CompanionClickThrough>
      </Companion>
     </CompanionAds>
    </Creative>
   </Creatives>
   <Extensions><Extension type="waterfall" fallback_index="0"/><Extension type="geo"><Country>NL</Country><Bandwidth>0</Bandwidth></Extension><Extension type="metrics"><FeEventId>SuIQaMaqKqWD1PIP-NPO4Ao</FeEventId><AdEventId>CJrKwYe6_YwDFXc7vwQd4AwzsQ</AdEventId></Extension><Extension type="ShowAdTracking"><CustomTracking><Tracking event="show_ad"><![CDATA[https://pagead2.googlesyndication.com/pcs/view?xai=AKAOjsvWPDNZUriJvTpGztZreJdv9IYN6sxE15K4PSWl5T66gpP46TTbBY7xG6LDhTzm9i87u5ltnwtajH6T-Cg7r7CGeWnU1gsJqYS4yMZfBhvO-HWti35DSvHhq9MqJnVOxmniFvfNtp1LIWQa6jtH3kwtfHuNfM_xV0CNidsZQmozVp3ImvNIXQgGch5xzwcNtT-dzPUCcHVF-kzgMM0RLJBjTPVqJSuu3W5_IANCOEn-3TtAkIWE1d_yBk0hblySSCsSpyIw7jq2hSbjqHyM-mJ_kQeje_aI4ePqptAxXRQmr-NSY_fUFW-n_DqhKOhjO6ONl_Xom9ah5fXRNrtTKNdqKGeDYTWnb5WNLMfYZNrPGNLnRCpdtM8ljmm9OdNhfOycO7RCXlO9MWc&sig=Cg0ArKJSzKh12huVKWgfEAE&uach_m=%5BUACH%5D&adurl=]]></Tracking></CustomTracking></Extension><Extension type="video_ad_loaded"><CustomTracking><Tracking event="loaded"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=video_ad_loaded]]></Tracking></CustomTracking></Extension><Extension type="esp"><EspLibrary LibraryName="uidapi.com" LibraryUrl=""/><EspLibrary LibraryName="euid.eu" LibraryUrl=""/><EspLibrary LibraryName="liveramp.com" LibraryUrl=""/><EspLibrary LibraryName="esp.criteo.com" LibraryUrl=""/><EspLibrary LibraryName="liveintent.com" LibraryUrl=""/><EspLibrary LibraryName="liveintent.triplelift.com" LibraryUrl=""/><EspLibrary LibraryName="id5-sync.com" LibraryUrl=""/><EspLibrary LibraryName="yahoo.com" LibraryUrl=""/><EspLibrary LibraryName="epsilon.com" LibraryUrl=""/><EspLibrary LibraryName="pubcid.org" LibraryUrl=""/><EspLibrary LibraryName="mygaruID" LibraryUrl=""/><EspLibrary LibraryName="justtag.com" LibraryUrl=""/><EspLibrary LibraryName="utiq.com" LibraryUrl=""/><EspLibrary LibraryName="audigent.com" LibraryUrl=""/><EspLibrary LibraryName="liveintent.indexexchange.com" LibraryUrl=""/><EspLibrary LibraryName="bidswitch.net" LibraryUrl=""/><EspLibrary LibraryName="pubmatic.com" LibraryUrl=""/><EspLibrary LibraryName="openx.net" LibraryUrl=""/><EspLibrary LibraryName="intentiq.com" LibraryUrl=""/><EspLibrary LibraryName="adserver.org" LibraryUrl=""/><EspLibrary LibraryName="rubiconproject.com" LibraryUrl=""/><EspLibrary LibraryName="intimatemerger.com" LibraryUrl=""/><EspLibrary LibraryName="ceeid.eu" LibraryUrl=""/><EspLibrary LibraryName="crwdcntrl.net" LibraryUrl=""/></Extension><Extension type="heavy_ad_intervention"><CustomTracking><Tracking event="heavy_ad_intervention_cpu"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=heavy_ad_intervention_cpu]]></Tracking><Tracking event="heavy_ad_intervention_network"><![CDATA[https://pagead2.googlesyndication.com/pagead/interaction/?ai=BoODGSuIQaJr9Kvf2_NUP4JnMiQuD6qWVRgAAABABII64hW84AViLgsbBgwRgkcSXhYAYugEKNzI4eDkwX3htbMgBBcACAuACAOoCJy8yMTc3NTc0NDkyMy9leHRlcm5hbC9zaW5nbGVfYWRfc2FtcGxlc_gC8NEegAMBkAPIBpgD4AOoAwHgBAHSBQYQj6XEiRagBiOoB7i-sQKoB_PRG6gHltgbqAeqm7ECqAfgvbECqAeaBqgH_56xAqgH35-xAqgH-MKxAqgH-8KxAtgHAeAHAdIILAiR4YBwEAEYHTIF64uAgCA6DIBAgICAgICUruADIEi9_cE6WJGZwYe6_YwD2AgCgAoFmAsBqg0CTkzqDRMIw5LCh7r9jAMVdzu_BB3gDDOx8A0B0BUB-BYBgBcB&sigh=elGxVUvGK8o&label=heavy_ad_intervention_network]]></Tracking></CustomTracking></Extension><Extension type="IconClickFallbackImages"><IconClickFallbackImages program="GoogleWhyThisAd"><IconClickFallbackImage width="1920" height="1080"><AltText>Why this ad? This ad is based on: * General factors like the app you&#39;re using, the time of day, or your approximate location. You can update your options for ads in this device&#39;s settings.</AltText><StaticResource creativeType="image/png"><![CDATA[https://pubads.g.doubleclick.net/ata-qr?url=aHR0cHM6Ly9hZHNzZXR0aW5ncy5nb29nbGUuY29tL3doeXRoaXNhZD9zb3VyY2U9ZGlzcGxheSZyZWFzb25zPUFkaEhKRVRRdUE0dzBzSUI4S1J3MlFUZGhJWTgtUjBGNWlDRElad003dHdDMHVLbFBSVkRRVlFRQXRyNGtOQ3E4S29ZOWVkSGRDUUhScXBOYWw0Z1VmRWJaVWpudlQwZV83R2VnZ1JCT0Zfek11TXg3YlR5Yy1NamM5c0Q0ZllTdkxDZ3kzYk1ieFdYSUpELUlNYVBJNVRnd1pWTEkzOUwyNWxmaGVuWkdPU2ZZeUw1ZnV0SjNJNGY1aWlWT04yWEFleUlLcEIyNDlMQnVmR2ZvUWFXSmoxWHI2Q3FqMG1hNDdIYmNId2JyMS1ibVgtV0pfay1aVl9XQ0J6WkVKRHhtcDJIa2R1VEd6cWVXWDFxeS1Gby1ScVpPbEJjNWR3MFdTbzJaRGYtZ1poQTA0M0RhSUx2Mjl5WHRlaEhPaFJVb25MV1FRa1pTVFNiSEZ0ZkJRLUV6cFB2aTBfMVB5RmxGMnhGUXh5NTZ1b2RPa0gyYS1ZUFpQZENsQ2s1RGNIbERqVnpKeWg4cGZVbHdNcHlPYTNCTTdVUDQ3Z21JbmctZ0tYSS1FeFdQWTh0LUJma002S25nZlpfWTlFRTFOYnJNUHpfN2F6QzlQdWx6MDdTdUZ1UEw0TkNVd05vM0VrMzExd0VNUm00bmoxRUpoMVk4SnNwTmd4Y0h3MC14SWItWWp4ZjVQUXVUZ2VleUVDSEs4QkVZeFduSEdOQy1uYmtvZUl0bjlvMURFVzk4NnJ4RWgxazlJaTJaelo0SUhvZ3RnREJDZExmV0NhaG11dmtpbG9vMnZzSkxCWkNrZEdVNG9WMTU0dGxOeWlHMUsxQ3JSU0JpWnBUTWo4Z1lqU1gxVHNTQm0yUTZTU3JVLWY1TG5LSXhsRktvaWF1VU9UbWhIa2xEalRaZ0F2N09aa0dWb2xtNmJ1T2ZMb0RQdDUwMXFIZjFTTWJJTXVaUlFuU3NLUy0temZhZkhOcDdxYmlMUUNQOTByUHdPTkVZckc5eld4WUVPTllFRWJycWJwYURwOWlYNVJ3U21xY1JqNXBILUpvUVFXNjJDUV9ncTRyb0U0NmNOX3RoTlBZcUl1QmY0TGliQ2s0STRDWGFFNm9KeENyWUpLSXYtZHhiazFxUURHd0sxMzN1dEFHQ3oySi1LM1UzQUpnVmNTRXFlanFvUSZvcGk9MTIyNzE1ODM3&hl=en&wp=ca-pub-9939518381636264&p=1&gqi=SuIQaMaqKqWD1PIP-NPO4Ao]]></StaticResource></IconClickFallbackImage></IconClickFallbackImages></Extension><Extension type="companion_about_this_ad"><Icon program="GoogleWhyThisAd" width="18" height="18" xPosition="right" yPosition="bottom"><StaticResource creativeType="image/png"><![CDATA[https://imasdk.googleapis.com/formats/wta/help_outline_white_24dp_with_3px_trbl_padding.png?wp=ca-pub-9939518381636264]]></StaticResource><IconClicks><IconClickThrough><![CDATA[https://adssettings.google.com/whythisad?source=display&reasons=AdhHJEQdppAbykkOyYmEWDs6DfL_QBzN-nDKypG_VkSgqArGTP9CNAi57wafQJQ6UFnHFIpUK2_0HI2exJiHw7oxoz0RSjRDIWvKy4dWWn6G4b7lgoGxrvTdqeHlXzyjVoaKiye9chipViOjSOTLxFKTbiY7sJiKkbXNb3iN0lFCA6Es32apXWwLZy95VwnvWzNh9Wamf9dh2lr3HE59OzzcpyPrZwiIq84Ok0vWrsSgkE72JIQJy4zn4GQwGwAZeeBct8wA_OC7jo3U8GTYSVPJO5lIBBOZCgliFVrAVoeFasRTWU_9nmF5KePIXLhW2FKGMd-KiVVxojs9uqs37i8eDVCJP-u2yeo-1QWFNZ3HomGdEIvv50xvl2iVoxyf8FNdSE4grhuVK5JFaUTyQ8aunoC69TKfJkKRFVlw6Okh9QD2k7AwVDQwbW1IABEwHp7ZLRd9LuPSu6XWcdmESkQarg0gmmak8HDX69m_xhdYdlVKz23cZpoL83Qgu403z8VPO4cO6MIMYVVCFeJ3lP4spMejrs7z9BjfcQmpHYmXSNDi9mzmiA4M3X8gt5DJ1EY8X01Orw7mdexWGqRaTz7-b0FOMpBSYjAc6Rz8M5Tr6Ihi9gsGle7suQDyfng4eYAEViAlO17WAHX3A1cgOSPNcHqSF_GHDxGc8up49gll11ADxtwrZMIZwv2WUl5tJc48rJPkvTRZYLHZMrMKS5egnEZfC4CNsLhgrdjXfXZv1tiv8jCnwg0NFwkhaFWNHRLg-uhPt2eQcAUx5LkqCmJyx6vofxoRXsW7jXJmevMzppU8hraXAm8QHtweasUFLwDYngBER8JP1WcyVCP2uDKv2w8jCrtyJIvQzsi6OGH-bTShdw&opi=122715837]]></IconClickThrough><IconClickFallbackImages><IconClickFallbackImage width="640" height="226"><AltText>Why this ad? This ad is based on: * General factors like the app you're using, the time of day, or your approximate location. You can update your options for ads in this device's settings.</AltText><StaticResource creativeType="image/png"><![CDATA[https://imasdk.googleapis.com/formats/wta/contextual_bks.png?wp=ca-pub-9939518381636264]]></StaticResource></IconClickFallbackImage></IconClickFallbackImages></IconClicks></Icon></Extension></Extensions>
  </InLine>
 </Ad>
</VAST>
        """.trimIndent()
		val playoutOverrides: Map<String, String?> = mapOf()
		rendererView?.bootstrap(mapOf("code" to "1234abcd", "vastXml" to vastXml), null, playoutOverrides)
	}

}
