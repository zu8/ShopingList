package com.zuas.study.shopinglist.domain

interface ShopListRepository {

    fun getShopList(): List<ShopItem>

    suspend  fun addShopItem(item : ShopItem)

    suspend fun deleteShopItem(item: ShopItem)

    suspend fun editShopItem( item: ShopItem)

    fun getShopItem(id: Int): ShopItem

}