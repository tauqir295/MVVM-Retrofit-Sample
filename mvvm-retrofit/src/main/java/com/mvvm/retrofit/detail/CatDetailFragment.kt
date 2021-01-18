package com.mvvm.retrofit.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mvvm.retrofit.R

/**
 * A simple [Fragment] subclass.
 * Use the [CatDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CatDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat_detail, container, false)
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
}