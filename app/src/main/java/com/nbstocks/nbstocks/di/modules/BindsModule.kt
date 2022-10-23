package com.nbstocks.nbstocks.di.modules

import com.nbstocks.nbstocks.csv.CSVParser
import com.nbstocks.nbstocks.csv.DailyListingsParser
import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.nbstocks.nbstocks.data.repositories.company_listings.CompanyListingsRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.daily_stock.DailyStockRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.company_listings.CompanyListingsRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsRepository(
        companyListingsRepositoryImpl: CompanyListingsRepositoryImpl
    ): CompanyListingsRepository

    @Binds
    @Singleton
    abstract fun bindDailyStockRepository(
        dailyStockRepositoryImpl: DailyStockRepositoryImpl
    ): DailyStockRepository

    @Binds
    @Singleton
    abstract fun bindDailyListingsParser(
        dailyListingsParser: DailyListingsParser
    ): CSVParser<DailyStockDto>

}