package com.yasser.bosta_task.Utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection

open class BaseFragment<T:ViewDataBinding>(private val contentLayoutId:Int):Fragment() {


    var mBinding:T?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= DataBindingUtil.inflate(
            inflater, contentLayoutId, container, false)
        return mBinding!!.root;
    }

    val navController:NavController
    get() {return findNavController()}

    fun navigate(action: Int, bundle: Bundle? = null) {
        findNavController().navigate(action, bundle)
    }

    fun navigate(direction: NavDirections, args: Bundle? = null) {
        findNavController().navigate(direction.actionId, args)
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }


}