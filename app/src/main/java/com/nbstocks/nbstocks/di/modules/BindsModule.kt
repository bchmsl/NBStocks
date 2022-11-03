package com.nbstocks.nbstocks.di.modules

import com.nbstocks.nbstocks.data.repositories.base_repository.BaseRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.company_listings.CompanyListingsRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.current_stock.CurrentStockRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.daily_stock.DailyStockPricesPricesRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_manage_users_stock.DbManageUsersStockRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_get_balance.GetBalanceRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_get_stock_amount.GetStockAmountRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.watchlist_stock.WatchlistStockRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import com.nbstocks.nbstocks.domain.repositories.company_listings.CompanyListingsRepository
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.DbManageUsersStockRepository
import com.nbstocks.nbstocks.domain.repositories.get_balance.GetBalanceRepository
import com.nbstocks.nbstocks.domain.repositories.get_stock_amount.GetStockAmountRepository
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
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
        dailyStockPricesRepositoryImpl: DailyStockPricesPricesRepositoryImpl
    ): DailyStockPricesRepository

    @Binds
    @Singleton
    abstract fun bindCurrentStockRepository(
        currentStockRepositoryImpl: CurrentStockRepositoryImpl
    ): CurrentStockRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistStockRepository(
        watchlistStockRepositoryImpl: WatchlistStockRepositoryImpl
    ): WatchlistStockRepository

    @Binds
    @Singleton
    abstract fun bindGetBalanceRepository(
        getBalanceRepositoryImpl: GetBalanceRepositoryImpl
    ): GetBalanceRepository

    @Binds
    @Singleton
    abstract fun bindUsersStockRepository(
        dbAddUsersStockRepositoryImpl: DbManageUsersStockRepositoryImpl
    ): DbManageUsersStockRepository

    @Binds
    @Singleton
    abstract fun bindGetStockAmountRepository(
        getStockAmountRepositoryImpl: GetStockAmountRepositoryImpl
    ): GetStockAmountRepository

    @Binds
    @Singleton
    abstract fun bindBaseRepository(
        baseRepositoryImpl: BaseRepositoryImpl
    ): BaseRepository

}