package com.nbstocks.nbstocks.common.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

fun DatabaseReference.addOnDataChangedListener(block: (DataSnapshot) -> Unit) {
    addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            block(snapshot)
        }
        override fun onCancelled(error: DatabaseError) {}
    })
}