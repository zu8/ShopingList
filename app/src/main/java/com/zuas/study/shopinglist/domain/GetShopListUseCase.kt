package com.zuas.study.shopinglist.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase(
    private val repository: ShopListRepository
) {

    operator fun invoke(): LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}