package com.udacity.shoestore.models

data class AuthData(
    val username: String = ""
) {
    val isLoggedIn: Boolean = username.isNotEmpty()
}
