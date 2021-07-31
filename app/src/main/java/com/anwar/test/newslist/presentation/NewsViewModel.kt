package com.anwar.test.newslist.presentation

import com.anwar.test.base.BaseViewModel
import com.anwar.test.detail.presentation.DetailViewState
import com.anwar.test.newslist.data.News
import com.anwar.test.newslist.data.NewsRepo
import com.anwar.test.newslist.domain.NewsSourceFactory
import com.anwar.test.utils.commons.CoroutineDispatcher
import com.anwar.test.utils.network.ResultType
import androidx.lifecycle.*
import androidx.paging.*
import com.anwar.test.detail.data.CommentItem
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    private val sourceFactory: NewsSourceFactory,
    private val dispatcher: CoroutineDispatcher = CoroutineDispatcher()
) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
        const val YOUTUBE_APP_URI = "vnd.youtube:"
        const val YOUTUBE_WEB_URI = "http://www.youtube.com/watch?v="
    }

    private var news: News? = null
    private val searchTextLiveData: MutableLiveData<String> = MutableLiveData("")
    var movies: LiveData<PagingData<News>> = MediatorLiveData()

    init {
      movies =  Transformations.switchMap(searchTextLiveData) { input: String ->
            return@switchMap getMoviesStream(input)
        }
    }

    private fun getMoviesStream(input: String): LiveData<PagingData<News>> {
        val result = viewModelScope.async {
            Pager(PagingConfig(PAGE_SIZE)) {
                sourceFactory.getSource(input)
            }
        }
        return runBlocking { result.await().liveData.cachedIn(viewModelScope)}
    }

    fun getLikeState(movieId: Int) {
        viewModelScope.launch(dispatcher.IO) {
            val likeState = newsRepo.isMovieLiked(movieId)
            withContext(dispatcher.Main) { updateViewState(DetailViewState.LikeState(likeState)) }
        }
    }

    fun fetchComments(commentIdList: ArrayList<String>) {
        viewModelScope.launch(dispatcher.IO) {
            val comments: MutableList<CommentItem> = mutableListOf()
            commentIdList.forEach { commentId ->
                val item = newsRepo.getNews(commentId)
                withContext(dispatcher.Main) {
                    if (item is ResultType.Success) {
                        comments.add(item.data.run {
                            CommentItem(id.toString(), "", "", "", title ?: text ?: "", "", 0, "")
                        })
                        updateViewState(DetailViewState.TrailersFetchedSuccess(comments))
                    } else {
                        updateViewState(DetailViewState.TrailersFetchedError)
                    }
                }
            }

        }
    }

    fun updateLikeStatus(news: News) {
        viewModelScope.launch(dispatcher.IO) {
            val newLikeState = newsRepo.isMovieLiked(news.id).not()
            newsRepo.changeLikeState(news, newLikeState)
            withContext(dispatcher.Main) {
                updateViewState(DetailViewState.LikeState(newLikeState))
            }
        }
    }

    fun setSelectedMovie(news: News) {
        this.news = news
    }

    fun getSelectedMovie(): News? {
        return news
    }

    fun getSearchLiveData(): MutableLiveData<String> {
        return searchTextLiveData
    }

}