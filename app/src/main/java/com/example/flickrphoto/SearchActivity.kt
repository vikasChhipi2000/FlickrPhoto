package com.example.flickrphoto

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.widget.SearchView

import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {
    var searchView : SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolBar(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView= menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)

        searchView?.isIconified = false
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val sharedPreferences =applicationContext.getSharedPreferences("com.example.flickrphoto", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString(FLICKR_QUERY,query).apply()
                searchView?.clearFocus()

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            finish()
            false
        }

        return true
    }


}
