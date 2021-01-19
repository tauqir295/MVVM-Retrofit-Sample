package com.mvvm.retrofit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvm.retrofit.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatLibLandingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_lib_landing)
        supportFragmentManager.beginTransaction().replace(
            R.id.catLibContainer,
            CatLibLandingFragment.newInstance()
        ).commitNow()
    }
}