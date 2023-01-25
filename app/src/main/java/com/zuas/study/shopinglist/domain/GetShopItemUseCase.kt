package com.zuas.study.shopinglist.domain

class GetShopItemUseCase(
    private val repository: ShopListRepository
) {

     operator fun invoke(id: Int): ShopItem? {

        return repository.getShopItem(id)
    }
}