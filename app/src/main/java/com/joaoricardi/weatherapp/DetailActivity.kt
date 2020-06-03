package com.joaoricardi.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.joaoricardi.weatherapp.models.NewsModel
import com.joaoricardi.weatherapp.viewModel.DetailViewModel
import com.joaoricardi.weatherapp.viewModel.DetailViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.news_list_item.view.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val newsModel = intent.getParcelableExtra(MainActivity.NEWS) as? NewsModel



        val viewModelFacttory = DetailViewModelFactory(newsModel!!,application)

        detailViewModel = ViewModelProviders.of(this,viewModelFacttory).get(DetailViewModel::class.java)

        detailViewModel.state.observeForever {state ->
            when(state) {
                is DetailViewModel.ScreenState.ShowDetail -> {
                    supportActionBar?.let {

                        title = state.value.author
                    }
                    Glide.with(this).load(state.value.urlToImage).into(imageView)
                    autorId.text = state.value.description
                    titleId.text = state.value.title
                }

                is DetailViewModel.ScreenState.Error -> {
                    Toast.makeText(this,"Erro ao exibir detalhes", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
