package com.yasser.bosta_task.Utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.yasser.bosta_task.R


class ViewUtils {
    companion object {

        fun pxToDp(px: Float): Float {
            val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
            return px / (densityDpi / 160f)
        }


        fun dpToPx(dp: Int): Float {
            val density = Resources.getSystem().displayMetrics.density
            return Math.round(dp * density).toFloat()
        }






        fun  initializeRecyclerView(
            recyclerView: RecyclerView,
            adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
            layoutManager: RecyclerView.LayoutManager,
            itemDecoration: RecyclerView.ItemDecoration?,
            itemAnimator: RecyclerView.ItemAnimator?,
        ) {

            if (itemDecoration != null) {
                recyclerView.addItemDecoration(itemDecoration)
            }

            recyclerView.itemAnimator = itemAnimator ?: DefaultItemAnimator()
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter


        }


        fun ImageView.loadImage(
            imageUrl: String,
            placeholderDrawable: Drawable? = null,
            progressBar: ProgressBar?
        ) {

            val theImage = GlideUrl(
                imageUrl, LazyHeaders.Builder()
                    .addHeader("User-Agent", "5")
                    .build()
            )
            Glide.with(this)
                .load(theImage)
                .error(placeholderDrawable)
                .apply(RequestOptions.timeoutOf(60*1000))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.e("Load Image",e!!.message!!)
                        progressBar?.hide()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        progressBar?.hide()
                        return false
                    }

                })
                .into(this)
        }





        fun showSnackBar(activity: Activity, message:String, isLong:Boolean, isError:Boolean){
            val length=if ( isLong){
                Snackbar.LENGTH_LONG
            }else{
                Snackbar.LENGTH_SHORT
            }
            val snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                message,
                length
            )
            if (isError){
                val snackBarView = snackbar.view
                snackBarView.setBackgroundColor(activity.resources.getColor(R.color.red))
            }
            snackbar.show()
        }

        fun View.show() {
            visibility = View.VISIBLE
        }

        fun View.hide(visibility: Int = View.GONE) {
            this.visibility = visibility
        }

    }
}