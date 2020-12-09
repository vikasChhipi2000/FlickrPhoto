package com.example.flickrphoto

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context:Context,recyclerView: RecyclerView,private val listener : OnRecyclerItemClicked) :RecyclerView.SimpleOnItemTouchListener(){

    interface OnRecyclerItemClicked{
        fun onSimpleClick(view:View,position : Int)
        fun onLongClick(view:View,position: Int)
    }
    val gestureDetector = GestureDetectorCompat(context,object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d("RecyclerItemClick","onSingleTapConfirmed called ")
            val childView = recyclerView.findChildViewUnder(e.x,e.y)
            listener.onSimpleClick(recyclerView,recyclerView.getChildAdapterPosition(childView!!))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d("RecyclerItemClick","onLongPress called ")
            val childView = recyclerView.findChildViewUnder(e.x,e.y)
            listener.onLongClick(recyclerView,recyclerView.getChildAdapterPosition(childView!!))
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d("RecyclerItemClick","onInterceptTouchEvent called $e")
        gestureDetector.onTouchEvent(e)
        return super.onInterceptTouchEvent(rv, e)
    }
}