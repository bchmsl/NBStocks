package com.nbstocks.nbstocks.common.extensions

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.nbstocks.nbstocks.R

fun View.makeSnackbar(s: String, isError: Boolean) {
    if (isError) {
        Snackbar.make(this, s, Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show()
    } else {
        Snackbar.make(this, s, Snackbar.LENGTH_SHORT).setBackgroundTint(Color.GREEN).show()
    }
}
