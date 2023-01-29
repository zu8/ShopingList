package com.zuas.study.shopinglist.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zuas.study.shopinglist.domain.ShopItem

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM shop_list")
    fun getShopList(): LiveData<List<ShopItem>>

    @Query("SELECT * FROM shop_list WHERE id == :itemId LIMIT 1")
    suspend fun getShopItem(itemId: Int): ShopItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(item: ShopItem)

    @Delete
    suspend fun deleteShopItem(item: ShopItem)
}