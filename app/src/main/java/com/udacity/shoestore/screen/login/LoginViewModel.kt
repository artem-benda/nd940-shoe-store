package com.udacity.shoestore.screen.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.screen.SharedViewModel

class LoginViewModel(private val sharedViewModel: SharedViewModel) : ViewModel() {
    val fieldLogin = MutableLiveData<String>("")
    val fieldPassword = MutableLiveData<String>("")

    fun onButtonClicked() {
        val login = fieldLogin.value
        val password = fieldPassword.value
        if (
            !login.isNullOrBlank() &&
            !password.isNullOrBlank()
        ) {
            sharedViewModel.login(login, password)
        } else {
            sharedViewModel.showError(R.string.please_fill_all_values)
        }
    }
}
