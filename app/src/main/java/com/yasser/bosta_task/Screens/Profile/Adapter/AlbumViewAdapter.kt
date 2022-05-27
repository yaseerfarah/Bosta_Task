package com.yasser.bosta_task.Screens.Profile.Adapter

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
import com.yasser.bosta_task.Model.AlbumModel
import com.yasser.bosta_task.Model.PhotoModel
import com.yasser.bosta_task.R
import com.yasser.bosta_task.Utils.BaseDiffUtil
import com.yasser.bosta_task.Utils.ViewUtils.Companion.loadImage
import com.yasser.bosta_task.Utils.ViewUtils.Companion.show
import com.yasser.bosta_task.databinding.AlbumItemBinding
import com.yasser.bosta_task.databinding.PhotoItemBinding


class AlbumViewAdapter(
    val context: Context,
    var albumList: MutableList<AlbumModel>,
    val onItemClicked: ItemClickListener<AlbumModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AlbumHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.album_item,parent,false))



    override fun getItemCount(): Int =albumList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AlbumHolder).bind(context,albumList[holder.bindingAdapterPosition],onItemClicked)
    }


    fun updateList(newAlbumList: List<AlbumModel>) {
        val placeInfoBaseDiffUtil: BaseDiffUtil =
            BaseDiffUtil(context, this.albumList, newAlbumList)
        val diffResult = DiffUtil.calculateDiff(placeInfoBaseDiffUtil)
        this.albumList.clear()
        this.albumList.addAll(newAlbumList)
        diffResult.dispatchUpdatesTo(this)
    }

//////////////////////////////////////////////////////////////////////////////////
    class AlbumHolder(var albumBinding: AlbumItemBinding) : RecyclerView.ViewHolder(albumBinding.root) {
        fun bind(context: Context,albumModel: AlbumModel, onItemClicked: ItemClickListener<AlbumModel>) {

            albumBinding.title.setText(albumModel.title)
            albumBinding.container
                .setOnClickListener { onItemClicked.onItemClick(albumBinding.root,albumModel) }

        }
    }


}