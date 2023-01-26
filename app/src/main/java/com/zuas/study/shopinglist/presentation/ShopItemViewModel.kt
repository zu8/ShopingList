package com.zuas.study.shopinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zuas.study.shopinglist.data.ShopListRepositoryImpl
import com.zuas.study.shopinglist.data.database.AppDatabase
import com.zuas.study.shopinglist.domain.AddShopItemUseCase
import com.zuas.study.shopinglist.domain.EditShopItemUseCase
import com.zuas.study.shopinglist.domain.GetShopItemUseCase
import com.zuas.study.shopinglist.domain.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).shoppingListDao()
    private val repository = ShopListRepositoryImpl(dao)

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

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
        viewModelScope.launch(Dispatchers.IO) {
            if (fieldsValid) {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase(shopItem)
                finishWorkFromSideThread()
            }
        }
    }


    fun getShopItem(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tempItem = getShopItemUseCase(itemId)
            tempItem?.let {
                _item.postValue(it)
            }
        }
    }

    fun editShopItem(itemId: Int, inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch(Dispatchers.IO) {
                _item.value?.let {
                    val newItem = it.copy(name = name, count = count)
                    editShopItemUseCase(newItem)
                    _item.postValue(newItem)
                    finishWorkFromSideThread()
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

    private fun finishWorkFromSideThread() {
        _shouldCloseScreen.postValue(Unit)
    }

}