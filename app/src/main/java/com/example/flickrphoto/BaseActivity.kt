package com.example.flickrphoto

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

internal const val PHOTO_TRANSFER = "photoTransfer"
internal const val FLICKR_QUERY = "flickrquery"
open class BaseActivity:AppCompatActivity() {

   internal fun activateToolBar(enableHome : Boolean){
       var toolbar = findViewById<View>(R.id.toolbar) as? Toolbar
       setSupportActionBar(toolbar)
       supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}