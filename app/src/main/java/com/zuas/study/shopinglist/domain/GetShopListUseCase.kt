package com.zuas.study.shopinglist.domain

class GetShopListUseCase(
    private val repository: ShopListRepository
) {

    fun getShopList(): List<ShopItem> {
        return repository.getShopList()
    }
}