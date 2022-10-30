package com.nbstocks.nbstocks.common.extensions

import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern

fun EditText.isValid(isEmail: Boolean = false, isPassword: Boolean = false): Boolean{
    val input = this.text.toString()
    if (input.isEmpty()) {
        this.error = "Field is empty!"
        return false
    }
    if (isEmail){
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            this.error = "Enter valid email!"
            return false
        }
    }
    if (isPassword){
        if (input.length < 8){
            this.error = "Password should contain at least 8 symbols!"
            return false
        }
    }
    this.error = null
    return true
}