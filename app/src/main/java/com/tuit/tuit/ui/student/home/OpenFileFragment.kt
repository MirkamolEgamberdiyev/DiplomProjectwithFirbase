package com.tuit.tuit.ui.student.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tuit.tuit.databinding.FragmentOpenFileBinding
import java.net.URLEncoder

class OpenFileFragment : Fragment() {
    private var _binding: FragmentOpenFileBinding? = null
    private val binding get() = _binding!!
    private var url = ""
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString("key")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpenFileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            webView.webViewClient = WebViewClient()
            progressDialog = ProgressDialog(requireContext())
            progressDialog!!.setMessage("Loading...")
            progressDialog!!.show()

            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {
                    if (progressDialog!!.isShowing) {
                        progressDialog!!.dismiss()
                    }
                }

                override fun onReceivedError(
                    view: WebView,
                    errorCode: Int,
                    description: String,
                    failingUrl: String
                ) {
                    view.loadUrl(url)
                    Toast.makeText(requireContext(), "Error:$description", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            webView.settings.builtInZoomControls = true
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + URLEncoder.encode(url, "ISO-8859-1"))
            Log.d("@@@", "onViewCreated: $url ")

        }
    }

}