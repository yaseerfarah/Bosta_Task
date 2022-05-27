package com.yasser.nagwa.Dagger.Component

import android.app.Application
import android.content.Context
import com.yasser.bosta_task.AppController
import com.yasser.bosta_task.Dagger.ViewModelModule
import com.yasser.nagwa.Dagger.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ ActivityModule::class, ViewModelModule::class,AndroidSupportInjectionModule::class, RepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder?

        @BindsInstance
        fun appContext(context: Context): Builder?

        fun build(): AppComponent?
    }


    fun inject(baseApplication: AppController)

}