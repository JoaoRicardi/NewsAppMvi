package com.joaoricardi.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.joaoricardi.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnId.setOnClickListener {
            viewModel.getNews()
        }

        viewModel.state.observeForever {state ->
            when(state){
                is MainViewModel.ScreenState.Loading -> {
                    textViewStatusId.text  = "Loading ..."
                }

                is MainViewModel.ScreenState.Error -> {
                    textViewStatusId.text  = "Error ${state.error}"
                }

                is MainViewModel.ScreenState.Loaded -> {
                    textViewStatusId.text  = "Temos ${state.value} noticias hoje"
                }
            }
        }


    }
}
