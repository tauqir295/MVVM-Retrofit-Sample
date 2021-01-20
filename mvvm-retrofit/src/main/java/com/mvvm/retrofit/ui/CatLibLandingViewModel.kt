package com.mvvm.retrofit.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.retrofit.network.model.Cat
import com.mvvm.retrofit.network.repository.MainRepository
import com.mvvm.retrofit.utils.NetworkHelper
import com.mvvm.retrofit.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class CatLibLandingViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _cats = MutableLiveData<Resource<List<Cat>>>()
    val catList: LiveData<Resource<List<Cat>>>
        get() = _cats

    init {
        fetchCatList()
    }

    private fun fetchCatList(limit: Int = 30, page: Int = 1) {
        viewModelScope.launch {
            _cats.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getCats(limit, page).let {
                        if (it.isSuccessful) {
                            _cats.postValue(Resource.success(it.body()))
                        } else _cats.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                } catch (e: IOException) {
                    _cats.postValue(Resource.error("No internet connection", null))
                }

            } else _cats.postValue(Resource.error("No internet connection", null))
        }
    }
}