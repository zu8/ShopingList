package com.zuas.study.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zuas.study.shopinglist.data.ShopListRepositoryImpl
import com.zuas.study.shopinglist.data.database.AppDatabase
import com.zuas.study.shopinglist.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).shoppingListDao()
    private val repository = ShopListRepositoryImpl(dao)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase()
    private val _item = MutableLiveData<ShopItem>()
    val item: LiveData<ShopItem>
        get() = _item

    fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteShopItemUseCase(item)
        }
    }

    fun changeEnableState(item: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = item.copy(enabled = !item.enabled)
            editShopItemUseCase(newItem)
        }
    }

}