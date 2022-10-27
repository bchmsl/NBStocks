package com.nbstocks.nbstocks.data.repositories.company_listings

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.local.database.StockDatabase
import com.nbstocks.nbstocks.data.mapper.toCompanyListingDomainModel
import com.nbstocks.nbstocks.data.mapper.toCompanyListingEntity
import com.nbstocks.nbstocks.data.remote.services.CompanyListingsService
import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import com.nbstocks.nbstocks.domain.repositories.company_listings.CompanyListingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingsRepositoryImpl @Inject constructor(
    private val api: CompanyListingsService,
    db: StockDatabase,
) : CompanyListingsRepository {

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