package com.joaoricardi.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joaoricardi.weatherapp.R
import com.joaoricardi.weatherapp.models.NewsModel
import kotlinx.android.synthetic.main.news_list_item.view.*

class NewsRecyclerAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>(){

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
        holder.itemView.setOnClickListener {
            onClickListener.onClick(news)
        }
        holder.bind(news)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(newsModel: NewsModel){
            println(newsModel.urlToImage)
            Glide.with(itemView.context).load(newsModel.urlToImage).into(itemView.imageViewId)
            itemView.tituloValueId.text = newsModel.title
            itemView.nomeDoAutorId.text = newsModel.author
        }
    }


    class OnClickListener(val clickListener: (newsModel: NewsModel) -> Unit){
        fun onClick(newsModel: NewsModel) = clickListener(newsModel)
    }
}
