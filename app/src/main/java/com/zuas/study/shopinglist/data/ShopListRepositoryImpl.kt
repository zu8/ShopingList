package com.zuas.study.shopinglist.data

import androidx.lifecycle.LiveData
import com.zuas.study.shopinglist.data.database.ShoppingListDao
import com.zuas.study.shopinglist.data.datasource.LocalDataSource
import com.zuas.study.shopinglist.domain.ShopItem
import com.zuas.study.shopinglist.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val localSource: LocalDataSource
) : ShopListRepository {
    override fun getShopList(): LiveData<List<ShopItem>> {
        return localSource.getShopList()
    }

    override suspend fun addShopItem(item: ShopItem) {
        localSource.insertShopItem(item)
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        localSource.deleteShopItem(item)
    }

    override suspend fun editShopItem(item: ShopItem) {
        localSource.insertShopItem(item)
    }

    override suspend fun getShopItem(id: Int): ShopItem? {
        return localSource.getShopItem(id)
    }
}