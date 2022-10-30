package com.nbstocks.nbstocks.presentation.ui.sign_up.model

data class User(
    val uid: String? = "",
    val email: String? = "",
    val password: String? = "",
    val balance: Int? = 10000
)
