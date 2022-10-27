package com.nbstocks.nbstocks.common.handlers

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

interface ResponseHandler {
    fun <T> handleException(e: Throwable) : Resource<T> {
        return when(e) {
            is FirebaseAuthInvalidUserException -> Resource.Error(Throwable("This e-mail is not found"))
            is FirebaseAuthUserCollisionException -> Resource.Error(Throwable("This e-mail already used"))
            is FirebaseAuthWeakPasswordException -> Resource.Error(Throwable("Enter valid password (At least 6 letters)"))
            is FirebaseAuthInvalidCredentialsException -> Resource.Error(Throwable("Enter valid e-mail"))
            is FirebaseNetworkException -> Resource.Error(Throwable("Check your internet connection"))
            is IllegalArgumentException -> Resource.Error(Throwable("Enter valid values"))
            is TimeoutException -> Resource.Error(Throwable("Check your internet connection"))
            is UnknownHostException -> Resource.Error(Throwable("Check your internet connection"))
            else -> Resource.Error(Throwable(e.message))
        }
    }

    fun <T> handleSuccess(data: T) : Resource<T> {
        return Resource.Success(data)
    }
}