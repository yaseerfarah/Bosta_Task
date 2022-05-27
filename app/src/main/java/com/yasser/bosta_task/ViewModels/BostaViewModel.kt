package com.yasser.bosta_task.ViewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.yasser.bosta_task.Data.ApiEndpoints
import com.yasser.bosta_task.Model.PhotoModel
import com.yasser.bosta_task.Model.UserModel
import com.yasser.bosta_task.Utils.ApiStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

class BostaViewModel@Inject constructor(context: Context, private val apiEndpoints: ApiEndpoints): ViewModel() {

    private var userLiveData: MediatorLiveData<ApiStatus<UserModel>> = MediatorLiveData()
    private var photoSearchListLiveData: MediatorLiveData<ApiStatus<List<PhotoModel>>> = MediatorLiveData()
    private var allPhotoList:MutableList<PhotoModel> = arrayListOf()
    private var disposables: CompositeDisposable= CompositeDisposable()

    private val  userInputSubject: BehaviorSubject<String> = BehaviorSubject.create<String>()

    fun getUserLivedata():LiveData<ApiStatus<UserModel>> = userLiveData
    fun getPhotoSearchListLivedata():LiveData<ApiStatus<List<PhotoModel>>> = photoSearchListLiveData


    init {

        disposables.add(
        userInputSubject
            .debounce(1, TimeUnit.SECONDS)
            .map {
                if (allPhotoList.isNotEmpty()&&it.isNotEmpty()){
                    return@map allPhotoList.filter { s->s.title.contains(it) }
                }else{
                    return@map allPhotoList
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                photoSearchListLiveData.postValue(ApiStatus.Success(it))
            }, {
                photoSearchListLiveData.postValue(ApiStatus.Failed(it.message.toString()))
            })
        )

    }



    fun photoSearchByName(name:String){
        userInputSubject.onNext(name)
    }





    fun getUser(){
        userLiveData.postValue(ApiStatus.Loading())
        disposables.add(
            apiEndpoints.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       if (it.isSuccessful&&it.body()!=null){
                           var userModel= it.body()!![Random.nextInt(0,it.body()!!.size)]
                           getAlbumsByUserId(userModel)
                       }else{
                           userLiveData.postValue(ApiStatus.Failed("Something went wrong"))
                       }
            },{
                userLiveData.postValue(ApiStatus.Failed(it.message.toString()))
            }))
    }


    private fun getAlbumsByUserId(userModel: UserModel){
        disposables.add(
            apiEndpoints.getAlbumsByUserId(userModel.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if (it.isSuccessful&&it.body()!=null){

                        userModel.albumList=it.body()!!.toMutableList()
                        userLiveData.postValue(ApiStatus.Success(userModel))

                    }else{

                        userLiveData.postValue(ApiStatus.Failed("Something went wrong"))

                    }
                },{
                    userLiveData.postValue(ApiStatus.Failed(it.message.toString()))
                })

        )
    }



    fun getPhotoListByAlbumId(albumId:String){
        photoSearchListLiveData.postValue(ApiStatus.Loading())
        allPhotoList.clear()
        disposables.add(
            apiEndpoints.getPhotosByAlbumId(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if (it.isSuccessful&&it.body()!=null){
                        allPhotoList.addAll(it.body()!!)
                        photoSearchListLiveData.postValue(ApiStatus.Success(allPhotoList))
                    }else{

                        photoSearchListLiveData.postValue(ApiStatus.Failed("Something went wrong"))

                    }
                },{
                    photoSearchListLiveData.postValue(ApiStatus.Failed(it.message.toString()))
                })

        )
    }





}