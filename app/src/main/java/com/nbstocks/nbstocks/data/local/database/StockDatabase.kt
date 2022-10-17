package com.nbstocks.nbstocks.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nbstocks.nbstocks.data.local.dao.StockDao
import com.nbstocks.nbstocks.data.local.model.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase(){

    abstract val dao: StockDao

}