package com.yasser.bosta_task.Screens.Profile

import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.yasser.bosta_task.Model.AlbumModel
import com.yasser.bosta_task.Model.UserModel
import com.yasser.bosta_task.R
import com.yasser.bosta_task.Screens.AlbumPhoto.AlbumPhotosFragment
import com.yasser.bosta_task.Screens.Profile.Adapter.AlbumViewAdapter
import com.yasser.bosta_task.Utils.ApiStatus
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.bosta_task.Utils.Interfaces.NetworkStatus
import com.yasser.bosta_task.Utils.ViewUtils
import com.yasser.bosta_task.Utils.ViewUtils.Companion.hide
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.bosta_task.ViewModels.BostaViewModel
import com.yasser.bosta_task.ViewModels.ViewModelFactory
import com.yasser.bosta_task.databinding.FragmentProfileBinding
import eg.com.invive.barberia_ktx.BroadcastReceiver.NetworkReceiver
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile),
    NetworkStatus {



    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var bostaViewModel: BostaViewModel
    lateinit var networkReceiver: NetworkReceiver

    var albumList:MutableList<AlbumModel> = arrayListOf()
    val adapter:AlbumViewAdapter by lazy {
        AlbumViewAdapter(
            requireContext(),
            albumList,
            object : ItemClickListener<AlbumModel>{
                override fun onItemClick(view: View, item: AlbumModel) {
                    var bundle=Bundle()
                    bundle.putParcelable(AlbumPhotosFragment.MODEL_KEY,item)
                    navigate(R.id.action_profileFragment_to_albumPhotosFragment,bundle)
                }
            }) }

    var userObserver: Observer<ApiStatus<UserModel>> = Observer {
        when(it){
            is ApiStatus.Success->{
                albumList.addAll(it.info.albumList!!)
                initializeView(it.info)
            }
            is ApiStatus.Loading->{
                ViewUtils.showSnackBar(requireActivity(),"Something went wrong",true,true)
            }
            is ApiStatus.Failed->{
                ViewUtils.showSnackBar(requireActivity(),"Something went wrong, try again later",true,true)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bostaViewModel= ViewModelProvider(this, viewModelFactory).get(BostaViewModel::class.java)
        networkReceiver = NetworkReceiver(this)
    }



    fun initializeView(userModel: UserModel){
        mBinding!!.name.setText(userModel.name)
        mBinding!!.address.setText(userModel.address.street+" ,"+userModel.address.suite+" ,"+userModel.address.city+" ,"+userModel.address.zipcode)
        ViewUtils.initializeRecyclerView(
            mBinding!!.listRecycler,
            adapter,
            LinearLayoutManager(requireContext()),
            null,
            null
        )
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
        mBinding!!.contentLayout.show()
        mBinding!!.noInternetLayout.hide()
    }

    override fun notConnect() {
        mBinding!!.noInternetLayout.show()
        mBinding!!.contentLayout.hide()
    }
}