package com.nbstocks.nbstocks.presentation.ui.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.readPreference
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.savePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uploadProfilePicResponse = MutableStateFlow<Resource<Unit>>(Resource.Loading(true))
    val uploadProfilePicResponse get() = _uploadProfilePicResponse.asStateFlow()

    private val _profilePic = MutableStateFlow<Resource<Bitmap?>>(Resource.Loading(true))
    val profilePic get() = _profilePic.asStateFlow()


    fun setShownBalance(
        context: Context,
        isShown: Boolean,
    ) {
        viewModelScope.launch {
            context.savePreference(isShown)
        }
    }

    fun uploadProfilePic(imageUri: Uri?) {
        uploadProfilePicOnStore(imageUri).addOnSuccessListener {
            _uploadProfilePicResponse.tryEmit(Resource.Success(Unit))
        }.addOnFailureListener {
            _uploadProfilePicResponse.tryEmit(Resource.Error(it))
        }
    }

    private fun uploadProfilePicOnStore(imageUri: Uri?) =
        FirebaseStorage.getInstance().getReference("Users/${auth.currentUser?.uid}")
            .putFile(imageUri!!)

    fun getUserImage(){
        val storeReference = FirebaseStorage.getInstance().getReference("Users/${auth.currentUser?.uid}")
        val tempFile = File.createTempFile("tempFile","jpg")

        storeReference.getFile(tempFile).addOnCompleteListener{
            val bitmap = BitmapFactory.decodeFile(tempFile.absolutePath)
            _profilePic.tryEmit(Resource.Success(bitmap))
        }
    }

    fun getShownBalanceState(
        context: Context
    ) = flow {
        emit(context.readPreference(true))
    }
}