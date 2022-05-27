package com.yasser.bosta_task.Utils

sealed  class ApiStatus<out T> {

    data class Success<T>(val info:T): ApiStatus<T>()
    class Loading<T>(): ApiStatus<T>()
    class Failed<T>(val message:String): ApiStatus<T>()

}