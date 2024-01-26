package com.example.zakiahmedkotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.zakiahmedkotlin.databinding.FragmentWebscreenBinding

class WebScreenFragment : Fragment() {

    lateinit var binding : FragmentWebscreenBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWebscreenBinding.inflate(inflater, container, false)
        val view : View = binding.root

        val url = arguments?.getString("URL");

        if (url!!.isNotEmpty()) {
            val webSettings: WebSettings = binding.webView.settings

            webSettings.javaScriptCanOpenWindowsAutomatically = true
            webSettings.javaScriptEnabled = true
            webSettings.pluginState = WebSettings.PluginState.ON

            binding.webView.webViewClient = WebViewClient()

            binding.webView.loadUrl(url!!)
        }


        return view
    }

    override fun onDetach() {
        super.onDetach()
    }
}