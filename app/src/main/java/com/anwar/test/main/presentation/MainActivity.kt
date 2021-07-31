package com.anwar.test.main.presentation

import com.anwar.test.R
import com.anwar.test.base.BaseActivity
import com.anwar.test.newslist.presentation.NewsViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity<NewsViewModel>(), HasSupportFragmentInjector {

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): NewsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(NewsViewModel::class.java)
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController(R.id.nav_movies_host_fragment)
        navController.setGraph(R.navigation.news_graph, intent.extras)
        setupActionBarWithNavController(navController)
    }

    override fun onBackPressed() {
        val findNavController = NavHostFragment.findNavController(nav_movies_host_fragment)
        when (findNavController.currentDestination?.id) {
            R.id.details -> findNavController.popBackStack()
            R.id.movies -> finish()
            else -> super.onBackPressed()
        }
    }

}

