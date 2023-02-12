package com.zuas.study.shopinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val repository: ShopListRepository
) {

    operator fun invoke(): LiveData<List<ShopItem>> {
        return repository.getShopList()
    }
}