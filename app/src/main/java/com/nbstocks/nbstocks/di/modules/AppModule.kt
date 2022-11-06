package com.nbstocks.nbstocks.di.modules

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.constants.ApiServiceHelpers
import com.nbstocks.nbstocks.common.constants.ModuleParams
import com.nbstocks.nbstocks.data.local.database.StockDatabase
import com.nbstocks.nbstocks.data.remote.services.CompanyListingsService
import com.nbstocks.nbstocks.data.remote.services.CurrentStockPriceService
import com.nbstocks.nbstocks.data.remote.services.IntervalStockPricesService
import com.nbstocks.nbstocks.data.remote.services.WatchlistStockInfoService
import com.nbstocks.nbstocks.data.repositories.change_password.ChangePasswordRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_add_user.DbAddUserRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.login.LoginRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.password_recovery.ResetPasswordRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.registration.RegisterRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.change_password.ChangePasswordRepository
import com.nbstocks.nbstocks.domain.repositories.db_add_user.DbAddUserRepository
import com.nbstocks.nbstocks.domain.repositories.login.LoginRepository
import com.nbstocks.nbstocks.domain.repositories.password_recovery.ResetPasswordRepository
import com.nbstocks.nbstocks.domain.repositories.registration.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named(ModuleParams.TWELVE_DATA)
    fun provideTwelveDataRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(CompanyListingsService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCompanyListingsApi(
        @Named(ModuleParams.TWELVE_DATA) retrofit: Retrofit
    ): CompanyListingsService =
        retrofit.create(CompanyListingsService::class.java)


    @Provides
    @Singleton
    @Named(ModuleParams.YAHOO_FINANCE)
    fun provideYahooFinanceRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiServiceHelpers.YahooFinanceService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStockApi(
        @Named(ModuleParams.YAHOO_FINANCE) retrofit: Retrofit
    ): CurrentStockPriceService =
        retrofit.create(CurrentStockPriceService::class.java)

    @Provides
    @Singleton
    fun provideIntervalStockApi(
        @Named(ModuleParams.YAHOO_FINANCE) retrofit: Retrofit
    ): IntervalStockPricesService =
        retrofit.create(IntervalStockPricesService::class.java)

    @Provides
    @Singleton
    fun provideWatchlistStockApi(
        @Named(ModuleParams.YAHOO_FINANCE) retrofit: Retrofit
    ): WatchlistStockInfoService =
        retrofit.create(WatchlistStockInfoService::class.java)


    @Provides
    @Singleton
    fun providesStockDatabase(
        @ApplicationContext
        context: Context
    ) =
        Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            ModuleParams.STOCK_DB
        ).build()


    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth
    ): LoginRepository = LoginRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideChangePasswordRepository(
        auth: FirebaseAuth
    ): ChangePasswordRepository = ChangePasswordRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideRegisterRepository(
        auth: FirebaseAuth,
        repository: DbAddUserRepositoryImpl
    ): RegisterRepository = RegisterRepositoryImpl(auth, repository)

    @Provides
    @Singleton
    fun provideResetPasswordRepository(
        auth: FirebaseAuth
    ): ResetPasswordRepository = ResetPasswordRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideAddUserDbRepository(
        db: FirebaseDatabase
    ): DbAddUserRepository = DbAddUserRepositoryImpl(db)
}