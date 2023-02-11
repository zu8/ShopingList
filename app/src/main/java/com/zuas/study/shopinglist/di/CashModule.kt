package com.zuas.study.shopinglist.di

import android.app.Application
import com.zuas.study.shopinglist.data.database.AppDatabase
import com.zuas.study.shopinglist.data.database.ShoppingListDao
import dagger.Module
import dagger.Provides

@Module
class CashModule {

    @Provides
    @ApplicationScope
    fun provideShoppingListDao(application: Application): ShoppingListDao{
        return AppDatabase.getInstance(application).shoppingListDao()
    }
}