package com.sarkar.shoppinglist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopItem(deleteShopItem: ShopItem) {
        shopListRepository.deleteShopItem(deleteShopItem)
    }

}