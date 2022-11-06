package com.nbstocks.nbstocks.presentation.ui.profile

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.nbstocks.nbstocks.App
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.readPreference
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.savePreference
import com.nbstocks.nbstocks.data.repositories.change_password.ChangePasswordRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uploadProfilePicResponse = MutableStateFlow<Resource<Unit>>(Resource.Loading(true))
    val uploadProfilePicResponse get() = _uploadProfilePicResponse.asStateFlow()

    private val _profilePic = MutableStateFlow<Resource<Bitmap>>(Resource.Loading(true))
    val profilePic get() = _profilePic.asStateFlow()


    fun setShownBalance(
        context: Context,
        isShown: Boolean,
    ) {
        viewModelScope.launch {
            context.savePreference(isShown)
            context.readPreference(true).let {
                Log.wtf("TAG_BALANCE", it.toString())
            }

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