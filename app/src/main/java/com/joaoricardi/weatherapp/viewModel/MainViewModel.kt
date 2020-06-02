package com.joaoricardi.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaoricardi.weatherapp.models.NewsModel
import com.joaoricardi.weatherapp.service.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel(){

    companion object{
        val TOKEN = "baeb3bac75ce4332a9374e06f7d24f42"
    }

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    fun getNews(){
        _state.postValue(ScreenState.Loading)
        coroutineScope.launch {
            val deferedNews = RetrofitService().getNewsApiService().getNews(TOKEN,"us")
            try{
                val responseDef = deferedNews.await()
                _state.postValue(ScreenState.Loaded(responseDef.articles))

            }catch (e: Exception){
                _state.postValue(ScreenState.Error(e.message ?: "Erro"))
            }
        }
    }

    sealed class ScreenState {
        object Loading: ScreenState()
        data class Loaded(val value:List<NewsModel>): ScreenState()
        data class Error(val error: String): ScreenState()
    }
}