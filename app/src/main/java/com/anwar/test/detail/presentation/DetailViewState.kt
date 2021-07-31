package com.anwar.test.detail.presentation

import com.anwar.test.detail.data.CommentItem

sealed class DetailViewState {
    object TrailersFetchedError : DetailViewState()
    class TrailersFetchedSuccess(val trailers:  List<CommentItem>) : DetailViewState()
    class LikeState(val isLiked: Boolean) : DetailViewState()
    class MessageRes(val resId: Int) : DetailViewState()
}
