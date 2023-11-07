package com.sarkar.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(editShopItem: ShopItem) {
        shopListRepository.editShopItem(editShopItem)
    }

}