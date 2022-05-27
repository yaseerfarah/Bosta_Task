package com.yasser.bosta_task.Screens.Splash

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yasser.bosta_task.Model.UserModel
import com.yasser.bosta_task.R
import com.yasser.bosta_task.Utils.ApiStatus
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.bosta_task.Utils.Interfaces.NetworkStatus
import com.yasser.bosta_task.Utils.ViewUtils
import com.yasser.bosta_task.Utils.ViewUtils.Companion.hide
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.bosta_task.ViewModels.BostaViewModel
import com.yasser.bosta_task.ViewModels.ViewModelFactory
import com.yasser.bosta_task.databinding.FragmentSplashBinding
import eg.com.invive.barberia_ktx.BroadcastReceiver.NetworkReceiver
import javax.inject.Inject

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash),NetworkStatus {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var bostaViewModel: BostaViewModel
    lateinit var networkReceiver: NetworkReceiver

     var userObserver:Observer<ApiStatus<UserModel>> = Observer {
         when(it){
             is ApiStatus.Success->{
                 navigate(R.id.action_splashFragment_to_profileFragment)
             }
             is ApiStatus.Loading->{
                 mBinding?.progress?.show()
             }
             is ApiStatus.Failed->{
                 ViewUtils.showSnackBar(requireActivity(),"Something went wrong, try again later",true,true)
             }
         }
     }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkReceiver = NetworkReceiver(this)
        bostaViewModel= ViewModelProvider(this, viewModelFactory).get(BostaViewModel::class.java)
        //bostaViewModel.getUser()

    }


    override fun onStart() {
        super.onStart()
        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        activity!!.registerReceiver(networkReceiver, netFilter)
        bostaViewModel.getUserLivedata().observe(this,userObserver)
    }

    override fun onStop() {
        super.onStop()
        activity!!.unregisterReceiver(networkReceiver)
        bostaViewModel.getUserLivedata().removeObserver(userObserver)
    }

    override fun connect() {
        mBinding!!.noInternetLayout.hide()
        bostaViewModel.getUser()
    }

    override fun notConnect() {
        mBinding!!.noInternetLayout.show()
    }
}