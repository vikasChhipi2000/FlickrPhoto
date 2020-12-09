package com.example.flickrphoto

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class GetJsonData (private val listener : OnDataAvailable) : AsyncTask<String,Void,ArrayList<Photo>>() {
    interface OnDataAvailable{
        fun onDataAvailable(data:List<Photo>)
        fun onError(e : Exception)
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        super.onPostExecute(result)
        Log.d("GetJsonData","onPostExecute called")
        listener.onDataAvailable(result)
        Log.d("GetJsonData","onPostExecute finish")
    }

    override fun doInBackground(vararg params: String): ArrayList<Photo> {
        Log.d("GetJsonData","doInBackground called")
        val list : ArrayList<Photo> = ArrayList()
        try {
            val jsonObject = JSONObject(params[0])
            val jsonArray = jsonObject.getJSONArray("items")
            for(i in 0 until jsonArray.length()){
                val jsonPhoto = jsonArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")
                val media = jsonPhoto.getJSONObject("media")
                val image = media.getString("m")
                val link = image.replaceFirst("_m.jpg","_b.jpg")
                val photo = Photo(title,author,authorId,link,tags, image)
                list.add(photo)
            }
        }catch (e:JSONException){
            Log.e("GetJsonData","error in json convertion ${e.message}")
            listener.onError(e)
            cancel(true)
        }
        Log.d("GetJsonData","doInBackground finish")
        return list
    }
}