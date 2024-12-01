package com.example.kocom.models


sealed class SortType(var name: String) {
    class SortIndex : SortType("Index")
    class SortTitle : SortType("Title")
    class SortDate : SortType("Date")
}