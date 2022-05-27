package com.yasser.bosta_task

import android.app.Activity
import android.app.Application
import com.yasser.nagwa.Dagger.Component.DaggerAppComponent

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AppController : Application(), HasAndroidInjector {

    // public static Locale localeLanguage;
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)?.appContext(applicationContext)?.build()?.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }


}