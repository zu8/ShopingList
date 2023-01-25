package com.zuas.study.shopinglist.domain

class AddShopItemUseCase(
    private val repository: ShopListRepository
) {

    fun addShopItem(item: ShopItem) {
        repository.addShopItem(item)
    }
}