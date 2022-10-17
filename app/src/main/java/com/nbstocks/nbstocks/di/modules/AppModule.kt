package com.nbstocks.nbstocks.di.modules

import android.content.Context
import androidx.room.Room
import com.nbstocks.nbstocks.data.local.database.StockDatabase
import com.nbstocks.nbstocks.data.remote.services.StockApi
import com.nbstocks.nbstocks.data.repositories.StockRepositoryImpl

import com.nbstocks.nbstocks.domain.repositories.StockRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule{
    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

}