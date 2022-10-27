package com.nbstocks.nbstocks.di.modules

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.constants.ModuleParams
import com.nbstocks.nbstocks.common.handlers.ResponseHandler
import com.nbstocks.nbstocks.data.local.database.StockDatabase
import com.nbstocks.nbstocks.data.remote.services.CompanyListingsService
import com.nbstocks.nbstocks.data.remote.services.StockPricesService
import com.nbstocks.nbstocks.data.repositories.db_add_user.DbAddUserRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.login.LoginRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.registration.RegisterRepositoryImpl

import com.nbstocks.nbstocks.domain.repositories.db_add_user.DbAddUserRepository
import com.nbstocks.nbstocks.domain.repositories.login.LoginRepository
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
    @Named(ModuleParams.STOCK_API)
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(CompanyListingsService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    @Provides
    @Singleton
    @Named(ModuleParams.QUOTE)
    fun provideRetrofitDailyStock(): Retrofit =
        Retrofit.Builder()
            .baseUrl(StockPricesService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStockDailyApi(
        @Named(ModuleParams.QUOTE)
        retrofit: Retrofit): StockPricesService =
        retrofit.create(StockPricesService::class.java)


    @Provides
    @Singleton
    fun provideStockApi(
        @Named(ModuleParams.STOCK_API)
        retrofit: Retrofit): CompanyListingsService =
        retrofit.create(CompanyListingsService::class.java)

    @Provides
    @Singleton
    fun providesStockDatabase(
        @ApplicationContext
        context: Context) =
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
    fun provideRegisterRepository(
        auth: FirebaseAuth,
        handler: ResponseHandler,
        repository: DbAddUserRepositoryImpl
    ): RegisterRepository = RegisterRepositoryImpl(auth, repository)

    @Provides
    @Singleton
    fun provideAddUserDbRepository(
        handler: ResponseHandler,
        db: FirebaseDatabase
    ): DbAddUserRepository = DbAddUserRepositoryImpl(db)
}