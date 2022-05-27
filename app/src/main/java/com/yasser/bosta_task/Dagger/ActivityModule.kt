package com.yasser.nagwa.Dagger.Component

import com.yasser.bosta_task.Dagger.MainActivityFragments
import com.yasser.bosta_task.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityFragments::class])
    abstract fun contributeMainActivity(): MainActivity

}