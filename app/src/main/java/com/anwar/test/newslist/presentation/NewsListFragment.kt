package com.anwar.test.newslist.presentation

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.anwar.test.R
import com.anwar.test.base.BaseFragment
import com.anwar.test.newslist.data.News
import com.anwar.test.utils.commons.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class NewsListFragment : BaseFragment<NewsViewModel>(), NewsAdapter.OnItemClick, (CombinedLoadStates) -> Unit, SearchView.OnQueryTextListener {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mGridLayoutManager: Provider<GridLayoutManager>

    @Inject
    lateinit var mGridSpacingItemDecoration: GridSpacingItemDecoration

    @Inject
    lateinit var mNewsAdapter: NewsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_news_list
    override fun getLifeCycleOwner(): LifecycleOwner = this

    override val viewModel by lazy {
        ViewModelProvider(requireActivity(), mViewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        mGridLayoutManager.get()?.let {
            it.reverseLayout = false
            it.isItemPrefetchEnabled = false
            moviesRecycler.layoutManager = it
        }
        moviesRecycler.apply {
            setHasFixedSize(true)
            addItemDecoration(mGridSpacingItemDecoration)
            itemAnimator = DefaultItemAnimator()
            mNewsAdapter.setListener(this@NewsListFragment)
            adapter = mNewsAdapter.withLoadStateFooter(
                    footer = NewsStateAdapter { mNewsAdapter.retry() }
            )
        }
        listenForAdapterStates()
    }

    private fun listenForAdapterStates() {
        viewModel.movies.observe(viewLifecycleOwner, { paging ->
            lifecycleScope.launch { mNewsAdapter.submitData(paging) }
        })
        btn_retry.setOnClickListener { mNewsAdapter.retry() }
        mNewsAdapter.addLoadStateListener(this)
    }

    override fun onItemClicked(newsEntity: News) {
        viewModel.setSelectedMovie(newsEntity)
        activity?.let { findNavController().navigate(R.id.details, Bundle()) }
    }

    override fun invoke(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Loading) {
            btn_retry.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
        } else {
            loadingView.visibility = View.GONE
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> {
                    btn_retry.visibility = View.VISIBLE
                    loadState.refresh as LoadState.Error
                }
                else -> null
            }
            errorState?.error?.localizedMessage?.let { showMessage(it) }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { viewModel.getSearchLiveData().postValue(it) }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
}

