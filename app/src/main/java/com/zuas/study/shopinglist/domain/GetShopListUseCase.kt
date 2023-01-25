package com.zuas.study.shopinglist.domain

class GetShopListUseCase(
    private val repository: ShopListRepository
) {

    operator fun invoke(): List<ShopItem> {
        return repository.getShopList()
    }
}