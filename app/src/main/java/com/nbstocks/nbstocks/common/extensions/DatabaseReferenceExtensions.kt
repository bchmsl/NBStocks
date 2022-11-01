package com.nbstocks.nbstocks.common.extensions

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

fun DatabaseReference.onChildAddedListener(
    block: (snapshot: DataSnapshot, previousChildName: String?) -> Unit,
    changedBlock: ((snapshot: DataSnapshot, previousChildName: String?) -> Unit)? = null
    ){
    addChildEventListener(object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            block(snapshot, previousChildName)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            changedBlock?.invoke(snapshot, previousChildName)
        }
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    })
}