package com.sample.mvvm.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvm.retrofit.ui.LandingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            MainFragment.newInstance()
        ).commitNow()
    }
}