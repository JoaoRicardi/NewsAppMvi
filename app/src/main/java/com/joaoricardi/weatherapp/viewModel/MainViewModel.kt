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

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state

    private val _navigateToSelect = MutableLiveData<NewsModel>()
    val navigateToSelect: LiveData<NewsModel>
        get() = _navigateToSelect

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>
        get() = _currentPage


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    companion object{
        val TOKEN = "baeb3bac75ce4332a9374e06f7d24f42"
    }

    init {
        _currentPage.value = 1
        getNews()
    }

     fun getNews() {
        _state.postValue(ScreenState.Loading)
        coroutineScope.launch {
            val newsList = RetrofitService().getNewsApiService().getNews(
                _currentPage.value ?: 1,
                TOKEN,
                "us"
            )

            if(newsList.isSuccessful){
                newsList.body()?.let {
                    _state.postValue(ScreenState.Loaded(it.articles))
                }
            }
            else{
                _state.postValue(ScreenState.Error( "Erro"))
            }

//            try{
//                val responseDef = deferedNews.await()
//                _state.postValue(ScreenState.Loaded(responseDef.articles))
//
//            }catch (e: Exception){
//            }
//                _state.postValue(ScreenState.Error(e.message ?: "Erro"))
        }
    }

    fun showNewsDetail(newsModel: NewsModel){
        _navigateToSelect.value = newsModel
    }

    fun clearewsDetailNavigate(){
        _navigateToSelect.value = null
    }

    fun getNextPage(){
        _currentPage.postValue(_currentPage.value?.plus(1))
        getNews()
    }

    sealed class ScreenState {
        object Loading: ScreenState()
        data class Loaded(val value:List<NewsModel>): ScreenState()
        data class Error(val error: String): ScreenState()
    }
}