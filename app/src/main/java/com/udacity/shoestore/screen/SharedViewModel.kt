package com.udacity.shoestore.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.models.AuthData
import com.udacity.shoestore.models.Shoe

class SharedViewModel : ViewModel() {
    private val _authData = MutableLiveData<AuthData>(AuthData())
    val authData: LiveData<AuthData>
        get() = _authData

    private val _errorTextResource = MutableLiveData<Int?>()
    val errorTextResource: LiveData<Int?>
        get() = _errorTextResource

    fun showError(errorTextResource: Int) {
        _errorTextResource.value = errorTextResource
    }

    fun showErrorComplete() {
        _errorTextResource.value = null
    }

    private val _shoeList = MutableLiveData<List<Shoe>>(emptyList())
    val shoeList: LiveData<List<Shoe>>
        get() = _shoeList

    fun addShoe(shoe: Shoe) {
        _shoeList.value = (shoeList.value ?: emptyList()) + shoe
    }

    fun login(email: String, password: String) {
        if (email.isNotEmpty()) {
            _authData.value = AuthData(email)
        } else {
            showError(R.string.error_email_empty)
        }
    }

    fun logout() {
        _authData.value = AuthData("")
    }
}
