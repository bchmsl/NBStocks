package com.nbstocks.nbstocks.domain.repositories.base_repository

import com.nbstocks.nbstocks.common.handlers.Resource
import retrofit2.Response

interface BaseRepository {
    suspend fun <T, M> safeApiCall(
        request: suspend () -> Response<T>,
        map: T.() -> M
    ): Resource<M>
}