package com.sarkar.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getSopItem(shopItemId: Int): ShopItem {
        return shopListRepository.getSopItem(shopItemId)
    }

}