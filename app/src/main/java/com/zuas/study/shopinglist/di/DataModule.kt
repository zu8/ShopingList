package com.zuas.study.shopinglist.di

import com.zuas.study.shopinglist.data.ShopListRepositoryImpl
import com.zuas.study.shopinglist.data.datasource.LocalDataSource
import com.zuas.study.shopinglist.data.datasource.LocalDataSourceImpl
import com.zuas.study.shopinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: ShopListRepositoryImpl):ShopListRepository

    @Binds
    fun bindDataLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}