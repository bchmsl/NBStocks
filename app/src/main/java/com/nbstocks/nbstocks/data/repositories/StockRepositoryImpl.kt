package com.nbstocks.nbstocks.data.repositories

import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.common.mapper.toCompanyListingDomainModel
import com.nbstocks.nbstocks.common.mapper.toCompanyListingEntity
import com.nbstocks.nbstocks.data.local.database.StockDatabase
import com.nbstocks.nbstocks.data.remote.services.StockApi
import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import com.nbstocks.nbstocks.domain.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    db: StockDatabase,
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String?
    ): Flow<Resource<List<CompanyListingDomainModel>>> = flow {
        emit(Resource.Loading(true))

        val localListings = loadFromCache(query)
        emit(Resource.Success(localListings))

        val isDbEmpty = localListings.isEmpty() && query.isNullOrBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

        if (shouldJustLoadFromCache) {
            emit(Resource.Loading(false))
            return@flow
        }

        val remoteListingsResource = loadFromRemote()
        if (remoteListingsResource is Resource.Success) {
            dao.clearCompanyListings()
            dao.insertCompanyListings(remoteListingsResource.data.map { it.toCompanyListingEntity() })
        }
        emit(remoteListingsResource)
        emit(Resource.Loading(false))
    }

    private suspend fun loadFromCache(query: String?): List<CompanyListingDomainModel> {
        return dao.searchCompanyListing(query ?: "").map { it.toCompanyListingDomainModel() }
    }

    private suspend fun loadFromRemote(): Resource<List<CompanyListingDomainModel>> {
        return try {
            val response = api.getListings()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.data!!.map { it!!.toCompanyListingDomainModel() })
            } else {
                Resource.Error(Throwable("Couldn't load data."))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}