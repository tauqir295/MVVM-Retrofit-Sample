package com.mvvm.retrofit.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mvvm.retrofit.R
import com.mvvm.retrofit.databinding.LandingFragmentBinding
import com.mvvm.retrofit.network.model.Cat
import com.mvvm.retrofit.utils.CAT_URL
import com.mvvm.retrofit.utils.Logger
import com.mvvm.retrofit.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatLibLandingFragment : Fragment(), CatRecyclerViewAdapter.OnRecyclerItemClickListener {

    private val viewModelCatLib: CatLibLandingViewModel by viewModels()
    private val adapter = CatRecyclerViewAdapter()
    private lateinit var binding: LandingFragmentBinding

    companion object {
        fun newInstance() = CatLibLandingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.landing_fragment, container, false)
        binding.catListRv.adapter = adapter

        adapter.setOnItemClickListener(this)

        // Specify the current fragment as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe updates.
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        requireActivity().findViewById<TextView>(R.id.toolbarTitle).text = getString(R.string.cat_list)

        requireActivity().findViewById<ImageView>(R.id.toolbarImage).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        viewModelCatLib.catList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { catList ->

                        Logger.d("LandingFragment SUCCESS", catList.toString())

                        binding.progressBar.visibility = View.GONE
                        adapter.updateCatList(catList)
                    }

                }
                Status.LOADING -> {
                    Logger.d("LandingFragment LOADING", "LOADING")
                    binding.progressBar.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    //Handle Error
                    Logger.d("LandingFragment ERROR", it.message.toString())
                    binding.progressBar.visibility = View.GONE

                }
            }
        })
    }

    override fun onItemClick(item: View?, cat: Cat) {

        (requireActivity() as CatLibLandingActivity).apply {
            val resultIntent = Intent()
            resultIntent.putExtra(CAT_URL, cat.url)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}