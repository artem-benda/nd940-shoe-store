package com.udacity.shoestore.screen.shoedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.R
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screen.SharedViewModel

class ShoeDetailViewModel(private val sharedViewModel: SharedViewModel) : ViewModel() {
    val fieldShoeName = MutableLiveData<String>("")
    val fieldCompany = MutableLiveData<String>("")
    val fieldShoeSize = MutableLiveData<String>("")
    val fieldDescription = MutableLiveData<String>("")

    private val _shouldNavigateToList = MutableLiveData<Boolean>(false)
    val shouldNavigateToList: LiveData<Boolean>
        get() = _shouldNavigateToList

    fun onNavigateToListComplete() {
        _shouldNavigateToList.value = false
    }

    fun onSaveButtonClicked() {
        val shoeName = fieldShoeName.value
        val company = fieldCompany.value
        val shoeSize = fieldShoeSize.value?.toDoubleOrNull() ?: 0.0
        val description = fieldDescription.value
        if (
            !shoeName.isNullOrBlank() &&
            !company.isNullOrBlank() &&
            shoeSize > 0 &&
            !description.isNullOrBlank()
        ) {
            val shoe = Shoe(shoeName, shoeSize, company, description)
            sharedViewModel.addShoe(shoe)
            _shouldNavigateToList.value = true
        } else {
            sharedViewModel.showError(R.string.please_fill_all_values)
        }
    }

    fun onCancelButtonClicked() {
        _shouldNavigateToList.value = true
    }
}
