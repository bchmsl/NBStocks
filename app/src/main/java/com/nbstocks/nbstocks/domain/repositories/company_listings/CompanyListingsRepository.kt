package com.nbstocks.nbstocks.domain.repositories.company_listings

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import kotlinx.coroutines.flow.Flow

interface CompanyListingsRepository {
    suspend fun getCompanyListings(
        fetchFromRemote:Boolean,
        query:String?
    ): Flow<Resource<List<CompanyListingDomainModel>>>
}