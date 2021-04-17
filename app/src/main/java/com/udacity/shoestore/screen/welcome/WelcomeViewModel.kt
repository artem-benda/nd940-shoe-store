package com.udacity.shoestore.screen.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {
    private val _shouldNavigateToList = MutableLiveData<Boolean>(false)
    val shouldNavigateToList: LiveData<Boolean>
        get() = _shouldNavigateToList

    fun onButtonClicked() {
        _shouldNavigateToList.value = true
    }

    fun onNavigateToListComplete() {
        _shouldNavigateToList.value = false
    }
}
