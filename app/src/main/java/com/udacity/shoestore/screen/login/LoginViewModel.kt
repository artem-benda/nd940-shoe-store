package com.udacity.shoestore.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _shouldNavigateToWelcome = MutableLiveData<Boolean>(false)
    val shouldNavigateToWelcome: LiveData<Boolean>
        get() = _shouldNavigateToWelcome

    fun onButtonClicked() {
        _shouldNavigateToWelcome.value = true
    }

    fun onNavigateToWelcomeComplete() {
        _shouldNavigateToWelcome.value = false
    }
}
