package com.perfex.app.main.ui.base.callback

import android.view.View

/**
 * This is a interface that contains callback methods to work with RecyclerView item clicks
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
interface ItemClickListener<T> {
    /**
     * This method is called when an item gets clicked.
     *
     * @param view clicked view
     * @param item model object
     */
    fun onItemClick(view: View, item: T) {}

    /**
     * This method is called when an item gets clicked.
     *
     * @param view clicked view
     * @param item model object
     * @param position model object position in the list
     */
    fun onItemClick(view: View, item: T, position: Int) {}







}
