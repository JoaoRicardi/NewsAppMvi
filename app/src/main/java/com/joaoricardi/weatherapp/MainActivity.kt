package com.joaoricardi.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaoricardi.weatherapp.adapter.NewsRecyclerAdapter
import com.joaoricardi.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = LinearLayoutManager(this)
        val newsAdapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener{
            viewModel.showNewsDetail(it)
        })

        btnNextPage.setOnClickListener {
            viewModel.getNews()
        }

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



        viewModel.navigateToSelect.observe(this, Observer {
            it?.let {
                Intent(this, DetailActivity::class.java).apply {
                    putExtra(NEWS, it)
                    startActivity(this)
                }
                viewModel.clearewsDetailNavigate()
            }
        })
    }

    companion object{
        val NEWS = "NEWS"
    }
}
