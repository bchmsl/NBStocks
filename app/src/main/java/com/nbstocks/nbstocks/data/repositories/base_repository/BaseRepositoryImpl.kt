package com.nbstocks.nbstocks.data.repositories.base_repository

import android.util.Log
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import retrofit2.Response
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(): BaseRepository {

    override suspend fun <T, M> handleResponse(
        request: suspend () -> Response<T>,
        map: T.() -> M
    ): Resource<M> {
        return try {
            val response = request.invoke()
                val body = response.body()
                if (response.isSuccessful) {
                    Resource.Success(body!!.map())
                } else {
                    Log.e("TAG: handleResponse1", response.code().toString())
                    Resource.Error(Throwable(response.errorBody().toString()))
                }
        } catch (e: Throwable) {
            Log.e("TAG: handleResponse2", e.toString())
            Resource.Error(e)
        }
    }

}