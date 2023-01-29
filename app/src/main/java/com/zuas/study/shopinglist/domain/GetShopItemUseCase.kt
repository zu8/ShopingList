package com.zuas.study.shopinglist.domain

class GetShopItemUseCase(
    private val repository: ShopListRepository
) {

     suspend operator fun invoke(id: Int): ShopItem? {

        return repository.getShopItem(id)
    }
}