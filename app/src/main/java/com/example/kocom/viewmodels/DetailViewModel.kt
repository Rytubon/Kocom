package com.example.kocom.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.kocom.models.Item
import com.example.kocom.service.repositories.UserRepository
import com.example.kocom.utils.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    val onItem = MutableLiveData<Item>()
    val onDeleteSuccess = MutableLiveData<Boolean>()

    fun getItemByIndex(index: Int) {
        job = scope.launch {
            onShowLoading.postValue(true)
            when (val response = userRepository.getItemByIndex(index)) {
                is com.example.kocom.models.Result.Failure -> {
                    onShowLoading.postValue(false)
                    onErrorResponse.postValue(response.exception.message)
                }

                is com.example.kocom.models.Result.Success -> {
                    response.data?.let {
                        onShowLoading.postValue(false)
                        onItem.postValue(it)
                    }
                }
            }
        }
    }

    fun deleteItem() {
        job = scope.launch {
            onShowLoading.postValue(true)
            val index = onItem.value?.index
            index?.let {
                when (val response = userRepository.deleteByIndex(index)) {
                    is com.example.kocom.models.Result.Failure -> {
                        onShowLoading.postValue(false)
                        onErrorResponse.postValue(response.exception.message)
                    }

                    is com.example.kocom.models.Result.Success -> {
                        response.data?.let {
                            onShowLoading.postValue(false)
                            onDeleteSuccess.postValue(it)
                        }
                    }
                }
            }
        }
    }
}