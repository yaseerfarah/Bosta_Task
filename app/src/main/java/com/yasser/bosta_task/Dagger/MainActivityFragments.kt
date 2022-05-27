package com.yasser.bosta_task.Dagger

import com.yasser.bosta_task.Screens.AlbumPhoto.AlbumPhotosFragment
import com.yasser.bosta_task.Screens.Profile.ProfileFragment
import com.yasser.bosta_task.Screens.Splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityFragments {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment


    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment


    @ContributesAndroidInjector
    abstract fun contributeAlbumPhotosFragment(): AlbumPhotosFragment


}