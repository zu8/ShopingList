package com.zuas.study.shopinglist.domain

class EditShopItemUseCase(
    private val repository: ShopListRepository
) {

    suspend operator fun invoke(item: ShopItem) {
        repository.editShopItem(item)
    }
}