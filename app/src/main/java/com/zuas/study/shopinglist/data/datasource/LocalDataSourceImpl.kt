package com.zuas.study.shopinglist.data.datasource

import androidx.lifecycle.LiveData
import com.zuas.study.shopinglist.data.database.ShoppingListDao
import com.zuas.study.shopinglist.domain.ShopItem
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: ShoppingListDao
): LocalDataSource {
    override fun getShopList(): LiveData<List<ShopItem>> {
        return dao.getShopList()
    }

    override suspend fun getShopItem(itemId: Int): ShopItem? {
        return dao.getShopItem(itemId)
    }

    override suspend fun insertShopItem(item: ShopItem) {
        dao.insertShopItem(item)
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        dao.deleteShopItem(item)
    }
}