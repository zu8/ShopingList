package com.zuas.study.shopinglist.data.datasource

import androidx.lifecycle.LiveData
import com.zuas.study.shopinglist.domain.ShopItem

interface LocalDataSource {

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun getShopItem(itemId: Int): ShopItem?

    suspend fun insertShopItem(item: ShopItem)

    suspend fun deleteShopItem(item: ShopItem)
}