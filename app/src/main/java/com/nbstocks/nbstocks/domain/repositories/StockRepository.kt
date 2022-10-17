package com.nbstocks.nbstocks.domain.repositories

import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote:Boolean,
        query:String?
    ): Flow<Resource<List<CompanyListingDomainModel>>>
}