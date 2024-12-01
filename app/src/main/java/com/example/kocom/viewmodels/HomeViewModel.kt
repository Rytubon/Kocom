package com.example.kocom.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.kocom.models.Item
import com.example.kocom.models.Result
import com.example.kocom.models.SortType
import com.example.kocom.service.repositories.UserRepository
import com.example.kocom.utils.BaseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class HomeViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val onUser = MutableLiveData<MutableList<Item>>()
    val onSortValue = MutableLiveData<SortType>()

    init {
        onSortValue.postValue(SortType.SortIndex())
    }

    fun getItems() {
        onShowLoading.postValue(true)
        println("asgkk : ${onSortValue.value?.name}")
        val sortType = onSortValue.value ?: SortType.SortIndex()
        job = scope.launch {
            when (val response = userRepository.getItems()) {
                is Result.Failure -> {
                    onErrorResponse.postValue(response.exception.message)
                    onShowLoading.postValue(false)
                }

                is Result.Success -> {
                    var items = response.data ?: mutableListOf()
                    when (sortType) {
                        is SortType.SortDate -> items.sortByDescending {
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                            dateFormat.parse(it.date)
                        }

                        is SortType.SortIndex -> items.sortByDescending {
                            it.index
                        }

                        is SortType.SortTitle -> items.sortByDescending {
                            it.title
                        }
                    }
                    onUser.postValue(items.map { it.copy() }.toMutableList())
                    onShowLoading.postValue(false)
                }
            }
        }
    }

    fun sortItem(sortType: SortType) {
        onSortValue.value = sortType
        getItems()
    }
}