package com.example.testvrg.screen;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testvrg.data.repository.Repository
import com.example.testvrg.model.JsonReddit
import kotlinx.coroutines.launch
import retrofit2.http.POST

class ScreenFragmentViewModel : ViewModel() {
    private var repo = Repository()
    val allApi: MutableLiveData<JsonReddit> = MutableLiveData()


    fun getDataInfo(after: String?) {
        viewModelScope.launch {
            val response = repo.getD(after)
            allApi.postValue(response)
            }
    }

}
