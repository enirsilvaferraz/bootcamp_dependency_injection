package com.ferraz.bootcamp.di

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CardViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    val userLiveData = MutableLiveData<UserState>()

    fun onSelect(uuid: String?) = viewModelScope.launch {

        userLiveData.value = UserState.Loading

        if (uuid == null) {

            userLiveData.value = UserState.Failure(R.string.failure_empty_uuid)

        } else {

            userLiveData.value = try {
                UserState.Success(getUserUseCase.getUserData(uuid))
            } catch (e: Exception) {
                UserState.Failure(R.string.failure_user_not_found)
            }
        }
    }
}

sealed interface UserState {
    class Success(val user: User?) : UserState
    class Failure(@StringRes val message: Int) : UserState
    object Loading : UserState
}
