package com.zuas.study.shopinglist.domain

class DeleteShopItemUseCase(
    private val repository: ShopListRepository
) {

    suspend operator fun invoke(item: ShopItem) {
        repository.deleteShopItem(item)
    }
}