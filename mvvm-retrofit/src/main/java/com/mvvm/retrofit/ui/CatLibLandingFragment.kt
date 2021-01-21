package com.mvvm.retrofit.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mvvm.retrofit.R
import com.mvvm.retrofit.databinding.LandingFragmentBinding
import com.mvvm.retrofit.network.model.Cat
import com.mvvm.retrofit.ui.adapter.CatRecyclerViewAdapter
import com.mvvm.retrofit.ui.adapter.LoadMoreListener
import com.mvvm.retrofit.utils.CAT_URL
import com.mvvm.retrofit.utils.Logger
import com.mvvm.retrofit.utils.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * Landing screen for selecting the cat
 */
@AndroidEntryPoint
class CatLibLandingFragment : Fragment(), CatRecyclerViewAdapter.OnRecyclerItemClickListener, LoadMoreListener.OnLoadMoreListener  {

    private val viewModelCatLib: CatLibLandingViewModel by viewModels()
    private lateinit var adapter: CatRecyclerViewAdapter
    private lateinit var binding: LandingFragmentBinding
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var loadMoreListener : LoadMoreListener
    private var pageCount = 1

    companion object {
        fun newInstance() = CatLibLandingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // data binding is used
        binding = DataBindingUtil.inflate(inflater, R.layout.landing_fragment, container, false)

        binding.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = CatRecyclerViewAdapter(layoutManager)
            catListRv.layoutManager = layoutManager
            catListRv.adapter = adapter

            adapter.setOnItemClickListener(this@CatLibLandingFragment)

            // Specify the current fragment as the lifecycle owner of the binding.
            // This is necessary so that the binding can observe updates.
            binding.lifecycleOwner = this@CatLibLandingFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPagination()

        setupObserver()

        requireActivity().let {
            it.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {_->
                // Handle navigation icon press
                it.onBackPressed()
            }

            it.findViewById<SwitchMaterial>(R.id.switchButton).setOnCheckedChangeListener { _, isChecked: Boolean ->
                switchBetweenGridListLayout(if (isChecked) {
                    3
                } else {
                    1
                })
            }
        }
    }

    /**
     * Observe update on view model live data
     */
    private fun setupObserver() {
        viewModelCatLib.catList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { catList ->
                        binding.progressBar.visibility = View.GONE

                        if (catList.isNotEmpty()) {
                            adapter.updateCatList(catList)
                            requireActivity().findViewById<LinearLayout>(R.id.switchContainer).visibility = View.VISIBLE
                            setScrollVariablesStatus()
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
                        }
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
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                }
            }
        })
    }

    /**
     * switch between list or grid view of items
     * @param - [spanCount] - this variable tells layout manager about number of items in a row
     * in grid
     */
    private fun switchBetweenGridListLayout(spanCount: Int) {
        layoutManager.spanCount = spanCount
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    /**
     * handles click of item in grid/list and pass to the calling activity
     *
     * @param - [item] - view which is clicked
     * @param - [cat] - cat list item which is clicked
     */
    override fun onItemClick(item: View?, cat: Cat) {

        (requireActivity() as CatLibLandingActivity).apply {
            val resultIntent = Intent()
            resultIntent.putExtra(CAT_URL, cat.url)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    /**
     * setting up scroll listener on recycler view
     */
    private fun setupPagination() {
        loadMoreListener = LoadMoreListener(layoutManager, this)
        loadMoreListener.setLoaded()
        binding.catListRv.addOnScrollListener(loadMoreListener)
    }

    /**
     * disabling the progress view and loaded variable
     */
    private fun setScrollVariablesStatus() {
        loadMoreListener.setLoaded()
    }

    override fun onLoadMore() {
        activity?.let {
            viewModelCatLib.fetchCatList(10, pageCount++)
        }
    }
}