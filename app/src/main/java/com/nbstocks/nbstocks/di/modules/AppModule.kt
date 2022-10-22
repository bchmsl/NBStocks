package com.nbstocks.nbstocks.di.modules

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.response_handler.ResponseHandler
import com.nbstocks.nbstocks.data.local.database.StockDatabase
import com.nbstocks.nbstocks.data.remote.services.StockApi
import com.nbstocks.nbstocks.data.remote.services.StockDaily
import com.nbstocks.nbstocks.data.repositories.StockRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.db_add_user.DbAddUserRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.login.LoginRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.registration.RegisterRepositoryImpl
import com.nbstocks.nbstocks.data.repositories.stock_details.StockDetailsRepositoryImpl

import com.nbstocks.nbstocks.domain.repositories.StockRepository
import com.nbstocks.nbstocks.domain.repositories.db_add_user.DbAddUserRepository
import com.nbstocks.nbstocks.domain.repositories.login.LoginRepository
import com.nbstocks.nbstocks.domain.repositories.registration.RegisterRepository
import com.nbstocks.nbstocks.domain.repositories.stock_details.StockDetailsRepository
import dagger.Binds
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
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    @Provides
    @Singleton
    @Named("Daily")
    fun provideRetrofitDailyStock(): Retrofit =
        Retrofit.Builder()
            .baseUrl(StockDaily.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideStockDailyApi(retrofit: Retrofit): StockDaily =
        retrofit.create(StockDaily::class.java)


    @Provides
    @Singleton
    fun provideStockApi(retrofit: Retrofit): StockApi =
        retrofit.create(StockApi::class.java)

    @Provides
    @Singleton
    fun providesStockDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "StockDB"
        ).build()


    @Provides
    @Singleton
    fun provideLoginRepository(auth: FirebaseAuth): LoginRepository = LoginRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideRegisterRepository(
        auth: FirebaseAuth, handler: ResponseHandler, repository: DbAddUserRepositoryImpl
    ): RegisterRepository = RegisterRepositoryImpl(auth, repository)

    @Provides
    @Singleton
    fun provideAddUserDbRepository(
        handler: ResponseHandler, db: FirebaseDatabase
    ): DbAddUserRepository = DbAddUserRepositoryImpl(db)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

    @Binds
    @Singleton
    abstract fun bindStockDetailsRepository(stockDetailsRepositoryImpl: StockDetailsRepositoryImpl): StockDetailsRepository

}