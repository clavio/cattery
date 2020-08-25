package com.ciphra.android.thecattery.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.ciphra.android.thecattery.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        thumbs_up_button.alpha = .5f
        thumbs_down_button.alpha = .5f
        val loadCatObserver = Observer<Cat>{
                thumbs_up_button.setOnClickListener {  }
                thumbs_down_button.setOnClickListener {  }
                thumbs_up_button.alpha = .5f
                thumbs_down_button.alpha = .5f
                Picasso.get().load(it.url).into(cat_image_view, object: Callback {

                    override fun onSuccess() {
                        thumbs_up_button.setOnClickListener { viewModel.voteUp() }
                        thumbs_down_button.setOnClickListener { viewModel.voteDown() }
                        thumbs_up_button.alpha = 1f
                        thumbs_down_button.alpha = 1f
                    }

                    override fun onError(e : Exception) {
                        viewModel.retrieveOneRandomCat()
                    }
                })
                viewModel.isProcessingCat = false
        }
        viewModel.thisCat.observe(requireActivity(), loadCatObserver)
        val failureObserver = Observer<Boolean>{
            displayError(REQUEST_ERROR)
        }
        viewModel.failure.observe(requireActivity(), failureObserver)

        if(isConnected(requireContext())) viewModel.retrieveOneRandomCat() else displayError(NO_INTERNET)
    }

    fun displayError(code : Int){
        main.removeAllViews()
        val errorTextView = TextView(requireContext())
        errorTextView.gravity = Gravity.CENTER
        errorTextView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val errorText = when(code){
            NO_INTERNET -> getString(R.string.internet_required)
            REQUEST_ERROR -> getString(R.string.error_processing)
            else -> ""
        }
        errorTextView.text = errorText
        main.addView(errorTextView)
    }
}