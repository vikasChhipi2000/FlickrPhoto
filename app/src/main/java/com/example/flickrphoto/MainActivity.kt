package com.example.flickrphoto

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),DownloadData.OnDownloadComplete
    ,GetJsonData.OnDataAvailable
    ,RecyclerItemClickListener.OnRecyclerItemClicked{

    val flickrRecyclerViewAdapter = RecyclerAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","onCreate called")
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this,recyclerView,this))
        recyclerView.adapter = flickrRecyclerViewAdapter

        val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne","robot",true,"en-us")
        Log.d("MainActivity",url)
        val downloadJson = DownloadData(this)
        downloadJson.execute(url)
        Log.d("onCreate","onCreate finish")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
           R.id.searchOption -> {
               val intent = Intent(this,SearchActivity::class.java)
               startActivity(intent)
               true
           }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSimpleClick(view: View, position: Int) {
        Log.d("MainActivity","onSimpleClick called")
        Toast.makeText(this,"simple click $position",Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(view: View, position: Int) {
        Log.d("MainActivity","onLongClick called")
        val phototransfer = flickrRecyclerViewAdapter.getPhoto(position)
        if(phototransfer!=null){
            val intent = Intent(this,PhotoDetail::class.java)
            intent.putExtra(PHOTO_TRANSFER,phototransfer)
            startActivity(intent)
        }
    }

    private fun createUri(rawUrl:String, tags : String, tagmode : Boolean, language : String) : String {
        Log.d("MainActivity","createUri called")
            return Uri.parse(rawUrl)
                .buildUpon()
                .appendQueryParameter("tags",tags)
                .appendQueryParameter("tagmode",if(tagmode)"ALL" else "ANY" )
                .appendQueryParameter("lang",language)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build()
                .toString()
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK){
            Log.d("MainActivity","ondownloadcomplete called")
            val getJsonData = GetJsonData(this)
            getJsonData.execute(data)
            Log.d("MainActivity","ondownloadcomplete finish")
        }else{
            Log.e("MainActivity","error in json download $data")
            Toast.makeText(this,"error!! $data",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d("MainActivity","onDataAvailable called")
        for (i in data.indices){
            Log.d("MainActivity",data[i].toString())
        }
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d("MainActivity","onDataAvailable finish")
    }

    override fun onError(e: Exception) {
        Log.e("MainActivity","error in json convertion")
        Toast.makeText(this,"error!! retry ",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences =this.getSharedPreferences("com.example.flickrphoto", Context.MODE_PRIVATE)
        val searchQuery = sharedPreferences.getString(FLICKR_QUERY,"")

        if(searchQuery!!.isNotEmpty()){
            val url = createUri("https://www.flickr.com/services/feeds/photos_public.gne",searchQuery,true,"en-us")
            Log.d("MainActivity",url)
            val downloadJson = DownloadData(this)
            downloadJson.execute(url)
        }
    }
}
