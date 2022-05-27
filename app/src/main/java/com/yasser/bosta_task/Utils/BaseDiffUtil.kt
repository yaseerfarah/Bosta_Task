package com.yasser.bosta_task.Utils

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.yasser.bosta_task.Utils.Interfaces.ModelBase

class BaseDiffUtil(private val context: Context, private val oldList: List<ModelBase>, private val newList: List<ModelBase>):
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id==newList[newItemPosition].id

    override fun getOldListSize(): Int=oldList.size


    override fun getNewListSize(): Int =newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition]==newList[newItemPosition]
}