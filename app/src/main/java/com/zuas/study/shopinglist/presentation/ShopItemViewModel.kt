package com.zuas.study.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.*
import com.zuas.study.shopinglist.data.ShopListRepositoryImpl
import com.zuas.study.shopinglist.data.database.AppDatabase
import com.zuas.study.shopinglist.domain.AddShopItemUseCase
import com.zuas.study.shopinglist.domain.EditShopItemUseCase
import com.zuas.study.shopinglist.domain.GetShopItemUseCase
import com.zuas.study.shopinglist.domain.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val addShopItemUseCase : AddShopItemUseCase,
    private val editShopItemUseCase : EditShopItemUseCase,
    private val getShopItemUseCase : GetShopItemUseCase,
) : ViewModel() {

    private val _item = MutableLiveData<ShopItem>()
    val item: LiveData<ShopItem>
        get() = _item

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        viewModelScope.launch {
            if (fieldsValid) {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase(shopItem)
                finishWork()
            }
        }
    }


    fun getShopItem(itemId: Int) {
        viewModelScope.launch {
            val tempItem = getShopItemUseCase(itemId)
            tempItem?.let {
                _item.value = it
            }
        }
    }

    fun editShopItem( inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                _item.value?.let {
                    val newItem = it.copy(name = name, count = count)
                    editShopItemUseCase(newItem)
                    _item.value = newItem
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            return 0;
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false

        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}