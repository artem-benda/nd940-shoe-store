package com.udacity.shoestore.screen.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoeListViewModel : ViewModel() {
    private val _shouldNavigateToDetails = MutableLiveData<Boolean>(false)
    val shouldNavigateToDetails: LiveData<Boolean>
        get() = _shouldNavigateToDetails

    fun onButtonClicked() {
        _shouldNavigateToDetails.value = true
    }

    fun onNavigateToDetailsComplete() {
        _shouldNavigateToDetails.value = false
    }
}
