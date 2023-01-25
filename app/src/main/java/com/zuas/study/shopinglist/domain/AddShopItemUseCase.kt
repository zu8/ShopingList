package com.zuas.study.shopinglist.domain

class AddShopItemUseCase(
    private val repository: ShopListRepository
) {

    suspend operator fun invoke(item: ShopItem) {
        repository.addShopItem(item)
    }
}