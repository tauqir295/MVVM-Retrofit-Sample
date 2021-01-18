package com.mvvm.retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mvvm.retrofit.R
import com.mvvm.retrofit.databinding.LandingFragmentBinding
import com.mvvm.retrofit.detail.CatDetailFragment
import com.mvvm.retrofit.network.model.Cat
import com.mvvm.retrofit.utils.CAT_DETAILS
import com.mvvm.retrofit.utils.Logger
import com.mvvm.retrofit.utils.Status
import com.mvvm.retrofit.utils.replaceWithNextFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : Fragment(), CatRecyclerViewAdapter.OnRecyclerItemClickListener {

    private val viewModel: LandingViewModel by viewModels()
    private val adapter = CatRecyclerViewAdapter()
    private lateinit var binding: LandingFragmentBinding

    companion object {
        fun newInstance() = LandingFragment()
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

        requireActivity().findViewById<ImageView>(R.id.toolbarImage).setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupObserver() {
        viewModel.catList.observe(viewLifecycleOwner, Observer {
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
        replaceWithNextFragment(
            this.id,
            requireActivity().supportFragmentManager,
            CatDetailFragment.newInstance(),
            Bundle().apply {
                putParcelable(CAT_DETAILS, cat)
            }
        )
    }

}