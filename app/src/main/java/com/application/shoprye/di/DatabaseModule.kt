package com.application.shoprye.di

import android.content.Context
import com.application.shoprye.dao.RyeJobDao
import com.application.shoprye.dao.ShoppingCartDao
import com.application.shoprye.data.RyeJobDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): RyeJobDatabase {
        return RyeJobDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideRyeJobDao(appDatabase: RyeJobDatabase): RyeJobDao {
        return appDatabase.ryeJobDao()
    }

    @Singleton
    @Provides
    fun provideShoppingCartDao(appDatabase: RyeJobDatabase): ShoppingCartDao {
        return appDatabase.shoppingCartDao()
    }
}