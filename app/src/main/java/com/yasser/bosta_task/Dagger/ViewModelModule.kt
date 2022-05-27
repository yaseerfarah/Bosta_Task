package com.yasser.bosta_task.Dagger

import android.content.Context
import androidx.lifecycle.ViewModel
import com.yasser.bosta_task.Data.ApiEndpoints
import com.yasser.bosta_task.ViewModels.BostaViewModel
import com.yasser.bosta_task.ViewModels.ViewModelFactory
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class ViewModelModule {
    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
    )
    @Retention(
        RetentionPolicy.RUNTIME
    )
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)


    @Singleton
    @Provides
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory(providerMap)
    }


    @Provides
    @Singleton
    @IntoMap
    @ViewModelKey(BostaViewModel::class)
    fun bostaViewModel(context: Context, apiEndpoints: ApiEndpoints): ViewModel {
        return BostaViewModel(context, apiEndpoints)
    }

}