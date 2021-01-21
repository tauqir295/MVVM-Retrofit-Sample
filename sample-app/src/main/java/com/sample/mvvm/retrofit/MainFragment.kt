package com.sample.mvvm.retrofit

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mvvm.retrofit.ui.CatLibLandingActivity
import com.mvvm.retrofit.utils.CAT_URL
import com.mvvm.retrofit.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment MainFragment.
         */
        @JvmStatic
        fun newInstance() = MainFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.findViewById<Button>(R.id.launchCatJourneyBtn)?.apply {
            setOnClickListener {
                navigateToCatJourneyLibrary()
            }
        }
    }

    /**
     * navigate to cat lib for fetching the cat image path.
     */
    private fun navigateToCatJourneyLibrary() {
        startActivityForResult(Intent((requireActivity() as MainActivity), CatLibLandingActivity::class.java), 1234)
    }

    /**
     * On the basis of data received from cat lib the image is loaded on the image view
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == RESULT_OK && data != null) {
            data.getStringExtra(CAT_URL)?.let {
                Logger.d("Main fragment", it)
                requireActivity().findViewById<ImageView>(R.id.catImageView).apply {
                    if (it.isNotEmpty()) {
                        Glide.with(requireContext())
                            .load(it)
                            .placeholder(com.mvvm.retrofit.R.drawable.placeholder_image)
                            .into(this)
                    }
                }
            }
        }
    }
}