package com.nbstocks.nbstocks.common.extensions

import com.google.firebase.database.*

fun DatabaseReference.addOnDataChangedListener(block: (DataSnapshot) -> Unit) {
    addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            block(snapshot)
        }

        override fun onCancelled(error: DatabaseError) {}
    })
}