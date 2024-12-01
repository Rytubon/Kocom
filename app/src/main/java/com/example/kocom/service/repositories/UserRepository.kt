package com.example.kocom.service.repositories

import com.example.kocom.models.Item
import com.example.kocom.models.Result
import com.example.kocom.service.client.local.dao.AppDatabase
import com.example.kocom.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


interface UserRepository {
    suspend fun deleteByIndex(
        index: Int
    ): Result<Boolean>

    suspend fun getItemByIndex(
        index: Int
    ): Result<Item>

    suspend fun getItems(
    ): Result<MutableList<Item>>
}

class UserRepositoryImpl(
    private val appDatabase: AppDatabase
) : UserRepository {
    override suspend fun getItems(): Result<MutableList<Item>> {
        val items = appDatabase.itemDao().getAllItems()
        if (items.isEmpty()) {
            val gson = Gson()
            val itemType = object : TypeToken<List<Item>>() {}.type
            val defaultItems = gson.fromJson<List<Item>>(Constants.items, itemType)
            appDatabase.itemDao().insertItems(defaultItems.toMutableList())
        }
        return Result.Success(appDatabase.itemDao().getAllItems())
    }

    override suspend fun deleteByIndex(index: Int): Result<Boolean> {
        appDatabase.itemDao().deleteById(index)
        return Result.Success(true)
    }

    override suspend fun getItemByIndex(index: Int): Result<Item> {
        val items = appDatabase.itemDao().getItemByIndex(index)
        return if (items.isNotEmpty()) {
            val item = items.first()
            Result.Success(item)
        } else Result.Failure(Exception("Item not found"))
    }

}