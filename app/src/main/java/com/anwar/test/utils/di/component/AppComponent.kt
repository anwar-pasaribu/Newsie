package com.anwar.test.utils.di.component

import com.anwar.test.utils.database.DbModule
import com.anwar.test.utils.di.module.AppModule
import com.anwar.test.utils.di.module.RepoModule
import com.anwar.test.utils.network.UrlModule
import com.anwar.test.utils.network.NetworkModule
import com.anwar.test.utils.di.builder.ActivityBuilder
import android.app.Application
import com.anwar.test.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (DbModule::class),
    (NetworkModule::class), (UrlModule::class),(RepoModule::class), (ActivityBuilder::class)])

interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: MyApp)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}