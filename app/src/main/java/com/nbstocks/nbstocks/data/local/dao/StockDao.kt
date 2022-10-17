package com.nbstocks.nbstocks.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nbstocks.nbstocks.data.local.model.CompanyListingEntity


@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
        SELECT * 
        FROM companylistingentity 
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' 
        OR UPPER(:query) == symbol
        OR LOWER(type) LIKE '%' || LOWER(:query) || '%' 
        

    """
    )
    suspend fun searchCompanyListing(
        query: String
    ): List<CompanyListingEntity>

}