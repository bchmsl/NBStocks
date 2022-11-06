package com.nbstocks.nbstocks.common.extensions

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(s: String, isError: Boolean) {
    if (isError) {
        Snackbar.make(this, s, Snackbar.LENGTH_SHORT).setBackgroundTint(Color.parseColor("#E82F46")).show()
    } else {
        Snackbar.make(this, s, Snackbar.LENGTH_SHORT).setBackgroundTint(Color.parseColor("#63C10A")).show()
    }
}

fun View.disable(){
    this.isEnabled = false
}
fun View.enable(){
    this.isEnabled = true
}