package com.sample.mvvm.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Create view model using Dagger dependencies graph.
 *
 * So any view model with @Inject constructor() can be used as VM type argument.
 *
 * @param VM should match type of requested view model. Otherwise Dagger compilation might fail.
 */
class ViewModelByDaggerFactory<VM : ViewModel> @Inject constructor(
    private val viewModelProvider: Provider<VM>
) : ViewModelProvider.Factory {

    private fun newViewModel(): VM = viewModelProvider.get()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //ViewModel as declared by generic type of ViewModelByDaggerFactory
        val newViewModel = newViewModel()

        return safeCast(modelClass, newViewModel)
    }

    /**
     * @return newViewModel itself if its class is sub-type generic T or throws otherwise.
     *
     * Note: if ViewModel generic type is not matching type of requested view model:
     * dagger compilation might fail because dagger will not be able to find class matching.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewModel> safeCast(modelClass: Class<T>, newViewModel: VM): T {
        if (modelClass.isInstance(newViewModel)) {
            return newViewModel as T
        } else {
            throw IllegalArgumentException("Requested ViewModel type $modelClass is not matching generic type declared on ViewModelByDaggerFactory: " + newViewModel.javaClass)
        }
    }
}