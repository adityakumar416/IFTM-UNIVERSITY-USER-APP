package com.example.iftm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.iftm.MainActivity
import com.example.iftm.databinding.FragmentKnowMoreWebViewBinding

class KnowMoreWebViewFragment : Fragment() {

    private lateinit var binding: FragmentKnowMoreWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKnowMoreWebViewBinding.inflate(layoutInflater, container, false)

        (activity as MainActivity).binding.navigation.visibility = View.GONE
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.loadUrl("https://www.iftmuniversity.ac.in/iftmuniversity/ug.php")

        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

        }


        return binding.root
    }

}