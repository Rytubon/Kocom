package com.example.kocom.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey var index: Int,
    var title: String,
    var date: String,
    var description: String
)
