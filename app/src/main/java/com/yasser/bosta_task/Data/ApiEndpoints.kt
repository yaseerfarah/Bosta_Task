package com.yasser.bosta_task.Data

import com.yasser.bosta_task.Model.AlbumModel
import com.yasser.bosta_task.Model.PhotoModel
import com.yasser.bosta_task.Model.UserModel
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {


    @GET("/users")
    fun getUsers(): Single<Response<List<UserModel>>>

    @GET("/albums")
    fun getAlbumsByUserId(@Query("userId")  userId:String): Single<Response<List<AlbumModel>>>


    @GET("/photos")
    fun getPhotosByAlbumId(@Query("albumId")  albumId:String): Single<Response<List<PhotoModel>>>



}