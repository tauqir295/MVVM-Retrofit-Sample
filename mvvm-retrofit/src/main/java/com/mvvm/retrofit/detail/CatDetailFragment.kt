package com.mvvm.retrofit.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mvvm.retrofit.R
import com.mvvm.retrofit.databinding.FragmentCatDetailBinding
import com.mvvm.retrofit.network.model.Cat
import com.mvvm.retrofit.utils.CAT_DETAILS
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [CatDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class CatDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCatDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cat_detail, container, false)

        val cat = arguments?.getParcelable(CAT_DETAILS) as Cat?
        if (URLUtil.isValidUrl(cat?.url)) {
            binding.cat = cat
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CatDetailFragment.
         */
        @JvmStatic
        fun newInstance() = CatDetailFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<ImageView>(R.id.toolbarImage).setOnClickListener {
            requireActivity().onBackPressed()
        }

    }
}