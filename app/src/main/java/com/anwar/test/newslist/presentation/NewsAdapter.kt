package com.anwar.test.newslist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anwar.test.R
import com.anwar.test.newslist.data.News


class NewsAdapter : PagingDataAdapter<News, RecyclerView.ViewHolder>(NewsModelComparator) {

    private lateinit var listener: OnItemClick

    fun setListener(mOnItemClick: OnItemClick) {
        listener = mOnItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_view, parent, false)
        return MovieViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MovieViewHolder
        val news = getItem(position)
        viewHolder.tv_news_title.text = news?.title
        viewHolder.tv_news_comment_count.text = news?.kids?.size.toString()
        viewHolder.tv_news_score.text = news?.score.toString()

        viewHolder.vg_news_container.setOnClickListener {
            listener.onItemClicked(news ?: return@setOnClickListener)
        }
    }

    inner class MovieViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vg_news_container: ViewGroup = itemView.findViewById(R.id.vg_news_container) as ViewGroup
        var tv_news_title: TextView = itemView.findViewById(R.id.tv_news_title) as TextView
        var tv_news_comment_count: TextView = itemView.findViewById(R.id.tv_news_comment_count) as TextView
        var tv_news_score: TextView = itemView.findViewById(R.id.tv_news_score) as TextView
    }

    interface OnItemClick {
        fun onItemClicked(newsEntity: News)
    }

    companion object {
        private val NewsModelComparator = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }


}
