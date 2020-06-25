package com.joaoricardi.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaoricardi.weatherapp.adapter.NewsRecyclerAdapter
import com.joaoricardi.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val actor = MainViewModel.MainActor(viewModel::takeIntention)

        val newsAdapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener{
            actor.navegateToDetail(it)
        })

        val manager = LinearLayoutManager(this)

        btnNextPage.setOnClickListener {actor.navegateToNextPage() }

        with(newsRecyclerId){
            layoutManager = manager
            adapter = newsAdapter
        }

        viewModel.state.observeForever {state ->
            when(state){
                is MainViewModel.ScreenState.Loading -> {
                    progressId.visibility = View.VISIBLE
                    errorLayoutId.visibility = View.GONE
                    newsRecyclerId.visibility  = View.GONE

                }

                is MainViewModel.ScreenState.Error -> {
                    progressId.visibility = View.GONE
                    newsRecyclerId.visibility  = View.GONE
                    errorLayoutId.visibility = View.VISIBLE
                }

                is MainViewModel.ScreenState.Loaded -> {
                    progressId.visibility = View.GONE
                    errorLayoutId.visibility = View.GONE
                    newsRecyclerId.visibility  = View.VISIBLE
                    newsAdapter.newList = state.value
                }
            }
        }

        viewModel.events.observeForever{sideEffect ->
            when(sideEffect){
                is MainViewModel.SideEffect.NavigateToNewsDetail ->{
                    Intent(this, DetailActivity::class.java).apply {
                        putExtra(NEWS, sideEffect.newsModel)
                        startActivity(this)
                    }
                    viewModel.clearewsDetailNavigate()
                }
                is MainViewModel.SideEffect.ClearDetails ->{
                    // Fazer nada...kkkk
                }
            }
        }

        actor.loadInitialData()
    }

    companion object{
        val NEWS = "NEWS"
    }
}
