package com.ciphra.android.thecattery.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ciphra.android.thecattery.Cat
import com.ciphra.android.thecattery.R
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
        viewModel.retrieveOneRandomCat()

        val loadCatObserver = Observer<Cat>{
                Picasso.get().load(it.url).into(cat_image_view)
             }
        viewModel.thisCat.observe(requireActivity(), loadCatObserver)
    }

}