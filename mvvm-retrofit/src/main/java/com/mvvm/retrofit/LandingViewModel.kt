package com.mvvm.retrofit

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LandingViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
    ) :
    ViewModel() {

        val hello = "hello world"
    }