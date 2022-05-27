package com.yasser.bosta_task.Screens.AlbumPhoto

import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.yasser.bosta_task.Model.AlbumModel
import com.yasser.bosta_task.Model.PhotoModel
import com.yasser.bosta_task.R
import com.yasser.bosta_task.Screens.AlbumPhoto.Adapter.ImageViewAdapter
import com.yasser.bosta_task.Utils.ApiStatus
import com.yasser.bosta_task.Utils.BaseFragment
import com.yasser.bosta_task.Utils.Interfaces.NetworkStatus
import com.yasser.bosta_task.Utils.ViewUtils
import com.yasser.bosta_task.Utils.ViewUtils.Companion.hide
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.bosta_task.ViewModels.BostaViewModel
import com.yasser.bosta_task.ViewModels.ViewModelFactory
import com.yasser.bosta_task.databinding.FragmentAlbumPhotosBinding
import eg.com.invive.barberia_ktx.BroadcastReceiver.NetworkReceiver
import javax.inject.Inject


class AlbumPhotosFragment : BaseFragment<FragmentAlbumPhotosBinding>(R.layout.fragment_album_photos),
    NetworkStatus {



    companion object{
        const val MODEL_KEY="model"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var bostaViewModel: BostaViewModel
    lateinit var networkReceiver: NetworkReceiver

     var albumModel: AlbumModel?=null


    var photoList:MutableList<PhotoModel> = arrayListOf()
    val adapter: ImageViewAdapter by lazy {
        ImageViewAdapter(
            requireContext(),
            photoList,
            object : ItemClickListener<PhotoModel> {
                override fun onItemClick(view: View, item: PhotoModel) {

                }
            }) }



    var photoListObserver: Observer<ApiStatus<List<PhotoModel>>> = Observer {
        when(it){
            is ApiStatus.Success->{
                if (it.info.isNotEmpty()){

                    adapter.updateList(it.info)
                    photoList.clear()
                    photoList.addAll(it.info)
                    mBinding!!.statefulLayout.showContent()
                }else{
                    mBinding!!.statefulLayout.showEmpty("No Photos Found")
                }


            }
            is ApiStatus.Loading->{
                mBinding!!.statefulLayout.showLoading()
            }
            is ApiStatus.Failed->{
                mBinding!!.statefulLayout.showError("Something went wrong, try again later",{ view->

                })
                ViewUtils.showSnackBar(requireActivity(),"Something went wrong, try again later",true,true)
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bostaViewModel= ViewModelProvider(this, viewModelFactory).get(BostaViewModel::class.java)
        networkReceiver = NetworkReceiver(this)
    }


    fun initializeView(){
        mBinding!!.albumName.setText(albumModel!!.title)
        ViewUtils.initializeRecyclerView(
            mBinding!!.listRecycler,
            adapter,
            GridLayoutManager(requireContext(),3),
            null,
            null
        )

        mBinding!!.backBtn.setOnClickListener {
            navigateUp()
        }

        mBinding!!.searchField.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                bostaViewModel.photoSearchByName(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        mBinding!!.searchField.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                bostaViewModel.photoSearchByName(mBinding!!.searchField.text.toString())
                return@OnEditorActionListener true
            }
            false
        })



    }


    override fun onStart() {
        super.onStart()
        val netFilter = IntentFilter()
        netFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        activity!!.registerReceiver(networkReceiver, netFilter)
        bostaViewModel.getPhotoSearchListLivedata().observe(this,photoListObserver)
    }

    override fun onStop() {
        super.onStop()
        activity!!.unregisterReceiver(networkReceiver)
        bostaViewModel.getPhotoSearchListLivedata().removeObserver(photoListObserver)
    }

    override fun connect() {
        mBinding!!.contentLayout.show()
        mBinding!!.noInternetLayout.hide()

        if (albumModel==null){
            arguments?.let {
                if (it.containsKey(MODEL_KEY)){
                    albumModel=it.getParcelable<AlbumModel>(MODEL_KEY)!!
                    bostaViewModel.getPhotoListByAlbumId(albumModel!!.id)
                    initializeView()
                }
            }
        }else if (photoList.isEmpty()){
            bostaViewModel.getPhotoListByAlbumId(albumModel!!.id)
        }

    }

    override fun notConnect() {
        mBinding!!.noInternetLayout.show()
        mBinding!!.contentLayout.hide()
    }
}