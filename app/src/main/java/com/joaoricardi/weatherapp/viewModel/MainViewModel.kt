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

    fun takeIntention(intention: Intention) {
        when (intention) {
          is Intention.LoadInitialData -> {
              _currentPage.value = 1
              getNews()
          }
          is Intention.NavegateToDetail -> showNewsDetail(intention.newsModel)
          is Intention.NavegateToNextPage -> getNextPage()
        }
    }

    private val _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state

    private val _events = MutableLiveData<SideEffect>()
    val events: LiveData<SideEffect>
        get() = _events

    private val _currentPage = MutableLiveData<Int>()
    val currentPage: LiveData<Int>
        get() = _currentPage

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    companion object{
        val TOKEN = "baeb3bac75ce4332a9374e06f7d24f42"
    }

    private fun getNews(){
        _state.postValue(ScreenState.Loading)
        coroutineScope.launch {
            val deferedNews = RetrofitService().getNewsApiService().getNews(
                _currentPage.value ?: 1,
                TOKEN,
                "us"
            )
            try{
                val responseDef = deferedNews.await()
                _state.postValue(ScreenState.Loaded(responseDef.articles))

            }catch (e: Exception){
                _state.postValue(ScreenState.Error(e.message ?: "Erro"))
            }
        }
    }

    fun showNewsDetail(newsModel: NewsModel){
        _events.postValue(SideEffect.NavigateToNewsDetail(newsModel))
    }

    fun clearewsDetailNavigate(){
        _events.postValue(SideEffect.ClearDetails)
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

    sealed class SideEffect {
        data class NavigateToNewsDetail(val newsModel: NewsModel): SideEffect()
        object ClearDetails: SideEffect()
    }

    sealed class Intention{
        object LoadInitialData: Intention()
        data class NavegateToDetail(val newsModel: NewsModel): Intention()
        object NavegateToNextPage: Intention()
    }

    class MainActor(private val emit: (Intention) -> Unit) {
        fun loadInitialData() = emit(Intention.LoadInitialData)
        fun navegateToDetail(newsModel: NewsModel) = emit(Intention.NavegateToDetail(newsModel))
        fun navegateToNextPage() = emit(Intention.NavegateToNextPage)
    }
}