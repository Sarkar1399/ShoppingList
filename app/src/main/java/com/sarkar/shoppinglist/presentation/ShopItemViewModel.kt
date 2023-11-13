package com.sarkar.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarkar.shoppinglist.data.ShopListRepositoryImpl
import com.sarkar.shoppinglist.domain.AddShopItemUseCase
import com.sarkar.shoppinglist.domain.EditShopItemUseCase
import com.sarkar.shoppinglist.domain.GetShopItemUseCase
import com.sarkar.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val _shouldCloseScreen: MutableLiveData<Unit> = MutableLiveData()
    val shouldCloseScreen: LiveData<Unit> get() = _shouldCloseScreen


    private val _errorInputName: MutableLiveData<Boolean> = MutableLiveData()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount: MutableLiveData<Boolean> = MutableLiveData()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private val _shopItemFromId: MutableLiveData<ShopItem> = MutableLiveData()
    val shopItemFromId: LiveData<ShopItem> get() = _shopItemFromId

    fun getShopItemFromId(shopItemId: Int) {
        _shopItemFromId.value = getShopItemUseCase.getSopItem(shopItemId)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            addShopItemUseCase.addShopItemUseCase(shopItem)
            finishWork()
        }

    }

    fun changeShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItemFromId.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String = inputName?.trim() ?: ""

    private fun parseCount(inputCount: String?): Int {
        val count: Int = try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
        return count
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