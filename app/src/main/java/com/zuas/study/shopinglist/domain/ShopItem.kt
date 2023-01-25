package com.zuas.study.shopinglist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list")
data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}