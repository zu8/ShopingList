package com.zuas.study.shopinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(
    private val repository: ShopListRepository
) {

    suspend operator fun invoke(item: ShopItem) {
        repository.editShopItem(item)
    }
}