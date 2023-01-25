package com.zuas.study.shopinglist.data

import com.zuas.study.shopinglist.data.database.ShoppingListDao
import com.zuas.study.shopinglist.domain.ShopItem
import com.zuas.study.shopinglist.domain.ShopListRepository


class ShopListRepositoryImpl(
    private val shoppingListDao: ShoppingListDao
): ShopListRepository {
    override fun getShopList(): List<ShopItem> {
        return shoppingListDao.getShopList()
    }

    override suspend fun addShopItem(item: ShopItem) {
        shoppingListDao.insertShopItem(item)
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        shoppingListDao.deleteShopItem(item)
    }

    override suspend fun editShopItem(item: ShopItem) {
       shoppingListDao.insertShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shoppingListDao.getShopItem(id)
    }
}