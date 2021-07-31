package com.anwar.test.detail.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anwar.test.R
import com.anwar.test.base.BaseFragment
import com.anwar.test.detail.data.CommentItem
import com.anwar.test.newslist.presentation.NewsViewModel
import com.anwar.test.newslist.presentation.NewsViewModel.Companion.YOUTUBE_APP_URI
import com.anwar.test.newslist.presentation.NewsViewModel.Companion.YOUTUBE_WEB_URI
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject


class DetailFragment : BaseFragment<NewsViewModel>(), CommentAdapter.CommentAdapterListener {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var commentAdapter: CommentAdapter

    override fun getLayoutId(): Int = R.layout.fragment_detail
    override fun getLifeCycleOwner(): LifecycleOwner = this

    override val viewModel by lazy {
        ViewModelProvider(requireActivity(), mViewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commentAdapter.setListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    fun initUI() {
        recycler_trailer.setHasFixedSize(true)
        recycler_trailer.layoutManager = mLinearLayoutManager
        recycler_trailer.itemAnimator = DefaultItemAnimator()
        recycler_trailer.adapter = commentAdapter
        renderMovieDetails()
    }

    private fun renderMovieDetails() {
        viewModel.getSelectedMovie()?.apply {
            tv_title.text = title
            tv_release_date.text = String.format(getString(R.string.lbl_score), score)
            tv_votes_count.text = String.format(getString(R.string.lbl_comment_count), kids.size.toString())
            img_like.setOnClickListener { viewModel.updateLikeStatus(this) }
            viewModel.getLikeState(id)
            viewModel.fetchComments(kids)
        }
    }

    override fun renderViewState(data: Any) {
        when (data) {
            is DetailViewState.MessageRes -> showMessage(getString(data.resId))
            is DetailViewState.LikeState -> renderLikeState(data.isLiked)
            is DetailViewState.TrailersFetchedSuccess -> renderTrailers(data.trailers)
            is DetailViewState.TrailersFetchedError -> renderFetchingTrailerError()
        }
    }

    private fun renderFetchingTrailerError() {
        trailers_loading.visibility = View.GONE
        showMessage(getString(R.string.fetch_trailers_error))
    }

    private fun renderTrailers(trailers: List<CommentItem>) {
        trailers_loading.visibility = View.GONE
        commentAdapter.addItems(trailers)
    }

    private fun renderLikeState(isLiked: Boolean) {
        if (isLiked) R.string.msg_news_liked else R.string.msg_news_disliked
        img_like.setImageResource(if (isLiked) R.drawable.like else R.drawable.dislike)
    }


    override fun onItemClicked(commentItem: CommentItem) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_APP_URI${commentItem.key}"))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_WEB_URI${commentItem.key}"))
            startActivity(intent)
        }
    }


}