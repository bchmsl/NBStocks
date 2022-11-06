package com.nbstocks.nbstocks.common.extensions

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.isValid(isEmail: Boolean = false, isPassword: Boolean = false): String?{
    val input = this.editText?.text.toString()
    if (input.isEmpty()) {
        this.error = "Field is empty!"
        return null
    }
    if (isEmail){
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            this.error = "Enter valid email!"
            return null
        }
    }
    if (isPassword){
        if (input.length < 8){
            this.error = "Password should contain at least 8 symbols!"
            return null
        }
    }
    this.error = null
    return input
}