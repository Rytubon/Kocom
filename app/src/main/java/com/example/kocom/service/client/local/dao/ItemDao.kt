package com.example.kocom.service.client.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kocom.models.Item


@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): MutableList<Item>

    @Query("DELETE FROM items WHERE `index` = :index")
    suspend fun deleteById(index: Int)

    @Insert
    fun insertItems(items: MutableList<Item>)

    @Query("SELECT * FROM items WHERE `index` = :index")
    suspend fun getItemByIndex(index: Int): List<Item>
}