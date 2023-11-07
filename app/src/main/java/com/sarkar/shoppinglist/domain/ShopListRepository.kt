package com.sarkar.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addShopItemUseCase(shopItem: ShopItem)

    fun deleteShopItem(deleteShopItem: ShopItem)

    fun editShopItem(editShopItem: ShopItem)

    fun getSopItem(shopItemId: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

}
