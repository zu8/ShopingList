package com.zuas.study.shopinglist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(
    private val repository: ShopListRepository
) {

     suspend operator fun invoke(id: Int): ShopItem? {

        return repository.getShopItem(id)
    }
}