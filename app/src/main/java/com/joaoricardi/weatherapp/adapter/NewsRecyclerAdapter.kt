package com.joaoricardi.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joaoricardi.weatherapp.R
import com.joaoricardi.weatherapp.models.NewsModel
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsRecyclerAdapter : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>(){

    var newList = listOf<NewsModel>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.news_list_item,parent, false)

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int  = newList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newList[position]
        holder.bind(news)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){


        fun bind(newsModel: NewsModel){

            itemView.tituloValueId.text = newsModel.title
            itemView.nomeDoAutorId.text = newsModel.author
        }

    }


}