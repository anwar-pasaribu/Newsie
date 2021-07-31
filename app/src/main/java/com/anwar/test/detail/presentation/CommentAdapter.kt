package com.anwar.test.detail.presentation

import android.os.Build
import android.text.Html
import com.anwar.test.detail.data.CommentItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anwar.test.R


class CommentAdapter(var commentItemList: MutableList<CommentItem>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private lateinit var mListener: CommentAdapterListener

    override fun getItemCount(): Int {
        return if (commentItemList.size > 0) commentItemList.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_view, parent, false)
        return CommentViewHolder(view)
    }

    fun addItems(mList: List<CommentItem>) {
        commentItemList.addAll(mList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        commentItemList.clear()
    }


    interface CommentAdapterListener {
        fun onItemClicked(commentItem: CommentItem)
    }

    inner class CommentViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTrailerName: TextView = itemView.findViewById(R.id.tv_comment) as TextView

    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val trailer = commentItemList[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvTrailerName.text = Html.fromHtml(trailer.name, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.tvTrailerName.text = Html.fromHtml(trailer.name)
        }
    }

    fun setListener(listener: CommentAdapterListener) {
        mListener = listener
    }


}
