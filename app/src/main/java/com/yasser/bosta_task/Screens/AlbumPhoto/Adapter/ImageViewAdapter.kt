package com.yasser.bosta_task.Screens.AlbumPhoto.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.yasser.bosta_task.Model.PhotoModel
import com.yasser.bosta_task.R
import com.yasser.bosta_task.Utils.BaseDiffUtil
import com.yasser.bosta_task.Utils.ViewUtils.Companion.loadImage
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.bosta_task.databinding.PhotoItemBinding


class ImageViewAdapter(
    val context: Context,
    var photoList: MutableList<PhotoModel>,
    val onItemClicked: ItemClickListener<PhotoModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PhotoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.photo_item,parent,false))



    override fun getItemCount(): Int =photoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoHolder).bind(context,photoList[holder.bindingAdapterPosition],onItemClicked)
    }


    fun updateList(newPhotoList: List<PhotoModel>) {
        val placeInfoBaseDiffUtil: BaseDiffUtil =
            BaseDiffUtil(context, this.photoList, newPhotoList)
        val diffResult = DiffUtil.calculateDiff(placeInfoBaseDiffUtil)
        this.photoList.clear()
        this.photoList.addAll(newPhotoList)
        diffResult.dispatchUpdatesTo(this)
    }

//////////////////////////////////////////////////////////////////////////////////
    class PhotoHolder(var photoBinding: PhotoItemBinding) : RecyclerView.ViewHolder(photoBinding.root) {
        fun bind(context: Context,photoModel: PhotoModel, onItemClicked: ItemClickListener<PhotoModel>) {

            photoBinding.progress.show()
            photoBinding.image.loadImage(photoModel.url,null,photoBinding.progress)
            photoBinding.container
                .setOnClickListener { onItemClicked.onItemClick(photoBinding.root,photoModel) }

        }
    }


}