package com.nbstocks.nbstocks.common.extensions.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackbar(s: String){
    Snackbar.make(this, s, Snackbar.LENGTH_SHORT).show()
}
