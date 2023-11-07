package com.sarkar.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sarkar.shoppinglist.domain.ShopItem
import com.sarkar.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private val shopList =
        sortedSetOf<ShopItem>(Comparator<ShopItem> { p0, p1 -> p0.id.compareTo(p1.id) })
    private var autoIncrementId: Int = 0

    init {
        for (i in 0 until 1000) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItemUseCase(item)
        }
    }

    override fun addShopItemUseCase(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(deleteShopItem: ShopItem) {
        shopList.remove(deleteShopItem)
        updateList()
    }

    override fun editShopItem(editShopItem: ShopItem) {
        val oldElement = getSopItem(editShopItem.id)
        shopList.remove(oldElement)
        addShopItemUseCase(editShopItem)
    }

    override fun getSopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element with id $shopItemId not found!")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateList() {
        shopListLiveData.value = shopList.toList()
    }


}