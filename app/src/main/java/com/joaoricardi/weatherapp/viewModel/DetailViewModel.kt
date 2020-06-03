package com.joaoricardi.weatherapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaoricardi.weatherapp.models.NewsModel

class DetailViewModel(val newDetail: NewsModel?, val app : Application) : ViewModel(){
    private var _state = MutableLiveData<ScreenState>()
    val state: LiveData<ScreenState>
        get() = _state

    init {
        if(newDetail == null){
            _state.postValue(ScreenState.Error("Erro ao pegar detalhes"))
        }
        else {
            _state.postValue(ScreenState.ShowDetail(newDetail))
        }
    }

    sealed class ScreenState {

        data class ShowDetail(val value:NewsModel): ScreenState()
        data class Error(val error: String): ScreenState()
    }
}